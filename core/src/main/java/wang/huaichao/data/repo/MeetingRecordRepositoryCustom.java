package wang.huaichao.data.repo;

import wang.huaichao.data.entity.crm.MeetingRecord;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Administrator on 8/30/2016.
 */
public interface MeetingRecordRepositoryCustom {
    //    @Transactional
    void deleteByBillingPeriodAndSiteNameIn(int billingPeriod, List<String> siteNames);

    void deleteByBillingPeriodAndCustomerIdAndSiteNameIn(
        int billingPeriod, String customerId, List<String> siteNames);

    List<MeetingRecord> findByCustomerIdAndBillingPeriodJoinPstnCode(String customerId, int billingPeriod);

    List<MeetingRecord> findByCustomerIdAndBillingPeriodJoinPstnCode(int billingPeriod, List<String> orderIds);

    List<MeetingRecord> findByCustomerIdsAndBillingPeriodJoinPstnCode(List<String> customerIds, int billingPeriod);

    List<MeetingRecord> findByBillingPeriodAndOrderIds(int billingPeriod, List<String> orderIds);
}
