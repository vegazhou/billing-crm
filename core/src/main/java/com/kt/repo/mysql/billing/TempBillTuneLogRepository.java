package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.TempBillTuneLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2016/5/4.
 */
public interface TempBillTuneLogRepository extends JpaRepository<TempBillTuneLog, Long> {
}
