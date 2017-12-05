package wang.huaichao.data.repo;

import org.springframework.data.repository.CrudRepository;
import wang.huaichao.data.entity.crm.PstnPackage;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 8/9/2016.
 */
@Transactional
public interface PstnPackageRepository extends CrudRepository<PstnPackage, String> {
    public PstnPackage findById(String id);

    public List<PstnPackage> findByOrderId(String orderId);

    public List<PstnPackage> findByOrderIdIn(List<String> orderIds);

    public void deleteByOrderId(String orderId);
}
