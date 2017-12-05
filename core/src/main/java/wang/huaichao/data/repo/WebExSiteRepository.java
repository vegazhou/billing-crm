package wang.huaichao.data.repo;

import com.kt.entity.mysql.crm.WebExSite;
import com.kt.repo.mysql.batch.WebExSiteRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Repository("WebExSiteRepository2")
public interface WebExSiteRepository extends JpaRepository<WebExSite, String>, WebExSiteRepositoryCustom {
    List<WebExSite> findByCustomerIdAndStateIn(String customerId, String... states);
}
