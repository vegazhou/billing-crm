package wang.huaichao.data.repo;

import com.kt.entity.mysql.billing.BssStorageUsageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
@Repository("storageUsageRepo")
public interface BssStorageUsageLogRepository extends JpaRepository<BssStorageUsageLog, String> {
    List<BssStorageUsageLog> findByCustomerIdAndAccountPeriod(
        String customerId, int accountPeriod);
}
