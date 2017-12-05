package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.BillingTask;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2016/4/13.
 */
public interface BillingTaskRepository extends JpaRepository<BillingTask, String> {

    BillingTask findFirstByCustomerIdAndAccountPeriod(String customerId, String accountPeriod);
}
