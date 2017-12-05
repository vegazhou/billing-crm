package wang.huaichao.data.repo;

import org.springframework.data.repository.CrudRepository;
import wang.huaichao.data.entity.crm.MeetingRecord;
import wang.huaichao.data.entity.crm.PstnCharge;

import java.util.List;


/**
 * Created by Administrator on 8/9/2016.
 */
public interface PstnChargeRepository extends CrudRepository<PstnCharge, Long> {
    public PstnCharge findByBillingPeriodAndOrderId(int billingPeriod, String orderId);

    public List<PstnCharge> findByBillingPeriodAndCustomerId(int billingPeriod, String customerId);
}
