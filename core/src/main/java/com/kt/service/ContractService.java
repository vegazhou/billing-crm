package com.kt.service;

import com.kt.biz.auth.PrivilegeChecker;
import com.kt.biz.bean.ContractBean;
import com.kt.biz.bean.ContractFirstInstallments;
import com.kt.biz.bean.ContractRefunds;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.billing.FeeCalculatorManager;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.MiscCharge;
import com.kt.biz.model.charge.impl.PSTNMonthlyPacketCharge;
import com.kt.biz.model.charge.impl.PSTNPersonalPacket;
import com.kt.biz.model.charge.impl.PSTNSinglePacket4AllSites;
import com.kt.biz.model.charge.impl.PSTNSinglePacket4MultiSites;
import com.kt.biz.model.charge.impl.WebExCmrMonthlyPay;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByHosts;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByPorts;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByTotalAttendees;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayPersonal;
import com.kt.biz.model.charge.impl.WebExStorageMonthlyOverflowPay;
import com.kt.biz.model.charge.impl.WebExStorageMonthlyPay;
import com.kt.biz.model.order.*;
import com.kt.biz.types.AccountType;
import com.kt.biz.types.AgentType;
import com.kt.biz.types.CommonState;
import com.kt.biz.types.FeeType;
import com.kt.common.Constants;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.BillExport;
import com.kt.entity.mysql.billing.BillFormalDetail;
import com.kt.entity.mysql.billing.ContractPdf;
import com.kt.entity.mysql.crm.*;
import com.kt.exception.*;
import com.kt.repo.mongo.entity.ContractPdfBean;
import com.kt.repo.mongo.repository.ContractPdfEntityRepository;
import com.kt.repo.mysql.batch.ContractPdfRepository;
import com.kt.repo.mysql.batch.ContractRepository;
import com.kt.repo.mysql.batch.CustomerRepository;
import com.kt.repo.mysql.batch.OrderRepository;
import com.kt.repo.mysql.billing.BillExportRepository;
import com.kt.service.billing.AccountService;
import com.kt.service.billing.BillService;
import com.kt.service.mail.MailService;
import com.kt.session.PrincipalContext;
import com.kt.util.DateUtil;
import com.mongodb.gridfs.GridFSFile;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Service
public class ContractService {

    @Autowired
    BillService billService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    BizSchemeService bizSchemeService;
    @Autowired
    ChargeSchemeService chargeSchemeService;
    @Autowired
    WebExSiteService webExSiteService;
    @Autowired
    AccountService accountService;
    @Autowired
    SalesmanService salesmanService;
    @Autowired
    BillExportRepository billExportRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    MailService mailService;
    @Autowired
    ContractPdfRepository contractPdfRepository;
    @Autowired
    ContractPdfEntityRepository contractPdfEntityRepository;     
    @Autowired
    GridFsOperations operations;
    @Autowired
    ProductService productService;

    private final static String OPERATOR_SYSTEM = "system";


    public AgentType getAgentTypeOfContract(String contractId) throws WafException {
        Contract contract = findByContractId(contractId);
        String agentId = contract.getAgentId();
        if (StringUtils.isNotBlank(agentId)) {
            return customerService.getAgentType(agentId);
        }
        return AgentType.DIRECT;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Contract draftContract(ContractBean contractBean) throws WafException {
        PrivilegeChecker.isOperator();
        assertSalesmanValid(contractBean.getSalesmanId());
        assertAgentValid(contractBean.getAgentId());
        Customer customer = customerService.findByCustomerId(contractBean.getCustomerId());
        Contract contract = new Contract();
        contract.setDisplayName(contractBean.getDisplayName());
        contract.setState(CommonState.DRAFT.toString());
        contract.setCustomerId(customer.getPid());
        contract.setDraftDate(DateUtil.now());
        contract.setDraftedBy(PrincipalContext.getCurrentUserName());
        contract.setLastModifiedDate(DateUtil.now());
        contract.setLastModifiedBy(PrincipalContext.getCurrentUserName());
        contract.setIsChanging(0);
        contract.setIsAlteration(0);
        contract.setSalesman(contractBean.getSalesmanId());
        contract.setAgentId(contractBean.getAgentId());
        contract.setIsRegistered(contractBean.isRegistered() ? 1 : 0);
        contract.setComments(contractBean.getComments());
        return contractRepository.save(contract);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Contract draftTelecomContract(String customerId, String displayName, String salesmanId) throws WafException {
        assertSalesmanValid(salesmanId);
        Customer customer = customerService.findByCustomerId(customerId);
        Contract contract = new Contract();
        contract.setDisplayName(displayName);
        contract.setState(CommonState.DRAFT.toString());
        contract.setCustomerId(customer.getPid());
        contract.setDraftDate(DateUtil.now());
        contract.setDraftedBy(OPERATOR_SYSTEM);
        contract.setLastModifiedDate(DateUtil.now());
        contract.setLastModifiedBy(OPERATOR_SYSTEM);
        contract.setIsChanging(0);
        contract.setIsAlteration(0);
        contract.setSalesman(salesmanId);
        return contractRepository.save(contract);
    }

    public Contract findByContractId(String id) throws WafException {
        Contract contract = contractRepository.findOne(id);
        if (contract == null) {
            throw new ContractNotFoundException();
        }
        return contract;
    }


    public List<Contract> findAgentContracts(String agentId) {
        return contractRepository.findByAgentId(agentId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void rename(String id, String newDisplayName, String salesMan, String agentId, boolean isRegistered, String comments) throws WafException {
        if (StringUtils.isBlank(newDisplayName)) {
            return;
        }
        PrivilegeChecker.isOperator();
        Contract contract = findByContractId(id);

        if (CommonState.valueOf(contract.getState()) != CommonState.DRAFT) {
            throw new OnlyDraftingContractAllowedException();
        }

        contract.setDisplayName(newDisplayName.trim());
        contract.setSalesman(salesMan);
        contract.setAgentId(agentId);
        contract.setIsRegistered(isRegistered ? 1 : 0);
		contract.setComments(comments);
        contractRepository.save(contract);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByContractId(String id) throws WafException {
        PrivilegeChecker.isOperator();
        Contract contract = findByContractId(id);

        if (CommonState.valueOf(contract.getState()) != CommonState.DRAFT) {
            throw new OnlyDraftingContractAllowedException();
        }

        List<Order> orders = orderRepository.findByContractId(id);
        for (Order order : orders) {
            orderService.deleteByForce(order.getPid());
        }

        webExSiteService.deleteAllSiteDraftByContractId(id);

        if (contract.getIsAlteration() > 0) {
            markContractAsNotChanging(contract.getAlterTargetId());
        }
        deletePdfByContractId(id);
        contractRepository.delete(id);
    }


    /**
     * 警告: 会强制删除合同，仅供方便测试时使用
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByForce(String id) throws WafException {
        Contract contract = findByContractId(id);
        List<Order> orders = orderRepository.findByContractId(id);
        for (Order order : orders) {
            orderService.deleteByForce(order.getPid());
        }

        webExSiteService.deleteAllSiteDraftByContractId(id);

        if (contract.getIsAlteration() > 0) {
            markContractAsNotChanging(contract.getAlterTargetId());
        }
        contractRepository.delete(id);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendContract4Approval(String contractId) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        sendContract4Approval(contractId, true);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendContract4Approval(String contractId, boolean sendNotificationEmail) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        assertOrderStartDateValid(contractId);
        sendContract4ApprovalIgnoreInvalidDate(contractId, sendNotificationEmail);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendContract4ApprovalIgnoreInvalidDate(String contractId) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        sendContract4ApprovalIgnoreInvalidDate(contractId, true);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendContract4ApprovalIgnoreInvalidDate(String contractId, boolean sendNotificationEmail) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        PrivilegeChecker.isOperator();
        Contract contract = findByContractId(contractId);

        if (CommonState.valueOf(contract.getState()) != CommonState.DRAFT) {
            throw new OnlyDraftingContractAllowedException();
        }

        assertContractCompletionAndNoCollision(contractId);
        assertContractAlterationValid(contractId);
        webExSiteService.assertSiteNotOccupied(contractId);

        List<Order> orders = orderRepository.findByContractId(contractId);
        for (Order order : orders) {
            orderService.sendOrder4Approval(order.getPid());
        }
        contract.setState(CommonState.WAITING_APPROVAL.toString());
        contractRepository.save(contract);
        contractRepository.flush();

        //TODO: 通知录单审核员
        if(sendNotificationEmail) {
            mailService.notifyContractWaitingApproval(contract);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendTelecomContract4Approval(String contractId) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        Contract contract = findByContractId(contractId);

        if (CommonState.valueOf(contract.getState()) != CommonState.DRAFT) {
            throw new OnlyDraftingContractAllowedException();
        }

        assertContractCompletionAndNoCollision(contractId);
        assertContractAlterationValid(contractId);
        webExSiteService.assertSiteNotOccupied(contractId);

        List<Order> orders = orderRepository.findByContractId(contractId);
        for (Order order : orders) {
            orderService.sendTelecomOrder4Approval(order.getPid());
        }
        contract.setState(CommonState.WAITING_APPROVAL.toString());
        contractRepository.save(contract);
        contractRepository.flush();

        //TODO: 通知录单审核员
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void withdraw(String contractId) throws WafException {
        PrivilegeChecker.isOperator();
        Contract contract = findByContractId(contractId);
        if (CommonState.valueOf(contract.getState()) != CommonState.WAITING_APPROVAL &&
                CommonState.valueOf(contract.getState()) != CommonState.WAITING_FIN_APPROVAL) {
            throw new OnlyWaitingApprovalContractAllowedException();
        }

        List<Order> orders = orderRepository.findByContractId(contractId);
        for (Order order : orders) {
            orderService.withdraw(order.getPid());
        }
        contract.setState(CommonState.DRAFT.toString());
        contractRepository.save(contract);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void approve(String contractId) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        approve(contractId, true);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void approve(String contractId, boolean sendNotificationEmail) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        assertOrderStartDateValid(contractId);
        approveIgnoreInvalidDate(contractId, sendNotificationEmail);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void approveIgnoreInvalidDate(String contractId) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        approveIgnoreInvalidDate(contractId, true);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void approveIgnoreInvalidDate(String contractId, boolean sendNotificationEmail) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        PrivilegeChecker.isContractAuditor();
        Contract contract = findByContractId(contractId);
        if (CommonState.valueOf(contract.getState()) != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalContractAllowedException();
        }

        assertContractCompletionAndNoCollision(contractId);

        webExSiteService.assertSiteNotOccupied(contractId);

        float firstInstallment = calculateFirstInstallment(contractId);

        if (firstInstallment > 0) {
            contract.setState(CommonState.WAITING_FIN_APPROVAL.toString());
            contractRepository.save(contract);
            contractRepository.flush();
            //TODO: 通知财务审核， 通知销售催缴首付款
            if(sendNotificationEmail) {
                mailService.notifyWaitingFirstInstallmentConfirmation(contract);
            }
        } else {
            putContractInEffect(contractId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void approveTelecom(String contractId) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        Contract contract = findByContractId(contractId);
        if (CommonState.valueOf(contract.getState()) != CommonState.WAITING_APPROVAL) {
            throw new OnlyWaitingApprovalContractAllowedException();
        }

        assertContractCompletionAndNoCollision(contractId);
        webExSiteService.assertSiteNotOccupied(contractId);

        float firstInstallment = calculateFirstInstallment(contractId);
        if (firstInstallment > 0) {
            contract.setState(CommonState.WAITING_FIN_APPROVAL.toString());
            contractRepository.save(contract);
            contractRepository.flush();
            //TODO: 通知财务审核， 通知销售催缴首付款
        } else {
            putTelecomContractInEffect(contractId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void finApprove(String contractId, Float payment, Float ccPayment) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        PrivilegeChecker.isFinAuditor();
        Contract contract = findByContractId(contractId);
        if (CommonState.valueOf(contract.getState()) != CommonState.WAITING_FIN_APPROVAL) {
            throw new OnlyWaitingApprovalContractAllowedException();
        }

        assertContractCompletionAndNoCollision(contractId);
        assertOrderStartDateValid(contractId);
        webExSiteService.assertSiteNotOccupied(contractId);

        depositFirstInstallment(contract, AccountType.PREPAID, new BigDecimal(payment));
        depositFirstInstallment(contract, AccountType.CC_DEPOSIT, new BigDecimal(ccPayment));
        putContractInEffect(contractId);


        if (contract.getIsAlteration() == 0) {
            if (payment > 0 || ccPayment > 0) {
                billService.chargeFirstInstallment(contractId);
            }
        } else {
            billService.chargeFirstInstallment(contract.getAlterTargetId());
        }
    }


    private void depositFirstInstallment(Contract contract, AccountType accountType, BigDecimal amount) throws WafException {
        if (amount == null || amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
            return;
        }

        AgentType agentType = getAgentTypeOfContract(contract.getPid());
        if (agentType == AgentType.RESELLER2) {
            accountService.deposit(contract.getAgentId(), accountType, amount);
        } else {
            accountService.deposit(contract.getCustomerId(), accountType, amount);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void finApproveTelecom(String contractId, Float payment) throws WafException, OrderCollisionsDetectedException, OrderIncompleteException {
        Contract contract = findByContractId(contractId);
        if (CommonState.valueOf(contract.getState()) != CommonState.WAITING_FIN_APPROVAL) {
            throw new OnlyWaitingApprovalContractAllowedException();
        }

        assertContractCompletionAndNoCollision(contractId);
//        assertOrderStartDateValid(contractId);
        webExSiteService.assertSiteNotOccupied(contractId);

        accountService.depositTelecom(contract.getCustomerId(), AccountType.PREPAID, new BigDecimal(payment));
        putTelecomContractInEffect(contractId);
        if (contract.getIsAlteration() == 0) {
            if (payment > 0) {
                billService.chargeFirstInstallment(contractId);
            }
        } else {
            billService.chargeFirstInstallment(contract.getAlterTargetId());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void decline(String contractId) throws WafException {
        PrivilegeChecker.isContractAuditor();
        doDecline(contractId);

        //TODO: 通知录单人员
        Contract contract = findByContractId(contractId);
        mailService.notifyContractDeclined(contract);
    }



    public boolean hasContract(String customerId) {
        return contractRepository.findFirstByCustomerId(customerId) != null;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Contract draftContractAlteration(String contractId) throws WafException {
        PrivilegeChecker.isOperator();
        Contract contract = findByContractId(contractId);
        CommonState state = CommonState.valueOf(contract.getState());
        if (state != CommonState.IN_EFFECT) {
            throw new OnlyInEffectContractAllowedException();
        }

        if (contract.getIsChanging() == 1) {
            throw new ContractIsAlreadyChangingException();
        }

        contract.setIsChanging(1);
        contractRepository.save(contract);

        Contract alteration = new Contract();
        alteration.setDisplayName(contract.getDisplayName());
        alteration.setState(CommonState.DRAFT.toString());
        alteration.setCustomerId(contract.getCustomerId());
        alteration.setDraftDate(DateUtil.now());
        alteration.setDraftedBy(PrincipalContext.getCurrentUserName());
        alteration.setLastModifiedDate(DateUtil.now());
        alteration.setLastModifiedBy(PrincipalContext.getCurrentUserName());
        alteration.setIsChanging(0);
        alteration.setIsAlteration(1);
        alteration.setAlterTargetId(contractId);
        alteration.setSalesman(contract.getSalesman());
        alteration.setAgentId(contract.getAgentId());
        alteration.setIsRegistered(contract.getIsRegistered());
        alteration.setComments(contract.getComments());
        alteration = contractRepository.save(alteration);

        orderService.cloneContractOrders4Alteration(contractId, alteration.getPid());
        return alteration;
    }

    /**
     * 返回指定客户，在指定账期中，可能发生过费用的合同（）
     */
//    public List<Contract> findInEffectOrEolContractsOfCustomer(String customerId, AccountPeriod accountPeriod) {
//        //TODO 目前返回该客户所有已生效过的合同，需要做优化
//        return contractRepository.findByCustomerIdAndState(customerId, CommonState.IN_EFFECT.toString());
//    }

    public List<Contract> findAccountableContractsOfCustomer(String customerId) throws WafException {
        AgentType agentType = customerService.getAgentType(customerId);
        switch (agentType) {
            case AGENT:
                return Collections.emptyList();
            case RESELLER2:
                return findContractsOfReseller2(customerId);
            default:
                return findNonReseller2Contracts(customerId);
        }
    }

    public List<Contract> findAccountableNonReseller2Contracts(String directCustomerId, AccountPeriod accountPeriod) {
        return contractRepository.findAccountableNonReseller2Contracts(directCustomerId, accountPeriod);
    }

    public List<Contract> findAccountableReseller2Contracts(String resellerCustomerId, AccountPeriod accountPeriod) {
        return contractRepository.findAccountableReseller2Contracts(resellerCustomerId, accountPeriod);
    }

    public List<Contract> findNonReseller2Contracts(String customerId) {
        return contractRepository.findNonReseller2Contracts(customerId);
    }

    public List<Contract> findContractsOfReseller2(String resellerId) {
        return contractRepository.findContractsOfReseller2(resellerId);
    }

    public List<Contract> findAllContractsByCustomerId(String customerId) {
        return contractRepository.findByCustomerId(customerId);
    }
    
    public Page<Contract> listAllByPage(int curPage, SearchFilter search) {
        return contractRepository.listAllByPage(curPage, search);
    }
    
    public Page<Contract> listAllByPageForConfirm(int curPage, SearchFilter search) {
        return contractRepository.listAllByPageForConfirm(curPage, search);
    }


    public ContractFirstInstallments summarizeFirstInstallment(String contractId) {
        List<Order> orders = orderService.findOrdersByContractId(contractId);
        ContractFirstInstallments result = new ContractFirstInstallments();
        for (Order order : orders) {
            if (StringUtils.isBlank(order.getOriginalOrderId())) {
                result.countIn(order);
            }
        }
        return result;
    }


    public float calculateFirstInstallment(String contractId) throws WafException {
        List<OrderBean> contractOrders = orderService.findOrderBeansByContractId(contractId);
        float firstInstallment = 0;
        for (OrderBean order : contractOrders) {
            if (StringUtils.isBlank(order.getOriginalOrderId())) {
                firstInstallment += order.calculateFirstInstallment();
            }
        }
        return firstInstallment;
    }





    public void assertContractCompletionAndNoCollision(String contractId) throws WafException, OrderIncompleteException, OrderCollisionsDetectedException {
        List<OrderBean> contractOrders = orderService.findOrderBeansByContractId(contractId);
        List<OrderBean> context = orderService.findOrderContext(contractId);

        if (contractOrders.size() == 0){
            throw new NoOrderInContractException();
        }

        for (OrderBean contractOrder : contractOrders) {
            CompletionCheckResult completionCheckResult
                    = OrderCompletionCheck.check(contractOrder);
            if (!completionCheckResult.isOk()) {
                throw new OrderIncompleteException(completionCheckResult.getMessage());
            }
        }

        for (OrderBean contractOrder : contractOrders) {
            CollisionDetectResult compatCheckResult =
                    OrderCollisionDetect.check(contractOrder, context);
            if (!compatCheckResult.isOk()) {
                throw new OrderCollisionsDetectedException(compatCheckResult.getMessage());
            }
        }
    }


    private void assertOrderStartDateValid(String contractId) throws WafException {
        List<OrderBean> contractOrders = orderService.findOrderBeansByContractId(contractId);

        for (OrderBean order : contractOrders) {
            if (StringUtils.isNotBlank(order.getOriginalOrderId())) {
                continue;
            }
            if (order.getStartDate().before(DateUtil.beginOfToday())) {
                throw new InvalidOrderStartDateException();
            }
        }
    }


    private void assertContractAlterationValid(String contractId) throws WafException {
        Contract contract = findByContractId(contractId);
        if (contract.getIsAlteration() == 0) {
            return;
        }
        List<Order> orders = orderService.findOrdersByContractId(contractId);
        BigDecimal total = new BigDecimal(0f);
        for (Order order : orders) {
            total = total.add(new BigDecimal(order.getTotalAmount()));
        }

        List<Order> originalOrders = orderService.findOrdersByContractId(contract.getAlterTargetId());
        BigDecimal originalTotal = new BigDecimal(0f);
        for (Order originalOrder : originalOrders) {
            originalTotal = originalTotal.add(new BigDecimal(originalOrder.getTotalAmount()));
        }
        if (originalTotal.compareTo(total) > 0) {
            throw new ContractAmountLessThanOriginalException();
        }
    }


    // for internal call only, no validation
    private void putContractInEffect(String contractId) throws WafException {
        Contract contract = findByContractId(contractId);

        if (contract.getIsAlteration() > 0) {
            //合同变更处理逻辑
            //新增订单的首付款计入账单
            billService.countContractFirstInstallmentIntoBill(contractId);
            createUpSellingInvoice(contractId);
            refund(contract);
            //garfield pdf and invoice
            transferPdf(contractId,contract);
           
            //garfield 
            writeBackToOriginalContract(contractId);
            putSiteDraftsInEffect(contractId);
            markContractAsNotChanging(contract.getAlterTargetId());
            contractRepository.delete(contractId);
            contractRepository.flush();
        } else {
            // 普通新开合同处理逻辑
            contract.setState(CommonState.IN_EFFECT.toString());
            contractRepository.save(contract);
            contractRepository.flush();

            List<Order> orders = orderRepository.findByContractId(contractId);
            for (Order order : orders) {
                orderService.approve(order.getPid());
            }
            putSiteDraftsInEffect(contractId);
            billService.countContractFirstInstallmentIntoBill(contractId);
            //create invoice by garfield
            createNormalInvoice(contractId);
        }
    }

    private void putTelecomContractInEffect(String contractId) throws WafException {
        Contract contract = findByContractId(contractId);

        if (contract.getIsAlteration() > 0) {
            //合同变更处理逻辑
            //新增订单的首付款计入账单
            billService.countContractFirstInstallmentIntoBill(contractId);
            createUpSellingInvoice(contractId);
            refund(contract);
            //garfield pdf and invoice
            transferPdf(contractId,contract);

            //garfield
            writeBackToOriginalContract(contractId);
            putSiteDraftsInEffect(contractId);
            markContractAsNotChanging(contract.getAlterTargetId());
            contractRepository.delete(contractId);
            contractRepository.flush();
        } else {
            // 普通新开合同处理逻辑
            contract.setState(CommonState.IN_EFFECT.toString());
            contractRepository.save(contract);
            contractRepository.flush();

            List<Order> orders = orderRepository.findByContractId(contractId);
            for (Order order : orders) {
                orderService.approveTelecom(order.getPid());
            }
            putSiteDraftsInEffect(contractId);
            billService.countContractFirstInstallmentIntoBill(contractId);
            //create invoice by garfield
            createNormalInvoice(contractId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void writeBackToOriginalContract(String alterationContractId) throws WafException {
        Contract contract = findByContractId(alterationContractId);
        if (contract.getIsAlteration() <= 0) {
            return;
        }

        String originalContractId = contract.getAlterTargetId();
        Contract originalContract = findByContractId(originalContractId);
        originalContract.setDisplayName(contract.getDisplayName());
        originalContract.setSalesman(contract.getSalesman());
        originalContract.setComments(contract.getComments());
        contractRepository.save(originalContract);
        List<Order> orders = orderService.findOrdersByContractId(alterationContractId);
        for (Order order : orders) {
            orderService.writeBackToOriginalContract(order.getPid(), originalContractId);
        }
    }


    private void doDecline(String contractId) throws WafException {
        Contract contract = findByContractId(contractId);
        if (CommonState.valueOf(contract.getState()) == CommonState.IN_EFFECT) {
            throw new ContractIsInEffectException();
        }
        contract.setState(CommonState.DRAFT.toString());
        contractRepository.save(contract);

        List<Order> orders = orderRepository.findByContractId(contractId);
        for (Order order : orders) {
            orderService.decline(order.getPid());
        }
    }


    private void putSiteDraftsInEffect(String contractId) throws WafException {
        List<WebExSiteDraft> drafts = webExSiteService.findSiteDraftsOfContract(contractId);
        if (drafts != null && drafts.size() > 0) {
            for (WebExSiteDraft draft : drafts) {
                webExSiteService.putDraftInEffect(draft.getId());
            }
        }
    }


    private void markContractAsNotChanging(String id) throws WafException {
        Contract contract = findByContractId(id);
        contract.setIsChanging(0);
        contractRepository.save(contract);
    }


    public ContractRefunds calcTotalRefundAmount(Contract alterationContract) throws WafException {
        List<BillItem> refunds = billService.calcRefund(alterationContract.getPid());
        BigDecimal totalWebExRefundAmount = new BigDecimal(0);
        BigDecimal totalCcRefundAmount = new BigDecimal(0);
        for (BillItem refund : refunds) {
            if (refund != null) {
                try {
                    BillFormalDetail bill =
                            billService.findFormalBill(alterationContract.getCustomerId(), refund.getOrderId(), refund.getAccountPeriod(), refund.getFeeType());
                    BigDecimal currentAmount = new BigDecimal(bill.getAmount());
                    BigDecimal currentUnpaidAmount = new BigDecimal(bill.getUnpaidAmount());
                    BigDecimal currentPaidAmount = currentAmount.subtract(currentUnpaidAmount);
                    BigDecimal newAmount = currentAmount.add(refund.getAmount());
                    if (currentPaidAmount.compareTo(newAmount) > 0) {
                        BigDecimal refundAmount = currentPaidAmount.subtract(newAmount);

                        if (FeeType.isWeBexFeeType(refund.getFeeType())) {
                            totalWebExRefundAmount = totalWebExRefundAmount.add(refundAmount);
                        } else if (FeeType.isCcFeeType(refund.getFeeType())) {
                            totalCcRefundAmount = totalCcRefundAmount.add(refundAmount);
                        }
                    }
                } catch (FormalBillNotFoundException ignore) {
                    ignore.printStackTrace();
                }
            }
        }
        return new ContractRefunds(totalWebExRefundAmount, totalCcRefundAmount);
    }

    private void refund(Contract alterationContract) throws WafException {
        List<BillItem> refunds = billService.calcRefund(alterationContract.getPid());
        for (BillItem refund : refunds) {
            if (refund != null) {
                AgentType agentType = getAgentTypeOfContract(alterationContract.getPid());
                String refundCustomerId = alterationContract.getCustomerId();
                if (agentType == AgentType.RESELLER2) {
                    refundCustomerId = alterationContract.getAgentId();
                }
                contractAlterationRefundFromBill(refundCustomerId, refund);
            }
        }
    }

    private static final String DEFAULT_REFUND_COMMENTS = "业务变更退款";

    private void contractAlterationRefundFromBill(String customerId, BillItem refundItem)
            throws FormalBillNotFoundException, RefundAmountExceedsOriginalAmountException, TempBillNotFoundException {
        if (refundItem.getFeeType() == FeeType.WEBEX_FIRST_INSTALLMENT || refundItem.getFeeType() == FeeType.CC_FIRST_INSTALLMENT) {
            billService.tuneBill(customerId, refundItem.getOrderId(), refundItem.getAccountPeriod(), refundItem.getFeeType(), refundItem.getAmount(), DEFAULT_REFUND_COMMENTS);
        } else {
            try {
                billService.tuneBill(customerId, refundItem.getOrderId(), refundItem.getAccountPeriod(), refundItem.getFeeType(), refundItem.getAmount(), DEFAULT_REFUND_COMMENTS);
            } catch (FormalBillNotFoundException e) {
                try {
                    billService.tuneTempBill(customerId, refundItem.getAccountPeriod(), refundItem.getFeeType(), refundItem.getAmount(), DEFAULT_REFUND_COMMENTS);
                } catch (TempBillNotFoundException ignore) {
                }
            }
        }
    }

    private void assertSalesmanValid(String salesmanId) throws WafException {
        Salesman salesman = salesmanService.findById(salesmanId);
        if (salesman.getEnabled() != 1) {
            throw new InvalidSalesmanAssignedException();
        }
    }

    private void assertAgentValid(String agentId) throws NotAnAgentException {
        if (StringUtils.isNotBlank(agentId)) {
            Customer mayBeAgent;
            try {
                mayBeAgent = customerService.findByCustomerId(agentId);
            } catch (WafException e) {
                throw new NotAnAgentException();
            }

            AgentType agentType = AgentType.valueOf(mayBeAgent.getAgentType());
            if (agentType != AgentType.AGENT && agentType != AgentType.RESELLER2) {
                throw new NotAnAgentException();
            }
        }
    }


    public void createNormalInvoice(String contractId)  throws  WafException{
    	 List<OrderBean> orders = orderService.findOrderBeansByContractId(contractId);
    	 AgentType agentType = getAgentTypeOfContract(contractId);
    	 Contract contract = findByContractId(contractId);
         String agentId=contract.getAgentId();
         for (OrderBean order : orders) {            	
         	invoiceForNewOrder(order, agentType,agentId);
         }
    	
    }

    public void createUpSellingInvoice(String contractId) throws WafException {


        List<Order> orders = orderService.findOrdersByContractId(contractId);
        AgentType agentType = getAgentTypeOfContract(contractId);
   	 	Contract contract = findByContractId(contractId);
        String agentId=contract.getAgentId();

        for (Order order : orders) {
            if (StringUtils.isNotBlank(order.getOriginalOrderId())) {
                try {
                	Date  terminatedOn= DateUtil.toDate(order.getEffectiveEndDate());
                    BillItem billItem = billService.calcRefund(order.getPid(), terminatedOn);
                    float amount = 0;
                    if (billItem != null) {
                        amount = billItem.getAmount().floatValue();
                    }
                    //result.add(billItem);
                    if (amount < 0) {
                        OrderBean orderBean = OrderBeanCache.get(order);
                        OrderBean originalOrder = orderService.findOrderBeanById(order.getOriginalOrderId());
                        String customerId = "";
                        if (agentType == AgentType.RESELLER2) {
                    		customerId = agentId;
                        } else {
                        	customerId = order.getCustomerId();
                        }
                        
                        Customer customer = customerRepository.findOne(customerId);
                        String code = customer.getCode();
                        String invoiceName = code + "-" + DateUtil.formatInvoiceNameDate(new Date()).substring(0, 6) + "01" + "-1-C";
                        String orderId = order.getOriginalOrderId();
                        //String invoiceMemo="12";
                        String invoiceMemo = code + "-" + DateUtil.formatInvoiceNameDate(orderBean.getStartDate()).substring(0, 6) + "01" + "-1";
                        ;
                        String documentDate = DateUtil.formatShortInvoiceDate(new Date());
                        
                        FeeCalculator calc = FeeCalculatorManager.getFeeCalculator(orderBean);
                        FeeCalculator origCalc = FeeCalculatorManager.getFeeCalculator(originalOrder);
                      
                        //计算出原订单到中止日期，上一个付费时间点已经支付的月数
                        int alreadyPaidMonths = origCalc.calcRecentPaidMonthsBefore(terminatedOn);
                        //计算出原订单到中止日期，从上一个付费时间点开始理应支付的月数
                        int shouldPayMonths = calc.calcRecentPaidMonthsBefore(terminatedOn);
                        int contractTerm=alreadyPaidMonths-shouldPayMonths;                 
                        
                        String  startDate =   DateUtil.formatShortInvoiceDate(terminatedOn);
                        insertUpsellingInvoiceData(invoiceName, invoiceMemo, documentDate, code, amount, orderId,contractTerm,startDate,billItem.getFeeType().getValue());
                    }
                } catch (ParseException ignore) {
                }
            } else {
            	
                OrderBean orderBean = OrderBeanCache.get(order);
                invoiceForNewOrder(orderBean,agentType,agentId);
            }
        }
        //return result;

        //合同变更处理逻辑


    }

    private void invoiceForNewOrder(OrderBean order,AgentType agentType,String agentId) {
    	String customerId ="";
    	if (agentType == AgentType.RESELLER2) {
    		customerId = agentId;
        } else {
        	customerId = order.getCustomerId();
        }
        
        Customer customer = customerRepository.findOne(customerId);
        String code = customer.getCode();
        String partName1= code + "-" + DateUtil.formatInvoiceNameDate(order.getStartDate()).substring(0, 6)+"01";
        int countIndex1=2+billService.findInvoiceCountByName(partName1).size();
        String invoiceName = partName1+"-"+countIndex1;
        String partName2= code + "-" + DateUtil.formatInvoiceNameDate(DateUtil.addOneMonth(order.getStartDate())).substring(0, 6) + "01";
        int countIndex2=2+billService.findInvoiceCountByName(partName2).size();
        String invoiceName2 = partName2+"-"+countIndex2;
        String partName3= code + "-" + DateUtil.formatInvoiceNameDate(DateUtil.addTwoMonth(order.getStartDate())).substring(0, 6) + "01";
        int countIndex3=2+billService.findInvoiceCountByName(partName3).size();
        String invoiceName3 = partName3+"-"+countIndex3;
        String startDate = DateUtil.formatShortInvoiceDate(order.getStartDate());
        String productDesc = "";
        String productType = "";
        String documentDate = DateUtil.formatShortInvoiceDate(new Date());
        String orderId = order.getId();
        float beforeTaxValue=0;
        try {
            Order orderDetail = orderService.findOrderById(orderId);
            productDesc = productService.findById(orderDetail.getProductId()).getDisplayName();
            beforeTaxValue=orderDetail.getFirstInstallment().floatValue();
            productType = productDesc;
        } catch (OrderNotFoundException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        } catch (ProductNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        AbstractChargeScheme scheme = order.getChargeScheme();
        if (scheme instanceof PSTNSinglePacket4MultiSites) {
            PSTNSinglePacket4MultiSites pstnSinglePacket4MultiSites = (PSTNSinglePacket4MultiSites) scheme;
            int contractTerm = pstnSinglePacket4MultiSites.getMonths();
            float amount = pstnSinglePacket4MultiSites.getPacketPrice();
            int unit = 1;
            int interval = 1;
            String revenueType = Constants.AUDIO_PACKAGE_FEE;
            insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


        } else if (scheme instanceof PSTNPersonalPacket) {        	
            int contractTerm = 12;
            float amount = 1;
            int unit = 1;
            int interval = 1;
            String revenueType = Constants.PSTN_PERSONAL_PACKET;
            insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


        } else if (scheme instanceof PSTNSinglePacket4AllSites) {
            PSTNSinglePacket4AllSites pstnSinglePacket4AllSites = (PSTNSinglePacket4AllSites) scheme;
            int contractTerm = pstnSinglePacket4AllSites.getMonths();
            float amount = pstnSinglePacket4AllSites.getPacketPrice();
            int unit = 1;
            int interval = 1;
            String revenueType = Constants.AUDIO_PACKAGE_FEE;
            insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


        } else if (scheme instanceof MiscCharge) {
            MiscCharge miscCharge = (MiscCharge) scheme;
            int contractTerm = 1;
            float amount = miscCharge.getTotalPrice();
            int unit = 1;
            int interval = 1;
            String revenueType = Constants.SITE_CONFIGURATION_FEE;
            insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


        } else {
            if (order.getPayInterval().getInterval() == -1) {

                int interval = 1;
                if (scheme instanceof WebExStorageMonthlyPay) {
                    WebExStorageMonthlyPay webExStorageMonthlyPay = (WebExStorageMonthlyPay) scheme;
                    int contractTerm = webExStorageMonthlyPay.getMonth();
                    int unit = webExStorageMonthlyPay.getStorageSize();
                    float amount = contractTerm * unit * webExStorageMonthlyPay.getUnitPrice();
                    String revenueType = Constants.STORAGE_FEE;
                    insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                            productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


                } if (scheme instanceof WebExStorageMonthlyOverflowPay) {
                	WebExStorageMonthlyOverflowPay webExStorageMonthlyOverflowPay = (WebExStorageMonthlyOverflowPay) scheme;
                    int contractTerm = webExStorageMonthlyOverflowPay.getMonth();
                    int unit = webExStorageMonthlyOverflowPay.getStorageSize();
                    float amount = contractTerm * unit * webExStorageMonthlyOverflowPay.getUnitPrice();
                    String revenueType = Constants.STORAGE_FEE;
                    insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                            productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


                } else if (scheme instanceof WebExConfMonthlyPayByHosts) {
                    WebExConfMonthlyPayByHosts webExConfMonthlyPayByHosts = (WebExConfMonthlyPayByHosts) scheme;

                    int contractTerm = webExConfMonthlyPayByHosts.getMonths();
                    int unit = webExConfMonthlyPayByHosts.getHosts();
                    float amount = contractTerm * unit * webExConfMonthlyPayByHosts.getUnitPrice();
                    String revenueType = Constants.NETWORK_SUBSCRIPTION_FEE;
                    insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                            productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

                } else if (scheme instanceof WebExConfMonthlyPayByPorts) {
                    WebExConfMonthlyPayByPorts webExConfMonthlyPayByPorts = (WebExConfMonthlyPayByPorts) scheme;
                    int contractTerm = webExConfMonthlyPayByPorts.getMonths();
                    int unit = webExConfMonthlyPayByPorts.getPorts();
                    float amount = contractTerm * unit * webExConfMonthlyPayByPorts.getUnitPrice();
                    String revenueType = Constants.NETWORK_SUBSCRIPTION_FEE;
                    insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                            productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


                }else if (scheme instanceof WebExConfMonthlyPayByTotalAttendees) {
                	WebExConfMonthlyPayByTotalAttendees webExConfMonthlyPayByTotalAttendees = (WebExConfMonthlyPayByTotalAttendees) scheme;
                    int contractTerm = webExConfMonthlyPayByTotalAttendees.getMonths();
                    int unit = webExConfMonthlyPayByTotalAttendees.getPorts();
                    float amount = contractTerm * unit * webExConfMonthlyPayByTotalAttendees.getUnitPrice();
                    String revenueType = Constants.NETWORK_SUBSCRIPTION_FEE;
                    insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                            productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


                } else if (scheme instanceof PSTNMonthlyPacketCharge) {
                    PSTNMonthlyPacketCharge pstnMonthlyPacketCharge = (PSTNMonthlyPacketCharge) scheme;
                    int contractTerm = pstnMonthlyPacketCharge.getMonths();
                    int unit = 1;
                    float amount = contractTerm * unit * pstnMonthlyPacketCharge.getPricePerMonth();
                    String revenueType = Constants.PSTN_FEE;
                    insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                            productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


                } else if (scheme instanceof WebExCmrMonthlyPay) {
                    WebExCmrMonthlyPay webExCmrMonthlyPay = (WebExCmrMonthlyPay) scheme;
                    int contractTerm = webExCmrMonthlyPay.getMonth();
                    int unit = 1;
                    float amount = contractTerm * unit * webExCmrMonthlyPay.getUnitPrice();
                    String revenueType = Constants.CMR_FEE;
                    insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                            productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


                }
            } else if (order.getPayInterval().getInterval() == 1) {

                String revenueType = Constants.NETWORK_SUBSCRIPTION_FEE;
                if (scheme instanceof WebExConfMonthlyPayByHosts) {
                    WebExConfMonthlyPayByHosts webExConfMonthlyPayByHosts = (WebExConfMonthlyPayByHosts) scheme;

                    int contractTerm = webExConfMonthlyPayByHosts.getMonths();
                    int interval = contractTerm;
                    int unit = webExConfMonthlyPayByHosts.getHosts();
                    float amount = unit * webExConfMonthlyPayByHosts.getUnitPrice();
                    insertInvoiceDateForIntervalOne(invoiceName,invoiceName2,invoiceName3, documentDate, code, revenueType, startDate,
        					productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

                } else if (scheme instanceof WebExConfMonthlyPayByPorts) {
                    WebExConfMonthlyPayByPorts webExConfMonthlyPayByPorts = (WebExConfMonthlyPayByPorts) scheme;
                    int contractTerm = webExConfMonthlyPayByPorts.getMonths();
                    int interval = contractTerm;
                    int unit = webExConfMonthlyPayByPorts.getPorts();
                    float amount = unit * webExConfMonthlyPayByPorts.getUnitPrice();
                    insertInvoiceDateForIntervalOne(invoiceName,invoiceName2,invoiceName3, documentDate, code, revenueType, startDate,
        					productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

                }else if (scheme instanceof WebExConfMonthlyPayByTotalAttendees) {
                	WebExConfMonthlyPayByTotalAttendees webExConfMonthlyPayByTotalAttendees = (WebExConfMonthlyPayByTotalAttendees) scheme;
                    int contractTerm = webExConfMonthlyPayByTotalAttendees.getMonths();
                    int interval = contractTerm;
                    int unit = webExConfMonthlyPayByTotalAttendees.getPorts();
                    float amount = unit * webExConfMonthlyPayByTotalAttendees.getUnitPrice();
                    insertInvoiceDateForIntervalOne(invoiceName,invoiceName2,invoiceName3, documentDate, code, revenueType, startDate,
        					productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

                } else if (scheme instanceof WebExStorageMonthlyPay) {
                    WebExStorageMonthlyPay webExStorageMonthlyPay = (WebExStorageMonthlyPay) scheme;
                    int contractTerm = webExStorageMonthlyPay.getMonth();
                    int interval = contractTerm;
                    int unit = webExStorageMonthlyPay.getStorageSize();
                    float amount = unit * webExStorageMonthlyPay.getUnitPrice();
                    insertInvoiceDateForIntervalOne(invoiceName,invoiceName2,invoiceName3, documentDate, code, revenueType, startDate,
        					productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

                }else if (scheme instanceof WebExStorageMonthlyOverflowPay) {
                	WebExStorageMonthlyOverflowPay webExStorageMonthlyOverflowPay = (WebExStorageMonthlyOverflowPay) scheme;
                    int contractTerm = webExStorageMonthlyOverflowPay.getMonth();
                    int interval = contractTerm;
                    int unit = webExStorageMonthlyOverflowPay.getStorageSize();
                    float amount = unit * webExStorageMonthlyOverflowPay.getUnitPrice();
                    insertInvoiceDateForIntervalOne(invoiceName,invoiceName2,invoiceName3, documentDate, code, revenueType, startDate,
        					productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

                }else if (scheme instanceof WebExConfMonthlyPayPersonal) {
                	WebExConfMonthlyPayPersonal WebExConfMonthlyPayPersonal = (WebExConfMonthlyPayPersonal) scheme;
                    int contractTerm = WebExConfMonthlyPayPersonal.getMonths();
                    int interval = contractTerm;
                    int unit = 1;
                    float amount = beforeTaxValue;
                    insertInvoiceDateForIntervalOne(invoiceName,invoiceName2,invoiceName3, documentDate, code, revenueType, startDate,
        					productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

                } else if (scheme instanceof PSTNMonthlyPacketCharge) {
                    PSTNMonthlyPacketCharge pstnMonthlyPacketCharge = (PSTNMonthlyPacketCharge) scheme;
                    int contractTerm = pstnMonthlyPacketCharge.getMonths();
                    int interval = contractTerm;
                    revenueType = Constants.PSTN_FEE;
                    int unit = 1;
                    float amount = pstnMonthlyPacketCharge.getPricePerMonth();
                    insertInvoiceDateForIntervalOne(invoiceName,invoiceName2,invoiceName3, documentDate, code, revenueType, startDate,
        					productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

                } else if (scheme instanceof WebExCmrMonthlyPay) {
                    WebExCmrMonthlyPay webExCmrMonthlyPay = (WebExCmrMonthlyPay) scheme;
                    int contractTerm = webExCmrMonthlyPay.getMonth();
                    int interval = contractTerm;
                    revenueType = Constants.CMR_FEE;
                    int unit = 1;
                    float amount = webExCmrMonthlyPay.getUnitPrice()*webExCmrMonthlyPay.getMonth()*webExCmrMonthlyPay.getPorts();
                    insertInvoiceDateForIntervalOne(invoiceName,invoiceName2,invoiceName3, documentDate, code, revenueType, startDate,
        					productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);
                    

                }

            } else if (order.getPayInterval().getInterval() == 3) {
                int month = 3;
                if(Constants.SHLXCODE.equals(code)){
	                insertNetworkFee(order, month, invoiceName3, documentDate, code, startDate,
	                        productDesc, productType, orderId,beforeTaxValue);
                }else{
                	insertNetworkFee(order, month, invoiceName, documentDate, code, startDate,
	                        productDesc, productType, orderId,beforeTaxValue);
                }


            } else if (order.getPayInterval().getInterval() == 6) {
                int month = 6;
                if(Constants.SHLXCODE.equals(code)){
	                insertNetworkFee(order, month, invoiceName3, documentDate, code, startDate,
	                        productDesc, productType, orderId,beforeTaxValue);
                }else{
                	insertNetworkFee(order, month, invoiceName, documentDate, code, startDate,
	                        productDesc, productType, orderId,beforeTaxValue);
                }

            } else if (order.getPayInterval().getInterval() == 12) {
                int month = 12;
                if(Constants.SHLXCODE.equals(code)){
	                insertNetworkFee(order, month, invoiceName3, documentDate, code, startDate,
	                        productDesc, productType, orderId,beforeTaxValue);
                }else{
                	insertNetworkFee(order, month, invoiceName, documentDate, code, startDate,
	                        productDesc, productType, orderId,beforeTaxValue);
                }

            }
        }
    }

    public void insertInvoiceData(String invoiceName, String documentDate, String code, String revenueType, String startDate,
                                   String productDesc, String productType, int interval, int unit, int contractTerm, float amount, String orderId,float beforeTaxValue) {
        amount = beforeTaxValue / Constants.TAXRATE;
        BillExport billExport = new BillExport();
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
        billExport.setFeeType(Constants.FEETYPE_WEBEX_FIRST_INSTALLMENT);
        billExportRepository.save(billExport);
    }

    private void insertUpsellingInvoiceData(String invoiceName, String invoiceMemo, String documentDate, String code, float amount, String orderId,int contractTerm,String startDate,int feeType) {
        float beforeTaxValue=amount;
    	amount = amount / Constants.TAXRATE;
        BillExport billExport = new BillExport();
        billExport.setInvoiceName(invoiceName);
        billExport.setInvoiceMemo(invoiceMemo);
        billExport.setDocumentType(Constants.CREDITNOTE);
        billExport.setDocumentDate(documentDate);
        billExport.setCustomerId(code);
        billExport.setCreateDate(DateUtil.now());
        billExport.setRevenueType(Constants.CREDITNOTE);
        billExport.setSalesChannel(Constants.DIRECT);
        billExport.setContractCommence(startDate);
        billExport.setProductDesc(Constants.CREDITNOTE);
        billExport.setProductType(Constants.CREDITNOTE);
        billExport.setPaymentInterval(1);
        billExport.setUnit(0);
        billExport.setContractTerm(contractTerm);
        billExport.setCreditType(Constants.CREDITTYPE);
        billExport.setNetInvoicedAmount(amount);
        billExport.setCreditAmount(amount);
        billExport.setOrderId(orderId);
        billExport.setBeforeTaxValue(beforeTaxValue);
        billExport.setFeeType(feeType);
        billExportRepository.save(billExport);
    }

    private void insertNetworkFee(OrderBean order, int month, String invoiceName, String documentDate, String code, String startDate,
                                  String productDesc, String productType, String orderId,float beforeTaxValue) {
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
            if (contractTerm < month) {
                amount = contractTerm * unit * webExConfMonthlyPayByHosts.getUnitPrice();
            } else {
                amount = month * unit * webExConfMonthlyPayByHosts.getUnitPrice();
            }

            insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

        } else if (scheme instanceof WebExConfMonthlyPayByPorts) {
            WebExConfMonthlyPayByPorts webExConfMonthlyPayByPorts = (WebExConfMonthlyPayByPorts) scheme;
            int contractTerm = webExConfMonthlyPayByPorts.getMonths();
            int unit = webExConfMonthlyPayByPorts.getPorts();
            int interval = (int) Math.ceil((float) contractTerm / (float) month);
            if (contractTerm < month) {
                amount = contractTerm * unit * webExConfMonthlyPayByPorts.getUnitPrice();
            } else {
                amount = month * unit * webExConfMonthlyPayByPorts.getUnitPrice();
            }

            insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


        }else if (scheme instanceof WebExConfMonthlyPayByTotalAttendees) {
        	WebExConfMonthlyPayByTotalAttendees webExConfMonthlyPayByTotalAttendees = (WebExConfMonthlyPayByTotalAttendees) scheme;
            int contractTerm = webExConfMonthlyPayByTotalAttendees.getMonths();
            int unit = webExConfMonthlyPayByTotalAttendees.getPorts();
            int interval = (int) Math.ceil((float) contractTerm / (float) month);
            if (contractTerm < month) {
                amount = contractTerm * unit * webExConfMonthlyPayByTotalAttendees.getUnitPrice();
            } else {
                amount = month * unit * webExConfMonthlyPayByTotalAttendees.getUnitPrice();
            }

            insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


        } else if (scheme instanceof WebExStorageMonthlyPay) {
            WebExStorageMonthlyPay webExStorageMonthlyPay = (WebExStorageMonthlyPay) scheme;
            int contractTerm = webExStorageMonthlyPay.getMonth();
            int unit = webExStorageMonthlyPay.getStorageSize();
            int interval = (int) Math.ceil((float) contractTerm / (float) month);
            if (contractTerm < month) {
                amount = contractTerm * unit * webExStorageMonthlyPay.getUnitPrice();
            } else {
                amount = month * unit * webExStorageMonthlyPay.getUnitPrice();
            }

            insertInvoiceData(invoiceName, documentDate, code, revenueTypeStorage, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


        }else if (scheme instanceof WebExStorageMonthlyOverflowPay) {
        	WebExStorageMonthlyOverflowPay webExStorageMonthlyOverflowPay = (WebExStorageMonthlyOverflowPay) scheme;
            int contractTerm = webExStorageMonthlyOverflowPay.getMonth();
            int unit = webExStorageMonthlyOverflowPay.getStorageSize();
            int interval = (int) Math.ceil((float) contractTerm / (float) month);
            if (contractTerm < month) {
                amount = contractTerm * unit * webExStorageMonthlyOverflowPay.getUnitPrice();
            } else {
                amount = month * unit * webExStorageMonthlyOverflowPay.getUnitPrice();
            }

            insertInvoiceData(invoiceName, documentDate, code, revenueTypeStorage, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


        } else if (scheme instanceof PSTNMonthlyPacketCharge) {
            PSTNMonthlyPacketCharge pstnMonthlyPacketCharge = (PSTNMonthlyPacketCharge) scheme;
            int contractTerm = pstnMonthlyPacketCharge.getMonths();
            int unit = 1;
            int interval = (int) Math.ceil((float) contractTerm / (float) month);
            if (contractTerm < month) {
                amount = contractTerm * unit * pstnMonthlyPacketCharge.getPricePerMonth();
            } else {
                amount = month * unit * pstnMonthlyPacketCharge.getPricePerMonth();
            }

            insertInvoiceData(invoiceName, documentDate, code, revenueTypePSTN, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);


        } else if (scheme instanceof WebExCmrMonthlyPay) {
            WebExCmrMonthlyPay webExCmrMonthlyPay = (WebExCmrMonthlyPay) scheme;
            int contractTerm = webExCmrMonthlyPay.getMonth();
            int unit = 1;
            int interval = (int) Math.ceil((float) contractTerm / (float) month);
            if (contractTerm < month) {
                amount = contractTerm * unit * webExCmrMonthlyPay.getUnitPrice();
            } else {
                amount = month * unit * webExCmrMonthlyPay.getUnitPrice();
            }

            insertInvoiceData(invoiceName, documentDate, code, revenueTypeCMR, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

        }else if (scheme instanceof WebExConfMonthlyPayPersonal) {
        	WebExConfMonthlyPayPersonal WebExConfMonthlyPayPersonal = (WebExConfMonthlyPayPersonal) scheme;
            int contractTerm = WebExConfMonthlyPayPersonal.getMonths();
            int unit = 1;
            int interval = (int) Math.ceil((float) contractTerm / (float) month);
            

            insertInvoiceData(invoiceName, documentDate, code, revenueTypePersonal, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createPdf(ContractPdf contractPdf, ByteArrayOutputStream baos) throws WafException {

        ContractPdf newContractPdf = contractPdfRepository.save(contractPdf);
        ContractPdfBean contractPdfBean = new ContractPdfBean();
        contractPdfBean.setCreatedAt(new Date());
        contractPdfBean.setId(newContractPdf.getId());
        contractPdfBean.setPdfContent(baos.toByteArray());
        contractPdfEntityRepository.save(contractPdfBean);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createPdfLargeSize(ContractPdf contractPdf, MultipartFile file) throws WafException, IOException {
        String fileName = contractPdf.getPdfName();
        String contentType = "application/octet-stream";
        GridFSFile storeThumb = operations.store(file.getInputStream(), "thumb" + fileName, contentType);
        String pdfid = storeThumb.getId().toString();
        contractPdf.setId(pdfid);
        contractPdfRepository.save(contractPdf);
    }


    public List<ContractPdf> listContractPdf(String contractId) {
        return contractPdfRepository.findByContractId(contractId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deletePdf(String pdfId) {
        contractPdfRepository.delete(pdfId);
        operations.delete(new Query(Criteria.where("_id").is(pdfId)));

        //contractPdfEntityRepository.delete(pdfId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transferPdf(String contractId, Contract contract) {
        String originalContractId = contract.getAlterTargetId();
        List<ContractPdf> pdfList = contractPdfRepository.findByContractId(contractId);
        for (ContractPdf contractPdf : pdfList) {
            contractPdf.setContractId(originalContractId);
            contractPdfRepository.save(contractPdf);
            contractPdfRepository.flush();
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deletePdfByContractId(String contractId) {
        List<ContractPdf> pdfList = contractPdfRepository.findByContractId(contractId);
        for (ContractPdf contractPdf : pdfList) {
            contractPdfRepository.delete(contractPdf);
            operations.delete(new Query(Criteria.where("_id").is(contractPdf.getId())));
        }

    }
    
    
    private void  insertInvoiceDateForIntervalOne(String invoiceName, String invoiceName2,String invoiceName3,String documentDate, String code, String revenueType, String startDate,
            String productDesc, String productType, int interval, int unit, int contractTerm, float amount, String orderId,float beforeTaxValue){
    	if (contractTerm == 1 ||Constants.SHLXCODE.equals(code)) {
        	if(Constants.SHLXCODE.equals(code)){
        		insertInvoiceData(invoiceName3, documentDate, code, revenueType, startDate,
    					productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);
        		
        	}else{
        		insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
        				productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);
        	}
        } else {
            insertInvoiceData(invoiceName, documentDate, code, revenueType, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);
            insertInvoiceData(invoiceName2, documentDate, code, revenueType, startDate,
                    productDesc, productType, interval, unit, contractTerm, amount, orderId,beforeTaxValue);

        }
    	
    }
}
