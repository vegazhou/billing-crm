package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.BillingLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2016/5/5.
 */
public interface BillingLogRepository extends JpaRepository<BillingLog, Long> {
}
