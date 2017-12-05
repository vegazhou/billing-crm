package com.kt.repo.mysql.billing;


import com.kt.entity.mysql.billing.BillFormalDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/6/16.
 */
public interface BillFormalDetailRepository extends JpaRepository<BillFormalDetail, Long>, BillFormalDetailRepositoryCustom {

    BillFormalDetail findOneByCustomerIdAndOrderIdAndAccountPeriodAndFeeType(String customerId, String orderId, String accountPeriod, int feeType);

    BillFormalDetail findOneByOrderIdAndAccountPeriodAndFeeType(String orderId, String accountPeriod, int feeType);

    List<BillFormalDetail> findByOrderIdAndFeeType(String orderId, int feeType);

    List<BillFormalDetail> findByFeeTypeOrderByAccountPeriodAsc(int feeType);

    List<BillFormalDetail> findByCustomerIdAndAccountPeriodAndFeeType(String customerId, String accountPeriod, int feeType);

    void deleteByCustomerIdAndAccountPeriodAndFeeTypeNot(String customerId, String accountPeriod, int feeType);

    List<BillFormalDetail> findByCustomerIdAndAccountPeriodAndFeeTypeNot(String customerId, String accountPeriod, int feeType);

    void deleteByOrderId(String orderId);

}
