package wang.huaichao.data.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import wang.huaichao.data.entity.crm.MeetingRecord;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by Administrator on 8/9/2016.
 */
public interface MeetingRecordRepository extends CrudRepository<MeetingRecord, Long>, MeetingRecordRepositoryCustom {
//    @Transactional
//    @Modifying
//    @Query("delete from MeetingRecord mr where mr.billingPeriod=?1 and mr.siteName in ?2")
//    public void deleteByBillingPeriodAndSiteNameIn(String billingPeriod, List<String> siteNames);

    public List<MeetingRecord> findByCustomerIdAndBillingPeriod(String customerId, int billingPeriod);

    public List<MeetingRecord> findByBillingPeriodAndOrderIdInOrderByCustomerIdAscStartTimeAsc(
        int billingPeriod, List<String> orderIds);

    List<MeetingRecord> findByBillingPeriodAndOrderId(int billingPeriod, String orderId);
}
