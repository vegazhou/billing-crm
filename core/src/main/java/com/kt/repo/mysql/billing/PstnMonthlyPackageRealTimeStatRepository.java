package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.PstnMonthlyPackageRealTimeStat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2016/4/18.
 */
public interface PstnMonthlyPackageRealTimeStatRepository extends JpaRepository<PstnMonthlyPackageRealTimeStat, Long> {

    PstnMonthlyPackageRealTimeStat findByIdAndAccountPeriod(Long id, String accountPeriod);
}
