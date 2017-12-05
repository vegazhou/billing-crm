package wang.huaichao.data.repo;

import com.kt.entity.mysql.billing.BillConfirmation;
import com.kt.entity.mysql.billing.BillConfirmationPrimaryKey;
import com.kt.entity.mysql.billing.BillFormalDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 9/21/2016.
 */
@Repository("BillConfirmationRepository2")
public interface BillConfirmationRepository extends CrudRepository<BillConfirmation, BillConfirmationPrimaryKey> {
    List<BillConfirmation> findByAccountPeriod(String accountPeriod);
}
