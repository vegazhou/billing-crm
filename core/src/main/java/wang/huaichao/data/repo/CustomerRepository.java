package wang.huaichao.data.repo;

import com.kt.entity.mysql.billing.BillConfirmation;
import com.kt.entity.mysql.billing.BillConfirmationPrimaryKey;
import com.kt.entity.mysql.crm.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 9/21/2016.
 */
@Repository("CustomerRepo2")
public interface CustomerRepository extends CrudRepository<Customer, String> {
    List<Customer> findByDisplayNameIn(List<String> names);

    List<Customer> findByPidIn(List<String> ids);
}
