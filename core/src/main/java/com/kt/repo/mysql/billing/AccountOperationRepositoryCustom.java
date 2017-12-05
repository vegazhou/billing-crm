package com.kt.repo.mysql.billing;

import com.kt.biz.billing.AccountPeriod;
import com.kt.entity.mysql.billing.AccountOperation;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/10/21.
 */
public interface AccountOperationRepositoryCustom {

    List<AccountOperation> findCustomerPaymentsByAccountPeriod(AccountPeriod accountPeriod);

    List<AccountOperation> findUnsyncedPayments();
}
