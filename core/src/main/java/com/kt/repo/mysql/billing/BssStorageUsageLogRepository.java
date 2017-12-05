package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.BssStorageUsageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/4/14.
 */
@Repository
public interface BssStorageUsageLogRepository extends JpaRepository<BssStorageUsageLog, String> {
    BssStorageUsageLog getByCustomerIdAndSiteNameAndAccountPeriod(String customerId, String siteName, int accountPeriod);
}
