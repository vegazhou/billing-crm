package wang.huaichao.data.repo;

import com.kt.biz.billing.AccountPeriod;
import com.kt.entity.mysql.billing.BillFormalDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Administrator on 8/9/2016.
 */
@Repository("BillFormalDetailRepository2")
public interface BillFormalDetailRepository
    extends CrudRepository<BillFormalDetail, Long> {

    List<BillFormalDetail> findByOrderIdIn(List<String> orderIds);

    List<BillFormalDetail> findByAccountPeriodAndFeeTypeAndOrderIdIn(
            String accountPeriod, int feeType, List<String> orders);

    List<BillFormalDetail> findByAccountPeriodAndCustomerId(
        String accountPeriod, String customerId);
}
