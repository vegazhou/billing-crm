package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.BillTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
public interface BillTempRepository extends JpaRepository<BillTemp, Long>, BillTempRepositoryCustom  {

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    BillTemp findByCustomerIdAndAccountPeriodAndFeeType(String customerId, String accountPeriod, int feeType);

    List<BillTemp> findByAccountPeriod(String accountPeriod);

    List<BillTemp> findByCustomerIdAndAccountPeriod(String customerId, String accountPeriod);

    void deleteByCustomerIdAndAccountPeriod(String customerId, String accountPeriod);

}
