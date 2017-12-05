package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {

    CustomerAccount findByCustomerIdAndAccountType(String customerId, String accountType);
}
