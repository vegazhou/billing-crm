package wang.huaichao.data.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import wang.huaichao.data.entity.crm.BillingProgress;
import wang.huaichao.data.entity.crm.BillingProgressPK;

import java.util.List;

/**
 * Created by Administrator on 9/20/2016.
 */
public interface BillingProgressRepository extends CrudRepository<BillingProgress, BillingProgressPK> {
    List<BillingProgress> findByBillingPeriodAndFailedTasks(int billingPeriod, int failedTasks);

    Page<BillingProgress> findByTypeOrderByBillingPeriodDesc(
        BillingProgressPK.BillingProgressType type,
        Pageable pageable
    );

    List<BillingProgress> findByBillingPeriodAndType(int billingPeriod, BillingProgressPK.BillingProgressType type);

    List<BillingProgress> findByBillingPeriodAndTypeAndFailedTasks(
        int billingPeriod,
        BillingProgressPK.BillingProgressType type,
        int failedTasks
    );


}
