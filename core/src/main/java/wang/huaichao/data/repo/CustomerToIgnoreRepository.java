package wang.huaichao.data.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wang.huaichao.data.entity.crm.CustomerToIgnore;

import java.util.List;

/**
 * Created by Administrator on 9/21/2016.
 */
@Repository
public interface CustomerToIgnoreRepository extends CrudRepository<CustomerToIgnore, String> {
    List<CustomerToIgnore> findByType(CustomerToIgnore.CustomerToIgnoreType type);
}
