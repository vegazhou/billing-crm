package wang.huaichao.data.repo;

import com.kt.entity.mysql.billing.PortsOverflowDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 9/21/2016.
 */
@Repository("PortsOverflowDetailRepository2")
public interface PortsOverflowDetailRepository
    extends CrudRepository<PortsOverflowDetail, Long> {

    List<PortsOverflowDetail> findByCustomerIdAndBillPeriodAndSiteNameIn(
        String customerId, int billingPeriod, List<String> siteNames);

    List<PortsOverflowDetail> findByCustomerIdAndBillPeriod(
        String customerId, int billingPeriod);

}
