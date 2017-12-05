package com.kt.service.billing;

import com.kt.biz.bean.CustomerAccountsBean;
import com.kt.biz.types.AccountOperationType;
import com.kt.biz.types.AccountType;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.AccountOperation;
import com.kt.entity.mysql.billing.CustomerAccount;
import com.kt.repo.mysql.billing.AccountOperationRepository;
import com.kt.repo.mysql.billing.AccountRepository;
import com.kt.repo.mysql.billing.CustomerAccountRepository;
import com.kt.service.ContractService;
import com.kt.service.OrderService;
import com.kt.service.SearchFilter;
import com.kt.session.PrincipalContext;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
@Service
public class AccountService {

    private static final String SYSTEM_OPERATOR = "SYSTEM";

    @Autowired
    CustomerAccountRepository customerAccountRepository;
    @Autowired
    AccountOperationRepository accountOperationRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ContractService contractService;
    @Autowired
    BillService billService;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CustomerAccount findOrCreate(String customerId, AccountType accountType) {
        CustomerAccount account = customerAccountRepository.findByCustomerIdAndAccountType(customerId, accountType.toString());
        if (account == null) {
            account = new CustomerAccount();
            account.setCustomerId(customerId);
            account.setAccountType(accountType.toString());
            account.setBalance(0f);
            account = customerAccountRepository.save(account);
        }
        return account;
    }


//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public synchronized BigDecimal payFromAccount(String customerId, String orderId, AccountType accountType, BigDecimal amount) {
//        CustomerAccount account = findOrCreate(customerId, accountType);
//        BigDecimal balance = new BigDecimal(account.getBalance());
//        BigDecimal realPaiAmount = amount.compareTo(balance) < 0 ? amount : balance;
//        adjust(customerId, accountType, realPaiAmount.multiply(new BigDecimal(-1)));
//        if (realPaiAmount.compareTo(new BigDecimal(0)) > 0) {
//            logAccountOperation(account.getId(), AccountOperationType.BILL_CHARGE, realPaiAmount.multiply(new BigDecimal(-1)), SYSTEM_OPERATOR, orderId);
//        }
//        return realPaiAmount;
//    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized BigDecimal payFromAccount(Long accountId, String orderId, BigDecimal amount) {
        CustomerAccount account = customerAccountRepository.findOne(accountId);
        BigDecimal balance = new BigDecimal(account.getBalance());
        BigDecimal realPaiAmount = amount.compareTo(balance) < 0 ? amount : balance;
        adjust(account.getCustomerId(), AccountType.valueOf(account.getAccountType()), realPaiAmount.multiply(new BigDecimal(-1)));
        if (realPaiAmount.compareTo(new BigDecimal(0)) > 0) {
            logAccountOperation(account.getId(), AccountOperationType.BILL_CHARGE, realPaiAmount.multiply(new BigDecimal(-1)), SYSTEM_OPERATOR, orderId);
        }
        return realPaiAmount;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void refundToAccount(String customerId, AccountType accountType, BigDecimal amount) {
        CustomerAccount account = findOrCreate(customerId, accountType);
        adjust(customerId, accountType, amount);
        logAccountOperation(account.getId(), AccountOperationType.REFUND, amount, SYSTEM_OPERATOR);
    }

    /**
     * 调整账户金额，但不负责记录LOG
     *
     * @param customerId   客户ID
     * @param accountType  账户类型
     * @param amount        发生变动的额度
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private synchronized void adjust(String customerId, AccountType accountType, BigDecimal amount) {
        CustomerAccount account = findOrCreate(customerId, accountType);

        BigDecimal newValue = amount.add(new BigDecimal(account.getBalance()));
        account.setBalance(MathUtil.scale(newValue.floatValue()));
        customerAccountRepository.save(account);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private synchronized void adjust(Long accountId, BigDecimal amount) {
        CustomerAccount account = customerAccountRepository.findOne(accountId);
        BigDecimal newValue = amount.add(new BigDecimal(account.getBalance()));
        account.setBalance(MathUtil.scale(newValue.floatValue()));
        customerAccountRepository.save(account);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void deposit(String customerId, AccountType accountType, BigDecimal amount) {
        CustomerAccount account = findOrCreate(customerId, accountType);
        adjust(customerId, accountType, amount);
        logAccountOperation(account.getId(), AccountOperationType.DEPOSIT, amount, PrincipalContext.getCurrentUserName());
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void depositTelecom(String customerId, AccountType accountType, BigDecimal amount) {
        CustomerAccount account = findOrCreate(customerId, accountType);
        adjust(customerId, accountType, amount);
        logAccountOperation(account.getId(), AccountOperationType.DEPOSIT, amount, "system");
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized void rollbackOperation(Long sequence) {
        AccountOperation operation = accountOperationRepository.findOne(sequence);
        operation.getCurrentAmount();
        CustomerAccount account = customerAccountRepository.findOne(operation.getAccountId());
        adjust(operation.getAccountId(), new BigDecimal(-operation.getCurrentAmount()));
    }





    private void logAccountOperation(Long accountId, AccountOperationType operationType, BigDecimal amount, String operator) {
        logAccountOperation(accountId, operationType, amount, operator, new Date(), null);
    }

    private void logAccountOperation(Long accountId, AccountOperationType operationType, BigDecimal amount, String operator, String reference) {
        logAccountOperation(accountId, operationType, amount, operator, new Date(), reference);
    }

    private void logAccountOperation(Long accountId, AccountOperationType operationType, BigDecimal amount, String operator, Date occurredDate, String reference) {
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAccountId(accountId);
        accountOperation.setOperationType(operationType.getValue());
        accountOperation.setCurrentAmount(MathUtil.scale(amount.doubleValue()));
        accountOperation.setPrimalAmount(MathUtil.scale(amount.doubleValue()));
        accountOperation.setOperateTime(DateUtil.formatDate(occurredDate));
        accountOperation.setOperator(operator);
        accountOperation.setReference(reference);
        accountOperationRepository.save(accountOperation);
    }

    public Page<CustomerAccountsBean> listAllByPageTemp(int curPage, SearchFilter search) {
        return accountRepository.paginate(curPage, search);

    }
    
    public Page<AccountOperation> listAllByPageForDetail(int curPage, SearchFilter search) {
        return accountRepository.listAllByPageForDetail(curPage, search);

    }

}
