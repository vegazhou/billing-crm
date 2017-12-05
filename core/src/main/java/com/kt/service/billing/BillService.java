package com.kt.service.billing;
import com.kt.biz.agent.RebateAlgorithm;
import com.kt.biz.agent.RebateAlgorithmBuilder;
import com.kt.biz.bean.*;
import com.kt.biz.billing.*;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.*;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.reseller.ResellerCustomerMapping;
import com.kt.biz.types.AccountType;
import com.kt.biz.types.AgentType;
import com.kt.biz.types.FeeType;
import com.kt.common.Constants;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.*;
import com.kt.entity.mysql.crm.Contract;
import com.kt.entity.mysql.crm.Customer;
import com.kt.entity.mysql.crm.Order;
import com.kt.entity.mysql.crm.Rate;
import com.kt.exception.*;
import com.kt.repo.mysql.batch.CustomerRepository;
import com.kt.repo.mysql.batch.OrderRepository;
import com.kt.repo.mysql.billing.*;
import com.kt.service.*;
import com.kt.service.mail.MailService;
import com.kt.service.mail.MailService.FirstInstallmentNotificationRow;
import com.kt.service.mail.MailTemplateService;
import com.kt.session.PrincipalContext;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import wang.huaichao.data.entity.crm.MeetingRecord;
import wang.huaichao.data.repo.MeetingRecordRepository;
import wang.huaichao.data.service.Biller;
import wang.huaichao.data.service.BillingService;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
@Service
public class BillService {
    @Autowired
    BillTempRepository billTempRepository;
    @Autowired
    BillTempDetailRepository billTempDetailRepository;

    @Autowired
    BillExportRepository billExportRepository;

    @Autowired
    BillingTaskRepository billingTaskRepository;
    @Autowired
    BillConfirmationRepository billConfirmationRepository;
    @Autowired
    CustomerAccountRepository customerAccountRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ContractService contractService;
    @Autowired
    FormalBillTuneLogRepository formalBillTuneLogRepository;
    @Autowired
    TempBillTuneLogRepository tempBillTuneLogRepository;
    @Autowired
    BillingLogRepository billingLogRepository;
    @Autowired
    BillFormalDetailRepository billFormalDetailRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    BillingService billingService;
    @Autowired
    MailTemplateService mailTemplateService;
    @Autowired
    MailService mailService;
    @Autowired
    ProductService productService;
    @Autowired
    MeetingRecordRepository meetingRecordRepository;



    ExecutorService mExecutor = Executors.newFixedThreadPool(30);



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void recalculateAll(AccountPeriod accountPeriod) throws WafException {
        List<Customer> allCustomers = customerService.listAllCustomerByList();
        List<BillTask> tasks = new ArrayList<>(allCustomers.size());
        for (Customer customer : allCustomers) {
            tasks.add(new BillTask(customer.getPid(), accountPeriod));
//            try {
//                recalculateBill(customer.getPid(), accountPeriod);
//            } catch (WafException ignore) {
//
//            }
        }

        try {
            mExecutor.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private class BillTask implements Callable<BigDecimal> {

        private String customerId;
        private AccountPeriod accountPeriod;

        public BillTask(String customerId, AccountPeriod accountPeriod) {
            this.customerId = customerId;
            this.accountPeriod = accountPeriod;
        }

        @Override
        public BigDecimal call() throws Exception {
            recalculateBill(customerId, accountPeriod);
            return new BigDecimal(0);
        }
    }


    /**
     * 重新计算某个客户在某个账期的账单，并存入临时账单表
     *
     * @param customerId    客户ID
     * @param accountPeriod 账期
     * @throws WafException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void recalculateBill(String customerId, AccountPeriod accountPeriod) throws WafException {
        //TODO: 重新计算账期账单时，必须先将快照恢复
        try {
            CustomerBill totalBill = new CustomerBill(customerId, accountPeriod);
            //计算当前账期应缴纳的预付费费用
            CustomerBill prepaidFeeBill = calcPrepaidDueOfCustomer(customerId, accountPeriod);
            totalBill.merge(prepaidFeeBill);

            //计算前一账期发生的后付费费用
            CustomerBill normalFeeBill = calcPostpaidDueOfCustomer(customerId, accountPeriod.previousPeriod());
            totalBill.merge(normalFeeBill);

            overwriteTempBill(totalBill);
        } catch (WafException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public synchronized BillFormalDetail findFormalBill(String customerId, String orderId, AccountPeriod accountPeriod, FeeType feeType) throws FormalBillNotFoundException {
        if (feeType == FeeType.WEBEX_FIRST_INSTALLMENT || feeType == FeeType.CC_FIRST_INSTALLMENT) {
            List<BillFormalDetail> bills = billFormalDetailRepository.findByOrderIdAndFeeType(orderId, feeType.getValue());
            if (bills == null || bills.size() == 0) {
                throw new FormalBillNotFoundException();
            }
            return bills.get(0);
        } else {
            BillFormalDetail formal =
                    billFormalDetailRepository.findOneByCustomerIdAndOrderIdAndAccountPeriodAndFeeType(customerId, orderId, accountPeriod.toString(), feeType.getValue());
            if (formal == null) {
                throw new FormalBillNotFoundException();
            }
            return formal;
        }
    }

    public synchronized  BillTemp findTempBill(String customerId, AccountPeriod accountPeriod, FeeType feeType) throws TempBillNotFoundException {
        BillTemp bill =
            billTempRepository.findByCustomerIdAndAccountPeriodAndFeeType(customerId, accountPeriod.toString(), feeType.getValue());
        if (bill == null) {
            throw new TempBillNotFoundException();
        }
        return bill;
    }





    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized BillFormalDetail countIntoFormalBill(String customerId, String orderId, AccountPeriod accountPeriod, FeeType feeType, BigDecimal amount) {
        BillFormalDetail formal = new BillFormalDetail();
        formal.setCustomerId(customerId);
        formal.setAccountPeriod(accountPeriod.toString());
        formal.setFeeType(feeType.getValue());
        formal.setAmount(amount.floatValue());
        formal.setUnpaidAmount(amount.floatValue());
        formal.setOrderId(orderId);

        formal.setSapSynced(0);

        BillFormalDetail f = billFormalDetailRepository.saveAndFlush(formal);
        return f;
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void tuneBill(Long billId, BigDecimal newAmount, String comments) throws WafException {
        BillFormalDetail bill = billFormalDetailRepository.findOne(billId);
        if (bill == null) {
            throw new FormalBillNotFoundException();
        }
        newAmount = MathUtil.scale(newAmount);
        BigDecimal currentAmount = MathUtil.scale(new BigDecimal(bill.getAmount()));
        BigDecimal tuneAmount = newAmount.subtract(currentAmount);

        try {
            tuneBill(bill.getCustomerId(), bill.getOrderId(), new AccountPeriod(bill.getAccountPeriod()), FeeType.valueOf(bill.getFeeType()), tuneAmount, comments);
            adjustInvoice(bill, tuneAmount);
        } catch (ParseException e) {
            throw new InvalidAccountPeriodFormatException(e);
        }
    }


    /**
     * 调账函数
     *
     * @param customerId       调账账单所属客户ID
     * @param accountPeriod    调账账单所属账期
     * @param feeType           调账账单的费用类型
     * @param tuneAmount        调账金额，上调为正数，下调为负数
     * @throws FormalBillNotFoundException
     * @throws RefundAmountExceedsOriginalAmountException
     */

    public synchronized void tuneBill(String customerId, String orderId, AccountPeriod accountPeriod, FeeType feeType, BigDecimal tuneAmount, String comments)
            throws FormalBillNotFoundException, RefundAmountExceedsOriginalAmountException {
        BillFormalDetail bill = findFormalBill(customerId, orderId, accountPeriod, feeType);
        tuneAmount = MathUtil.scale(tuneAmount);
        BigDecimal currentAmount = MathUtil.scale(new BigDecimal(bill.getAmount()));
        BigDecimal currentUnpaidAmount = MathUtil.scale(new BigDecimal(bill.getUnpaidAmount()));
        BigDecimal currentPaidAmount = currentAmount.subtract(currentUnpaidAmount);
        BigDecimal newAmount = currentAmount.add(tuneAmount);
        BigDecimal newUnpaid = new BigDecimal(0f);
        if (newAmount.compareTo(currentAmount) == 0) {
            return;
        }

        if (newAmount.compareTo(new BigDecimal(0)) < 0) {
            //原金额小于减免金额，抛异常
            throw new RefundAmountExceedsOriginalAmountException();
        }

        if (currentPaidAmount.compareTo(newAmount) > 0) {
            BigDecimal refundAmount = currentPaidAmount.subtract(newAmount);
            //已付金额大于新金额，需要做退款处理
            bill.setAmount(newAmount.floatValue());
            bill.setUnpaidAmount(0f);
            AccountType refundAccountType = getChargeAccountTypeOfFeeType(feeType);
            accountService.refundToAccount(customerId, refundAccountType, refundAmount);
        } else {
            //已付金额不大于新金额，无需做退款处理，只需修改当前账目金额
            newUnpaid = newAmount.subtract(currentPaidAmount);
            bill.setAmount(newAmount.floatValue());
            bill.setUnpaidAmount(newUnpaid.floatValue());
        }
//        bill.setLastUpdated(DateUtil.now());
        bill.setSapSynced(0);
        billFormalDetailRepository.save(bill);

        logFormalBillTuneOperation(bill.getId(), currentAmount.floatValue(), newAmount.floatValue(),
                currentUnpaidAmount.floatValue(), newUnpaid.floatValue(), comments);
    }


    private AccountType getChargeAccountTypeOfFeeType(FeeType feeType) {
        switch (feeType) {
            case WEBEX_FIRST_INSTALLMENT:
            case WEBEX_CONFERENCE_FEE:
            case WEBEX_STORAGE_FEE:
                return AccountType.PREPAID;
            case WEBEX_PSTN_FEE:
            case WEBEX_OVERFLOW_FEE:
            case WEBEX_EC_DEPOSIT:
                return AccountType.DEPOSIT;
            default:
                return AccountType.PREPAID;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void tuneTempBill(Long billId, BigDecimal newAmount, String comments) throws WafException {
        BillTemp bill = billTempRepository.findOne(billId);
        if (bill == null) {
            throw new TempBillNotFoundException();
        }

        BigDecimal tuneAmount = newAmount.subtract(new BigDecimal(bill.getAmount()));

        try {
            tuneTempBill(bill.getCustomerId(), new AccountPeriod(bill.getAccountPeriod()), FeeType.valueOf(bill.getFeeType()), tuneAmount, comments);
        } catch (ParseException e) {
            throw new InvalidAccountPeriodFormatException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void tuneTempBill(String customerId, AccountPeriod accountPeriod, FeeType feeType, BigDecimal tuneAmount, String comments)
            throws TempBillNotFoundException, RefundAmountExceedsOriginalAmountException {
        BillTemp bill = findTempBill(customerId, accountPeriod, feeType);
        BigDecimal currentAmount = new BigDecimal(bill.getAmount());
        BigDecimal newAmount = currentAmount.add(tuneAmount);
        if (newAmount.compareTo(new BigDecimal(0)) < 0) {
            //原金额小于减免金额
            throw new RefundAmountExceedsOriginalAmountException();
        }

        bill.setAmount(newAmount.floatValue());
        billTempRepository.save(bill);

        logTempBillTuneOperation(bill.getId(), currentAmount.floatValue(), newAmount.floatValue(), comments);
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void overwriteTempBill(CustomerBill bill) {
        AccountPeriod accountPeriod = bill.getAccountPeriod();
        String customerId = bill.getCustomerId();
        billTempRepository.deleteByCustomerIdAndAccountPeriod(customerId, accountPeriod.toString());
        billTempDetailRepository.deleteByCustomerIdAndAccountPeriod(customerId, accountPeriod.toString());

        for (Map.Entry<FeeType, BigDecimal> fee : bill.getFees().entrySet()) {
            FeeType feeType = fee.getKey();
            BigDecimal amount = fee.getValue();
            BillTemp billItem = buildTempBillItem(customerId, accountPeriod, feeType, amount);
            billTempRepository.save(billItem);
        }

        for (CustomerBill.Detail detail : bill.getDetails().values()) {
            BillTempDetail detailRecord = buildTempBillItemDetail(customerId, detail.getOrderId(), accountPeriod, detail.getFeeType(), detail.getAmount());
            billTempDetailRepository.save(detailRecord);
        }
    }

    private BillTemp buildTempBillItem(String customerId, AccountPeriod accountPeriod, FeeType feeType, BigDecimal amount) {
        BillTemp item = new BillTemp();
        item.setAccountPeriod(accountPeriod.toString());
        item.setCustomerId(customerId);
        item.setAmount(amount.setScale(2, RoundingMode.HALF_UP).floatValue());
        item.setFeeType(feeType.getValue());
        return item;
    }


    private BillTempDetail buildTempBillItemDetail(String customerId, String orderId, AccountPeriod accountPeriod, FeeType feeType, BigDecimal amount) {
        BillTempDetail detail = new BillTempDetail();
        detail.setCustomerId(customerId);
        detail.setFeeType(feeType.getValue());
        detail.setAmount(amount.setScale(2, RoundingMode.HALF_UP).floatValue());
        detail.setAccountPeriod(accountPeriod.toString());
        detail.setOrderId(orderId);
        return detail;
    }





    public List<AgentRebateBean> calculateAgentRebate(AccountPeriod accountPeriod) throws WafException, ParseException {
        List<Customer> agents = customerService.listAllAgents();
        List<AgentRebateBean> total = new LinkedList<>();
        for (Customer agent : agents) {
            List<AgentRebateBean> bills = calculateAgentRebate(agent.getPid(), accountPeriod);
            total.addAll(bills);
        }
        return total;
    }




    public List<AgentRebateBean> calculateAgentRebate(String agentId, AccountPeriod accountPeriod) throws WafException, ParseException {
        List<AgentRebateBean> bills = billFormalDetailRepository.listRebateBillsOfAgent(agentId, accountPeriod);
        for (AgentRebateBean bill : bills) {
            RebateAlgorithm algorithm = RebateAlgorithmBuilder.build(bill);
            if (algorithm == null) {
                bills.remove(bill);
                continue;
            }
            bill.setUnitPrice(algorithm.computeUnitPrice().doubleValue());
            bill.setAgentUnitPrice(algorithm.computeAgentUnitPrice().doubleValue());
            bill.setAgentTotalPrice(algorithm.computeAgentAmount().doubleValue());
            bill.setRebateWithRegister(bill.getAmount() - algorithm.computeRegisterSettleAmount().doubleValue());
            bill.setRebateWithoutRegister(bill.getAmount() - algorithm.computeNonRegisterSettleAmount().doubleValue());
        }
        return bills;
    }


    /**
     * 计算指定计费周期内客户的应缴纳的预付应缴款
     *
     * @param customerId 客户ID
     * @param accountPeriod 计费周期
     */
    public CustomerBill calcPrepaidDueOfCustomer(String customerId, AccountPeriod accountPeriod) throws WafException {
        CustomerBill bill = new CustomerBill(customerId, accountPeriod);
        List<Contract> contracts = contractService.findAccountableContractsOfCustomer(customerId);
        for (Contract contract : contracts) {
            List<Order> orders = orderService.findOrdersByContractId(contract.getPid());
            for (Order order : orders) {
                List<BillItem> billItems = calcPrepaidDue(order.getPid(), accountPeriod);
                if (billItems == null) {
                    continue;
                }
                for (BillItem billItem : billItems) {
                    bill.accumulate(order.getPid(), billItem.getFeeType(), billItem.getAmount());
                }
            }
        }
        return bill;
    }

    /**
     * 计算指定计费周期内客户产生的后付应缴款
     * @param customerId     客户ID
     * @param accountPeriod  计费周期
     * @return                  客户账单
     * @throws WafException
     */
    public CustomerBill calcPostpaidDueOfCustomer(String customerId, AccountPeriod accountPeriod) throws WafException {
        CustomerBill bill = new CustomerBill(customerId, accountPeriod);
        List<Contract> contracts = contractService.findAccountableContractsOfCustomer(customerId);
//        try {
//            Future future = billingService.calculatingPstnFeeByBillingPeriodAndCustomerId(accountPeriod.toInt(), customerId);
//            future.get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        for (Contract contract : contracts) {
            List<Order> orders = orderService.findOrdersByContractId(contract.getPid());
            for (Order order : orders) {
                List<BillItem> billItems = calcPostpaidDue(order.getPid(), accountPeriod);
                if (billItems == null) {
                    continue;
                }
                for (BillItem billItem : billItems) {
                    bill.accumulate(order.getPid(), billItem.getFeeType(), billItem.getAmount());
                }
            }
        }
        return bill;
    }


    /**
     * 计算变更合同所产生的退款
     * @param alterationContractId  变更合同ID
     * @return  退款账单项目
     * @throws WafException
     */
    public List<BillItem> calcRefund(String alterationContractId) throws WafException {
        List<Order> orders = orderService.findOrdersByContractId(alterationContractId);
        List<BillItem> result = new ArrayList<>();
        for (Order order : orders) {
            if (StringUtils.isNotBlank(order.getOriginalOrderId())) {
                try {
                    result.add(calcRefund(order.getPid(), DateUtil.toDate(order.getEffectiveEndDate())));
                } catch (ParseException ignore) {
                }
            }
        }
        return result;
    }


    public List<BillItem> calcPostpaidDue(String orderId, AccountPeriod accountPeriod) throws WafException {
        OrderBean order = orderService.findOrderBeanById(orderId);
        FeeCalculator calc = FeeCalculatorManager.getFeeCalculator(order);
        if (order.isEffectiveInAccountPeriod(accountPeriod)) {
            return calc.calculatePostpaidDue(accountPeriod);
        } else {
            return null;
        }
    }

    private List<BillItem> calcPrepaidDue(String orderId, AccountPeriod accountPeriod) throws WafException {
        OrderBean order = orderService.findOrderBeanById(orderId);
        //TODO: 这里有一个BUG，后半月开始的订单有可能在订单结束后依然要收费
//        if (order.isEffectiveInAccountPeriod(accountPeriod)) {
            FeeCalculator calc = FeeCalculatorManager.getFeeCalculator(order);
            return calc.calculatePrepaidDue(accountPeriod);
//        } else {
//            return null;
//        }
    }

    public BillItem calcRefund(String orderId, Date date) throws WafException {
        OrderBean order = orderService.findOrderBeanById(orderId);
        OrderBean originalOrder = orderService.findOrderBeanById(order.getOriginalOrderId());
        FeeCalculator calc = FeeCalculatorManager.getFeeCalculator(order);
        return calc.calculateRefund(originalOrder, date);
    }


    public static class TempBillConfirmForm {
        private String customerId;

        private AccountPeriod accountPeriod;

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public AccountPeriod getAccountPeriod() {
            return accountPeriod;
        }

        public void setAccountPeriod(AccountPeriod accountPeriod) {
            this.accountPeriod = accountPeriod;
        }
    }

    /**
     * 批量确认临时账单
     *
     * @throws TempBillAlreadyConfirmedException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void confirmBill(List<TempBillConfirmForm> forms) throws WafException {
        for (TempBillConfirmForm form : forms) {
            confirmBill(form.getCustomerId(), form.getAccountPeriod());
        }
    }


    /**
     * 确认某客户在某一账期的临时账单，将临时账单记入正式账单
     *
     * @param accountPeriod 账期
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void confirmBill(String customerId, AccountPeriod accountPeriod) throws WafException {
        assertTempBillCanBeConfirmed(customerId, accountPeriod);
        deleteConfirmedBills(customerId, accountPeriod);
        List<BillTempDetail> toBeCopiedBills = billTempDetailRepository.findByCustomerIdAndAccountPeriod(customerId, accountPeriod.toString());
        for (BillTempDetail toBeCopied : toBeCopiedBills) {
            //全部重新生成正式账单
//            if (toBeCopied.getConfirmed() == 0) {
                BillFormalDetail formal = convert2formal(toBeCopied);
//              create invoce
                if(formal.getFeeType()==FeeType.WEBEX_PSTN_FEE.getValue() ||
                        formal.getFeeType()==FeeType.WEBEX_OVERFLOW_FEE.getValue()|| formal.getFeeType()==FeeType.WEBEX_POSTPAID_CONFERENCE_FEE.getValue()){
                	createInvoice(formal);
                }else if(formal.getFeeType()!=1){
                	createCircleInvoice(formal);
                }
                billFormalDetailRepository.save(formal);
                toBeCopied.setConfirmed(1);
                billTempDetailRepository.save(toBeCopied);
//            }
        }
        //不删除临时账单
//        billTempRepository.deleteByCustomerIdAndAccountPeriod(customerId, accountPeriod.toString());
        logBillConfirmation(customerId, accountPeriod);
    }


    private void deleteConfirmedBills(String customerId, AccountPeriod accountPeriod) throws WafException {
        List<BillFormalDetail> bills = billFormalDetailRepository.findByCustomerIdAndAccountPeriodAndFeeTypeNot(customerId, accountPeriod.toString(), FeeType.WEBEX_FIRST_INSTALLMENT.getValue());
        for (BillFormalDetail bill : bills) {
            if (bill.getAmount().compareTo(bill.getUnpaidAmount()) != 0) {
                //该笔账已经有扣款动作，需要做退款
                tuneBill(bill.getId(), new BigDecimal(0), "系统账单重计");
                billFormalDetailRepository.delete(bill);
            }
        }
        billFormalDetailRepository.flush();
    }



    private BillFormalDetail convert2formal(BillTempDetail temp) {
        BillFormalDetail formal = new BillFormalDetail();
        formal.setCustomerId(temp.getCustomerId());
        formal.setAccountPeriod(temp.getAccountPeriod());
        formal.setFeeType(temp.getFeeType());
        formal.setAmount(temp.getAmount());
        formal.setUnpaidAmount(temp.getAmount());
        formal.setOrderId(temp.getOrderId());
        formal.setSapSynced(0);
        return formal;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void charge() throws WafException {
        List<BillFormalDetail> unpaidBills = billFormalDetailRepository.findUnpaidBills();
        for (BillFormalDetail unpaidBill : unpaidBills) {
            charge(unpaidBill);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void charge(Long billId) throws WafException {
        BillFormalDetail bill = billFormalDetailRepository.findOne(billId);
        charge(bill);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
     public synchronized void charge(Long... billIds) throws WafException {
        for (Long billId : billIds) {
            charge(billId);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void charge(String customerId) throws WafException {
        List<BillFormalDetail> unpaidBills = billFormalDetailRepository.listUnpaidBills(customerId);
        for (BillFormalDetail unpaidBill : unpaidBills) {
            charge(unpaidBill);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void charge(String customerId, AccountPeriod accountPeriod) throws WafException {
        List<BillFormalDetail> unpaidBills = billFormalDetailRepository.listUnpaidBills(customerId, accountPeriod);
        for (BillFormalDetail unpaidBill : unpaidBills) {
            charge(unpaidBill);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void chargeFirstInstallment(String contractId) throws WafException {
        List<BillFormalDetail> bills = billFormalDetailRepository.findFirstInstallmentBillOfContract(contractId);
        for (BillFormalDetail bill : bills) {
            charge(bill);
        }
    }


    /**
     * 根据某笔账单项目，从客户的相应账户中进行扣款
     *
     * @param bill 待扣款账单项
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized BigDecimal charge(BillFormalDetail bill) throws WafException {
        BigDecimal unpaidAmount = new BigDecimal(bill.getUnpaidAmount());
        BigDecimal realPaid = billCharge(bill.getCustomerId(), bill.getOrderId(), FeeType.valueOf(bill.getFeeType()), unpaidAmount);
        if (realPaid.compareTo(new BigDecimal(0)) != 0) {
            unpaidAmount = unpaidAmount.subtract(realPaid);
            if (unpaidAmount.compareTo(new BigDecimal(0)) == 0) {
                bill.setClearedDate(DateUtil.now());
            }
            bill.setUnpaidAmount(unpaidAmount.floatValue());
            billFormalDetailRepository.save(bill);
        }
        return realPaid;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void countContractFirstInstallmentIntoBill(String contractId) throws WafException {
        Contract contract = contractService.findByContractId(contractId);
        AgentType agentType = contractService.getAgentTypeOfContract(contractId);
        List<Order> orders = orderService.findOrdersByContractId(contractId);

        for (Order order : orders) {
            if (StringUtils.isBlank(order.getOriginalOrderId())) {
                //只有新下订单才需要计算首付款，对于订单变更，无需重复缴纳
                if (agentType == AgentType.RESELLER2) {
                    countIntoFormalBill(contract.getAgentId(), order.getPid(), new AccountPeriod(new Date()),
                            FeeType.valueOf(order.getFiType()), new BigDecimal(order.getFirstInstallment()));
                } else {
                    countIntoFormalBill(contract.getCustomerId(), order.getPid(), new AccountPeriod(new Date()),
                            FeeType.valueOf(order.getFiType()), new BigDecimal(order.getFirstInstallment()));
                }
            }
        }
    }






    private synchronized BigDecimal billCharge(String customerId, String orderId, FeeType feeType, BigDecimal amount) throws WafException {
        AccountType[] accountChargeOrder = new AccountType[]{AccountType.PREPAID};
        switch (feeType) {
            case WEBEX_FIRST_INSTALLMENT:
            case WEBEX_CONFERENCE_FEE:
            case WEBEX_STORAGE_FEE:
                accountChargeOrder = new AccountType[]{AccountType.PREPAID};
                break;
            case WEBEX_PSTN_FEE:
            case WEBEX_OVERFLOW_FEE:
            case WEBEX_EC_DEPOSIT:
                accountChargeOrder = new AccountType[]{AccountType.DEPOSIT};
                break;
            case CC_FIRST_INSTALLMENT:
            case CC_FEE:
            case CC_PSTN_FEE:
                accountChargeOrder = new AccountType[]{AccountType.CC_DEPOSIT};
                break;
        }
        if (feeType == FeeType.WEBEX_EC_DEPOSIT) {
            accountChargeOrder = new AccountType[] {AccountType.DEPOSIT};
        }

        BigDecimal totalRealPaidAmount = new BigDecimal(0);
        for (AccountType accountType : accountChargeOrder) {
            if (amount.compareTo(new BigDecimal(0)) == 0) {
                break;
            }
            List<CustomerAccount> accounts = findAccountsOfCustomer(customerId, accountType);
            for (CustomerAccount account : accounts) {
                if (amount.compareTo(new BigDecimal(0)) > 0) {
                    BigDecimal realPayedAmount = accountService.payFromAccount(account.getId(), orderId, amount);

                    if (realPayedAmount.compareTo(new BigDecimal(0)) != 0) {
                        totalRealPaidAmount = totalRealPaidAmount.add(realPayedAmount);
                        amount = amount.subtract(realPayedAmount);
                    }
                }
            }
        }

        return totalRealPaidAmount;
    }



    public List<CustomerAccount> findAccountsOfCustomer(String customerId, AccountType accountType) throws WafException {
        List<CustomerAccount> accounts = new ArrayList<>();
        Customer customer = customerService.findByCustomerId(customerId);
        List<String> codes = ResellerCustomerMapping.mapResellerCodes(customer.getCode());
        for (String code : codes) {
            Customer c = customerService.findFirstCustomerByCode(code);
            accounts.add(accountService.findOrCreate(c.getPid(), accountType));
        }
        return accounts;
    }


    private void assertTempBillCanBeConfirmed(String customerId, AccountPeriod accountPeriod) throws TempBillAlreadyConfirmedException {
        BillConfirmationPrimaryKey key = new BillConfirmationPrimaryKey();
        key.setAccountPeriod(accountPeriod.toString());
        key.setCustomerId(customerId);
        BillConfirmation confirmation = billConfirmationRepository.findOne(key);
        if (confirmation != null) {
            throw new TempBillAlreadyConfirmedException();
        }
    }

    private void logBillConfirmation(String customerId, AccountPeriod accountPeriod) {
        BillConfirmation confirmationLog = new BillConfirmation();
        confirmationLog.setCustomerId(customerId);
        confirmationLog.setAccountPeriod(accountPeriod.toString());
        confirmationLog.setConfirmedDate(DateUtil.now());
        confirmationLog.setConfirmedBy(PrincipalContext.getCurrentUserName());
        billConfirmationRepository.save(confirmationLog);
    }

    public List<FormalBillDetailBean> listAllByPageForFormalDetail(String customerId, AccountPeriod accountPeriod, SearchFilter search) {
        return billFormalDetailRepository.findBillDetail(customerId, accountPeriod, search);
    }

    public Page<FormalBillDetailBean> paginateBillFormalDetail(String customerName, AccountPeriod accountPeriod, int curPage, SearchFilter searchFilter) {
        return billFormalDetailRepository.paginateBillDetail(customerName, accountPeriod, curPage, searchFilter);
    }


    public Page<BillAge> listAllByPageForBillAge(int curPage, SearchFilter search) {
        return billFormalDetailRepository.listAllByPageForBillAge(curPage, search);

    }

    public List<TempBillDetailBean> listAllByPageTemp(String customerId, AccountPeriod accountPeriod, SearchFilter search) {
        return billTempRepository.findTempBillDetail(customerId, accountPeriod, search);
    }

    public List<BillExport> listAllBillForExport(String date) {
        //return billExportRepository.findBills(date);
    	return billExportRepository.findBills(date);
    }

    public List<BillExport> getBiggestDateBillForExport() {

    	return billExportRepository.findBiggestDate();
    }

    public List<BillExport> getSamllestDateForExport() {

    	return billExportRepository.findSmallestDate();
    }

    public List<BillExport> getALLBills(String date) {

    	return billExportRepository.findAllBills(date);
    }

    public List<BillExport> getInvoice(String date) {

    	return billExportRepository.findInvoice(date);
    }

    public List<BillExport> findInvoiceCountByName(String invoiceName) {

    	return billExportRepository.findInvoiceCountByName(invoiceName);
    }

   public List<BillExport> getOneInvoice(String name) {

    	return billExportRepository.findByInvoiceNameContainingAndNetInvoicedAmountNotOrderByInvoiceNumberAsc(name, 0);
    }


   public List<BillExport> getOneInvoiceByName(String name) {

   	return billExportRepository.findByInvoiceNameAndNetInvoicedAmountNotOrderByInvoiceNumberAsc(name, 0);
   }

    public Page<BillTemp> listAllByPageTempForConfirm(int curPage, SearchFilter search) {
        return billTempRepository.listAllByPageForConfirm(curPage, search);

    }

    public Page<FormalBillTuneLog> listAllByPageForFormalBillLog(int curPage, SearchFilter search) {
        return formalBillTuneLogRepository.listAllByPage(curPage, search);

    }


    public List<BillFormalDetail> listBillsByAccountType(String customerName) {
        return billFormalDetailRepository.listBillsByAccountType(customerName);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void logFormalBillTuneOperation(Long billId, Float primalAmount, Float currentAmount, Float primalUnpaid, Float currentUnpaid, String comments) {
        FormalBillTuneLog log = new FormalBillTuneLog();
        log.setBillId(billId);
        log.setPrimalAmount(primalAmount);
        log.setCurrentAmount(currentAmount);
        log.setOperator(PrincipalContext.getCurrentUserName());
        log.setComments(comments);
        log.setLogDate(DateUtil.now());
        formalBillTuneLogRepository.save(log);
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void logTempBillTuneOperation(Long billId, Float primalAmount, Float currentAmount, String comments) {
        TempBillTuneLog log = new TempBillTuneLog();
        log.setBillId(billId);
        log.setPrimalAmount(primalAmount);
        log.setCurrentAmount(currentAmount);
        log.setOperator(PrincipalContext.getCurrentUserName());
        log.setComments(comments);
        tempBillTuneLogRepository.save(log);

    }


    public void logBillingLog(String customer, AccountPeriod accountPeriod, String log) {
        BillingLog logEntry = new BillingLog();
        logEntry.setCustomerId(customer);
        logEntry.setAccountPeriod(accountPeriod.toString());
        logEntry.setLog(log);
        logEntry.setTimeStamp(DateUtil.now());
        billingLogRepository.save(logEntry);
    }



    Rate findSpecifiedRate(List<Rate> rates, String code) {
        for (Rate rate : rates) {
            if (rate.getCode().equals(code)) {
                return  rate;
            }
        }
        return null;
    }





    public Page<MonthlyBillBean> paginateMonthlyBillByAccountPeriod(AccountPeriod accountPeriod, int curPage, SearchFilter search) {
        return billFormalDetailRepository.paginateMonthlyBill(accountPeriod, curPage, search);
    }

    public Page<MonthlyBillBean> paginateBillByCustomerName(String customerName, int curPage, SearchFilter search) {
        return billFormalDetailRepository.paginateBillByCustomerName(customerName, curPage, search);
    }

    private void createInvoice(BillFormalDetail formal ) {
    	String customerId=formal.getCustomerId();
    	String orderId=formal.getOrderId();
    	Customer customer=customerRepository.findOne(customerId);
    	Order order=orderRepository.findOne(orderId);
    	String code=customer.getCode();
    	OrderBean orderBean=OrderBeanCache.get(order);
    	AccountPeriod accountPeriod=null;
    	try {
			 accountPeriod=new AccountPeriod(formal.getAccountPeriod());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	String invoiceName=code+"-"+accountPeriod.toString()+"01"+"-1";
    	String startDate=DateUtil.formatShortInvoiceDate(orderBean.getStartDate());//should be the 9.1-9.31
    	String productDesc=orderBean.getBiz().getDisplayName();
    	String productType="";
    	String documentDate=DateUtil.formatShortInvoiceDate(new Date());


    	int contractTerm=0;
    	String revenueType=null;
		float amount=formal.getAmount();
		int unit=0;
		int interval=1;
		if(formal.getFeeType()==11){
			revenueType=Constants.AUDIO_USAGE_OR_OVERAGE;
			productType=Constants.PRODUCT_TYPE_AUDIO_USAGE_OR_OVERAGE;
		}else{
			revenueType=Constants.NETWORK_USAGE_OR_OVERAGE;
			productType=Constants.PRODUCT_TYPE_NETWORK_USAGE_OR_OVERAGE;
		}
		billExportRepository.deleteByInvoiceNameAndProductTypeAndOrderId(invoiceName,productType,orderId);
		billExportRepository.flush();
		insertInvoiceData(invoiceName, documentDate, code, revenueType , startDate,
				    productDesc, productType, interval, unit, contractTerm, amount,orderId,formal.getFeeType());

    }

    private void adjustInvoice(BillFormalDetail formal ,BigDecimal bigDecimal ) throws WafException{
    	
    	String orderId=formal.getOrderId();
    	Order order=orderRepository.findOne(orderId);
    	String contractId=order.getContractId();
    	AgentType agentType = contractService.getAgentTypeOfContract(order.getContractId());
   	 	Contract contract = contractService.findByContractId(contractId);
        String agentId=contract.getAgentId();
    	String customerId = "";
        if (agentType == AgentType.RESELLER2) {
    		customerId = agentId;
        } else {
        	customerId = order.getCustomerId();
        }
    	
    	Customer customer=customerRepository.findOne(customerId);
    	
    	
    	float amount=bigDecimal.floatValue();
    	String code=customer.getCode();
    	OrderBean orderBean=OrderBeanCache.get(order);
    	String invoiceName=code+"-"+DateUtil.formatInvoiceNameDate(new Date()).substring(0,6)+"01"+"-01-C";

    	//String invoiceMemo="12";
    	String invoiceMemo = code+"-"+DateUtil.formatInvoiceNameDate(orderBean.getStartDate()).substring(0,6)+"01"+"-01";;
    	String documentDate=DateUtil.formatShortInvoiceDate(new Date());
    	insertUpsellingInvoiceData(invoiceName,invoiceMemo, documentDate, code, amount, orderId,formal.getFeeType());

    }


    private void insertInvoiceData(String invoiceName,String documentDate,String code,String revenueType ,String startDate,
			   String productDesc,String productType,int interval,int unit,int contractTerm,float amount,String orderId,int feeType)
	{
    	float beforeTaxValue=amount;
    	amount=amount/Constants.TAXRATE;
		BillExport billExport=new BillExport();
		billExport.setInvoiceName(invoiceName);
		billExport.setDocumentType(Constants.INVOICE);
		billExport.setDocumentDate(documentDate);
		billExport.setCustomerId(code);
		billExport.setCreateDate(DateUtil.now());
		billExport.setRevenueType(revenueType);
		billExport.setSalesChannel(Constants.DIRECT);
		billExport.setContractCommence(startDate);
		billExport.setProductDesc(productDesc);
		billExport.setProductType(productType);
		billExport.setPaymentInterval(interval);
		billExport.setUnit(unit);
		billExport.setContractTerm(contractTerm);
		billExport.setListedPrice(amount);
		billExport.setInvoicedAmount(amount);
		billExport.setNetInvoicedAmount(amount);
		billExport.setCreditAmount(0);
		billExport.setOrderId(orderId);
		billExport.setBeforeTaxValue(beforeTaxValue);
		billExport.setFeeType(feeType);
		billExportRepository.save(billExport);
	}

    private void insertUpsellingInvoiceData(String invoiceName,String invoiceMemo,String documentDate,String code,float amount,String orderId,int feeType)
    {
    		float beforeTaxValue=amount;
    		amount=amount/Constants.TAXRATE;
    		BillExport billExport=new BillExport();
    		billExport.setInvoiceName(invoiceName);
    		billExport.setInvoiceMemo(invoiceMemo);
    		billExport.setDocumentType(Constants.CREDITNOTE);
    		billExport.setDocumentDate(documentDate);
    		billExport.setCustomerId(code);
    		billExport.setCreateDate(DateUtil.now());
    		billExport.setRevenueType(Constants.CREDITNOTE);
    		billExport.setSalesChannel(Constants.DIRECT);
    		billExport.setContractCommence("");
    		billExport.setProductDesc(Constants.CREDITNOTE);
    		billExport.setProductType(Constants.CREDITNOTE);
    		billExport.setPaymentInterval(1);
    		billExport.setUnit(0);
    		billExport.setContractTerm(0);
    		billExport.setCreditType(Constants.CREDITTYPE_ADJUSTBILL);
    		billExport.setNetInvoicedAmount(amount);
    		billExport.setCreditAmount(amount);
    		billExport.setOrderId(orderId);
    		billExport.setBeforeTaxValue(beforeTaxValue);
    		billExport.setFeeType(feeType);
    		billExportRepository.save(billExport);
    }


    public String createInvoicePdfHtml(String billterm,BillExport billExport,String startDate,String endDate) throws WafException, ParseException{

         List<BillExport> billingReportList=getOneInvoice(billExport.getInvoiceName());

         VelocityEngine ve = new VelocityEngine();
         ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
         ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
         ve.setProperty("eventhandler.referenceinsertion.class", Biller.InsertHanler.class.getName());
         ve.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
         ve.init();

        Template template = ve.getTemplate("sap/oldinvoice.vm" , "utf-8");     




         VelocityContext context = new VelocityContext();
         AccountPeriod accountPeriod = new AccountPeriod(billterm);


         context.put("customerCode", billExport.getCustomerId());
         context.put("invoiceName", billExport.getInvoiceName());
         context.put("customerName", customerService.findFirstCustomerByCode(billExport.getCustomerId()).getDisplayName());
         context.put("customerContact", customerService.findFirstCustomerByCode(billExport.getCustomerId()).getContactName());
         context.put("startTime", DateUtil.formatShortDate(accountPeriod.beginOfThisPeriod()));
         context.put("endTime", DateUtil.formatShortDate(accountPeriod.endOfThisPeriod()));




         List<FirstInstallmentNotificationRow> rows = new ArrayList<>();
         FirstInstallmentNotificationRow summary = new FirstInstallmentNotificationRow();
         float total = 0f;
         for (BillExport billExportInList : billingReportList) {

                 FirstInstallmentNotificationRow row = new FirstInstallmentNotificationRow();
                 row.setEffectiveStartDate(startDate);
                 row.setEffectiveEndDate(endDate);
                 row.setProduct(billExportInList.getProductDesc());
                 float fi = MathUtil.scale(billExportInList.getNetInvoicedAmount()*1.0672f);
                 row.setFirstInstallment(String.valueOf(fi));
                 total += fi;
                 rows.add(row);

         }
         context.put("rows", rows);
         summary.setFirstInstallment(String.valueOf(total));
         context.put("summary", summary);

         StringWriter sw = new StringWriter();
         template.merge(context, sw);
         String html = sw.toString();
         return html;
    }
    
    
    public String createInvoicePdfHtmlContent(String billterm,BillExport billExport,String startDate,String endDate) throws WafException, ParseException{
        
        List<BillExport> billingReportList=getOneInvoice(billExport.getInvoiceName());
        
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty("eventhandler.referenceinsertion.class", Biller.InsertHanler.class.getName());
        ve.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
        ve.init();

       Template template = ve.getTemplate("sap/invoice.vm" , "utf-8");
       
        
        
        VelocityContext context = new VelocityContext();
        AccountPeriod accountPeriod = new AccountPeriod(billterm);
       
        
        context.put("customerCode", billExport.getCustomerId());
        context.put("invoiceName", billExport.getInvoiceName());
        context.put("customerName", customerService.findFirstCustomerByCode(billExport.getCustomerId()).getDisplayName());
        context.put("customerContact", customerService.findFirstCustomerByCode(billExport.getCustomerId()).getContactName());
        context.put("startTime", DateUtil.formatShortDate(accountPeriod.beginOfThisPeriod()));
        context.put("endTime", DateUtil.formatShortDate(accountPeriod.endOfThisPeriod()));

        
        
        
        List<FirstInstallmentNotificationRow> rows = new ArrayList<>();
        FirstInstallmentNotificationRow summary = new FirstInstallmentNotificationRow();
        float total = 0f;
        for (BillExport billExportInList : billingReportList) {
          
                FirstInstallmentNotificationRow row = new FirstInstallmentNotificationRow();
                row.setEffectiveStartDate(startDate);
                row.setEffectiveEndDate(endDate);
                row.setProduct(billExportInList.getProductDesc());
                float fi = MathUtil.scale(billExportInList.getNetInvoicedAmount()*1.0672f);
                row.setFirstInstallment(String.valueOf(fi));
                total += fi;
                rows.add(row);
            
        }
        context.put("rows", rows);
        summary.setFirstInstallment(String.valueOf(total));
        context.put("summary", summary);

        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        String html = sw.toString();
        return html;
   }
    
    
    public String createInvoicePdfHtmlHeader(String content) throws WafException, ParseException{
        
    
        
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty("eventhandler.referenceinsertion.class", Biller.InsertHanler.class.getName());
        ve.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
        ve.init();

       Template template = ve.getTemplate("sap/invoiceheader.vm" , "utf-8");
       
        
        
        VelocityContext context = new VelocityContext();
     
       
        
        context.put("content", content);
       

        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        String html = sw.toString();
        return html;
   }
    
    private void createCircleInvoice(BillFormalDetail formal ) {
    	String customerId=formal.getCustomerId();
    	String orderId=formal.getOrderId();
    	int feeType=formal.getFeeType();
    	Customer customer=customerRepository.findOne(customerId);
    	Order order=orderRepository.findOne(orderId);
    	String code=customer.getCode();
    	OrderBean orderBean=OrderBeanCache.get(order);
    	AccountPeriod accountPeriod=null;
    	String productDesc="";
    	String productType="";
    	String documentDate="";
    	String invoiceName="";
    	String invoiceName3="";
    	String startDate="";
    	int monthInterval=0;
    	try {
			 accountPeriod=new AccountPeriod(formal.getAccountPeriod());
		
	    	Date nowDate=DateUtil.toInvoiceDate(accountPeriod.toString()+"01");
	    	String partName1=code+"-"+accountPeriod.toString()+"01";
	    	//String partName1= code + "-" + DateUtil.formatInvoiceNameDate(order.getStartDate()).substring(0, 6)+"01";
	        int countIndex1=2+findInvoiceCountByName(partName1).size();
	        invoiceName = partName1+"-"+countIndex1;
	        
	        String partName3= code + "-" + DateUtil.formatInvoiceNameDate(DateUtil.addTwoMonth(nowDate)).substring(0, 6) + "01";
	        int countIndex3=2+findInvoiceCountByName(partName3).size();
	        invoiceName3 = partName3+"-"+countIndex3;
	        
	    	startDate=DateUtil.formatShortInvoiceDate(orderBean.getStartDate());//should be the 9.1-9.31	    	
	    	
	   	 	
			
	    	Calendar now = Calendar.getInstance();
	    	now.setTime(nowDate); 	
	    	
	    	Calendar start = Calendar.getInstance();
	    	start.setTime(orderBean.getStartDate()); 		
	    	monthInterval = (now.get(Calendar.YEAR)-start.get(Calendar.YEAR))*12+now.get(Calendar.MONTH) - start.get(Calendar.MONTH);
	    	documentDate=DateUtil.formatShortInvoiceDate(new Date());
    	
    
           
    		 productDesc=  productService.findById(order.getProductId()).getDisplayName();           
    		 productType=productDesc;
         }  catch (Exception e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
    	
    	
       float beforeTaxValue=order.getFirstInstallment().floatValue();
		
       
       
       
		AbstractChargeScheme scheme = orderBean.getChargeScheme();
		if(orderBean.getPayInterval().getInterval() == 1) {

			 String revenueType = Constants.NETWORK_SUBSCRIPTION_FEE;
             if (scheme instanceof WebExConfMonthlyPayByHosts) {
                 WebExConfMonthlyPayByHosts webExConfMonthlyPayByHosts = (WebExConfMonthlyPayByHosts) scheme;

                 int contractTerm = webExConfMonthlyPayByHosts.getMonths();
                 int interval = contractTerm;
                 int unit = webExConfMonthlyPayByHosts.getHosts();
                 insertInvoiceDateForCircle(monthInterval,invoiceName, documentDate, code, revenueType, startDate,
          				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);

             } else if (scheme instanceof WebExConfMonthlyPayByPorts) {
                 WebExConfMonthlyPayByPorts webExConfMonthlyPayByPorts = (WebExConfMonthlyPayByPorts) scheme;
                 int contractTerm = webExConfMonthlyPayByPorts.getMonths();
                 int interval = contractTerm;
                 int unit = webExConfMonthlyPayByPorts.getPorts();
                 insertInvoiceDateForCircle(monthInterval,invoiceName, documentDate, code, revenueType, startDate,
          				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);

             }else if (scheme instanceof WebExConfMonthlyPayByTotalAttendees) {
            	 WebExConfMonthlyPayByTotalAttendees webExConfMonthlyPayByTotalAttendees = (WebExConfMonthlyPayByTotalAttendees) scheme;
                 int contractTerm = webExConfMonthlyPayByTotalAttendees.getMonths();
                 int interval = contractTerm;
                 int unit = webExConfMonthlyPayByTotalAttendees.getPorts();
                 insertInvoiceDateForCircle(monthInterval,invoiceName, documentDate, code, revenueType, startDate,
          				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);

             } else if (scheme instanceof WebExStorageMonthlyPay) {
                 WebExStorageMonthlyPay webExStorageMonthlyPay = (WebExStorageMonthlyPay) scheme;
                 int contractTerm = webExStorageMonthlyPay.getMonth();
                 int interval = contractTerm;
                 int unit = webExStorageMonthlyPay.getStorageSize();
                 insertInvoiceDateForCircle(monthInterval,invoiceName, documentDate, code, revenueType, startDate,
          				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);

             }else if (scheme instanceof WebExStorageMonthlyOverflowPay) {
            	 WebExStorageMonthlyOverflowPay webExStorageMonthlyOverflowPay = (WebExStorageMonthlyOverflowPay) scheme;
                 int contractTerm = webExStorageMonthlyOverflowPay.getMonth();
                 int interval = contractTerm;
                 int unit = webExStorageMonthlyOverflowPay.getStorageSize();
                 insertInvoiceDateForCircle(monthInterval,invoiceName, documentDate, code, revenueType, startDate,
          				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);

             }else if (scheme instanceof PSTNMonthlyPacketCharge) {
                PSTNMonthlyPacketCharge pstnMonthlyPacketCharge = (PSTNMonthlyPacketCharge) scheme;
                int contractTerm = pstnMonthlyPacketCharge.getMonths();
                int interval = contractTerm;
                revenueType = Constants.PSTN_FEE;
                int unit = 1;
                insertInvoiceDateForCircle(monthInterval,invoiceName, documentDate, code, revenueType, startDate,
         				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);

            } else if (scheme instanceof WebExCmrMonthlyPay) {
                WebExCmrMonthlyPay webExCmrMonthlyPay = (WebExCmrMonthlyPay) scheme;
                int contractTerm = webExCmrMonthlyPay.getMonth();
                int interval = contractTerm;
                revenueType = Constants.CMR_FEE;
                int unit = 1;                
                insertInvoiceDateForCircle(monthInterval,invoiceName, documentDate, code, revenueType, startDate,
         				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);              	

            }else if (scheme instanceof WebExConfMonthlyPayPersonal) {
            	WebExConfMonthlyPayPersonal WebExConfMonthlyPayPersonal = (WebExConfMonthlyPayPersonal) scheme;
                int contractTerm = WebExConfMonthlyPayPersonal.getMonths();
                int interval = contractTerm;
                revenueType = Constants.PERSONAL_FEE;
                int unit = 1;                
                insertInvoiceDateForCircle(monthInterval,invoiceName, documentDate, code, revenueType, startDate,
         				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);              	

            }

        }else if (orderBean.getPayInterval().getInterval() == 3) {
            int month = 3;
            insertInvoiceDateForCircleIntervalBiggerOne(orderBean, month, invoiceName, documentDate, code, startDate,
                    productDesc, productType, orderId,beforeTaxValue,invoiceName3,feeType);


        } else if (orderBean.getPayInterval().getInterval() == 6) {
            int month = 6;
            insertInvoiceDateForCircleIntervalBiggerOne(orderBean, month, invoiceName, documentDate, code, startDate,
                    productDesc, productType, orderId,beforeTaxValue,invoiceName3,feeType);

        } else if (orderBean.getPayInterval().getInterval() == 12) {
            int month = 12;
            insertInvoiceDateForCircleIntervalBiggerOne(orderBean, month, invoiceName, documentDate, code, startDate,
                    productDesc, productType, orderId,beforeTaxValue,invoiceName3,feeType);

        }

    }
    
    private void insertInvoiceDateForCircle(int monthInterval,String invoiceName,String documentDate,String code,String revenueType ,String startDate,
			   String productDesc,String productType,int interval,int unit,int contractTerm,float beforeTaxValue,String orderId,int feeType){
    	
    	if ((monthInterval >= 2 && !Constants.SHLXCODE.equals(code))||(monthInterval >= 3 && Constants.SHLXCODE.equals(code))) {
         	
         	insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
         				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);              	
         	
         } 

    } 
    
    private void insertInvoiceDateForCircleIntervalBiggerOne(OrderBean order, int month, String invoiceName, String documentDate, String code, String startDate,
            String productDesc, String productType, String orderId,float beforeTaxValue,String invoiceName3,int feeType){
 	
    	if(Constants.SHLXCODE.equals(code)){
    		insertNetworkFee(order, month, invoiceName3, documentDate, code, startDate,
                    productDesc, productType, orderId,beforeTaxValue,feeType);
        }else{
        	insertNetworkFee(order, month, invoiceName, documentDate, code, startDate,
                    productDesc, productType, orderId,beforeTaxValue,feeType);
        }

    } 
    
    private void insertNetworkFee(OrderBean order, int month, String invoiceName, String documentDate, String code, String startDate,
            String productDesc, String productType, String orderId,float beforeTaxValue,int feeType) {
			float amount = 0;
			AbstractChargeScheme scheme = order.getChargeScheme();
			String revenueType = Constants.NETWORK_SUBSCRIPTION_FEE;
			String revenueTypeStorage = Constants.STORAGE_FEE;
			String revenueTypePSTN = Constants.PSTN_FEE;
			String revenueTypeCMR = Constants.CMR_FEE;
			String revenueTypePersonal = Constants.PERSONAL_FEE;
			if (scheme instanceof WebExConfMonthlyPayByHosts) {
				WebExConfMonthlyPayByHosts webExConfMonthlyPayByHosts = (WebExConfMonthlyPayByHosts) scheme;
				int contractTerm = webExConfMonthlyPayByHosts.getMonths();
				int unit = webExConfMonthlyPayByHosts.getHosts();
				int interval = (int) Math.ceil((float) contractTerm / (float) month);				
				
				insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);
				
				
			} else if (scheme instanceof WebExConfMonthlyPayByPorts) {
				WebExConfMonthlyPayByPorts webExConfMonthlyPayByPorts = (WebExConfMonthlyPayByPorts) scheme;
				int contractTerm = webExConfMonthlyPayByPorts.getMonths();
				int unit = webExConfMonthlyPayByPorts.getPorts();
				int interval = (int) Math.ceil((float) contractTerm / (float) month);
				
				
				insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);
			
			
			}else if (scheme instanceof WebExConfMonthlyPayByTotalAttendees) {
				WebExConfMonthlyPayByTotalAttendees webExConfMonthlyPayByTotalAttendees = (WebExConfMonthlyPayByTotalAttendees) scheme;
				int contractTerm = webExConfMonthlyPayByTotalAttendees.getMonths();
				int unit = webExConfMonthlyPayByTotalAttendees.getPorts();
				int interval = (int) Math.ceil((float) contractTerm / (float) month);
				
				
				insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);
			
			
			} else if (scheme instanceof WebExStorageMonthlyPay) {
				WebExStorageMonthlyPay webExStorageMonthlyPay = (WebExStorageMonthlyPay) scheme;
				int contractTerm = webExStorageMonthlyPay.getMonth();
				int unit = webExStorageMonthlyPay.getStorageSize();
				int interval = (int) Math.ceil((float) contractTerm / (float) month);
				
				
				insertInvoiceData(invoiceName, documentDate, code, revenueTypeStorage, startDate,
				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);
				
			
			}else if (scheme instanceof WebExStorageMonthlyOverflowPay) {
				WebExStorageMonthlyOverflowPay webExStorageMonthlyOverflowPay = (WebExStorageMonthlyOverflowPay) scheme;
				int contractTerm = webExStorageMonthlyOverflowPay.getMonth();
				int unit = webExStorageMonthlyOverflowPay.getStorageSize();
				int interval = (int) Math.ceil((float) contractTerm / (float) month);
				
				
				insertInvoiceData(invoiceName, documentDate, code, revenueTypeStorage, startDate,
				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);
				
			
			} else if (scheme instanceof PSTNMonthlyPacketCharge) {
				PSTNMonthlyPacketCharge pstnMonthlyPacketCharge = (PSTNMonthlyPacketCharge) scheme;
				int contractTerm = pstnMonthlyPacketCharge.getMonths();
				int unit = 1;
				int interval = (int) Math.ceil((float) contractTerm / (float) month);
				
				
				insertInvoiceData(invoiceName, documentDate, code, revenueTypePSTN, startDate,
				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);
				  
			
			} else if (scheme instanceof WebExCmrMonthlyPay) {
				WebExCmrMonthlyPay webExCmrMonthlyPay = (WebExCmrMonthlyPay) scheme;
				int contractTerm = webExCmrMonthlyPay.getMonth();
				int unit = 1;
				int interval = (int) Math.ceil((float) contractTerm / (float) month);
				
				
				insertInvoiceData(invoiceName, documentDate, code, revenueTypeCMR, startDate,
				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);
			
			}else if (scheme instanceof WebExConfMonthlyPayPersonal) {
				WebExConfMonthlyPayPersonal WebExConfMonthlyPayPersonal = (WebExConfMonthlyPayPersonal) scheme;
				int contractTerm = WebExConfMonthlyPayPersonal.getMonths();
				int unit = 1;
				int interval = (int) Math.ceil((float) contractTerm / (float) month);
				
				
				insertInvoiceData(invoiceName, documentDate, code, revenueTypePersonal, startDate,
				productDesc, productType, interval, unit, contractTerm, beforeTaxValue, orderId,feeType);
			
			}
			
		}
}
