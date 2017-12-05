package wang.huaichao.data.repo;

import org.springframework.data.repository.CrudRepository;
import wang.huaichao.data.entity.crm.PstnPackageUsage;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Administrator on 8/9/2016.
 */
public interface PstnPackageUsageRepository extends CrudRepository<PstnPackageUsage, String> {
    List<PstnPackageUsage> findByPackageId(String packageId);

    void deleteByPackageId(String packageId);

    @Transactional
    void deleteByPackageIdIn(List<String> packageIds);
}
