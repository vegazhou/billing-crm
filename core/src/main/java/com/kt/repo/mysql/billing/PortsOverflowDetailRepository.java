package com.kt.repo.mysql.billing;

import com.kt.entity.mysql.billing.PortsOverflowDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
public interface PortsOverflowDetailRepository extends JpaRepository<PortsOverflowDetail, Long>, PortsOverflowDetailRepositoryCustom {
    @Modifying
    @Query(value = " select * from BB_PORTS_OVERFLOW_DETAILS \n" +
            " where CUSTOMER_ID = ?1 AND SITE_ID = ?2 AND BILL_PERIOD = ?3 and id in (\n" +
            " select s.id \n" +
            "from ( \n" +
            "    select id, row_number() over (partition by conf_id order by conf_id) as group_idx  \n" +
            "    from BB_PORTS_OVERFLOW_DETAILS WHERE CUSTOMER_ID = ?1 AND SITE_ID = ?2 AND BILL_PERIOD = ?3 \n" +
            ") s\n" +
            "where s.group_idx = 1\n" +
            " )", nativeQuery = true)
    List<PortsOverflowDetail> findMeetingsPortsByCustomerIdAndSiteIdAndBillPeriod(String customerId, int siteId, int billPeriod);
    @Query(value = "SELECT * FROM BB_PORTS_OVERFLOW_DETAILS WHERE CUSTOMER_ID=?1 AND SITE_ID=?2 AND BILL_PERIOD=?3 ", nativeQuery = true)
    List<PortsOverflowDetail> findPortsOverflowDetailByCustomerIdAndSiteIdAndBillPeriod(String customerId, int siteId, int billPeriod);
    PortsOverflowDetail  findFirstByCustomerIdAndSiteIdAndBillPeriod(String customerId, int siteId, int billPeriod);
    @Query(value = "SELECT COUNT(1)  FROM BB_PORTS_OVERFLOW_DETAILS WHERE CUSTOMER_ID=?1 AND SITE_ID=?2 AND BILL_PERIOD=?3 ", nativeQuery = true)
    int countPortsByCustomerIdAndSiteIdAndBillPeriod(String customerId, int siteId, int billPeriod);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM BB_PORTS_OVERFLOW_DETAILS WHERE CUSTOMER_ID=?1 AND SITE_ID=?2 AND BILL_PERIOD=?3 ", nativeQuery = true)
    void deleteByCustomerIdAndSiteIdAndBillPeriod(String customerId, int siteId, int billPeriod);

}
