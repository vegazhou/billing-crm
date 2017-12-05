package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.BillTempDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
public interface BillTempDetailRepository extends JpaRepository<BillTempDetail, Long> {

    BillTempDetail findFirstByOrderIdAndFeeType(String orderId, int feeType);

    BillTempDetail findFirstByOrderIdAndFeeTypeAndAccountPeriod(String orderId, int feeType, String accountPeriod);

    void deleteByCustomerIdAndAccountPeriod(String customerId, String accountPeriod);

    List<BillTempDetail> findByCustomerIdAndAccountPeriod(String customerId, String accountPeriod);
}
