package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.WebExSite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public interface WebExSiteRepository extends JpaRepository<WebExSite, String>, WebExSiteRepositoryCustom {
    WebExSite findOneBySiteName(String siteName);

    WebExSite findOneBySiteNameAndState(String siteName, String state);

    WebExSite findOneBySiteNameAndCustomerId(String siteName, String customerId);

    WebExSite findOneBySiteNameAndCustomerIdAndState(String siteName, String customerId, String state);

    List<WebExSite> findByCustomerIdAndState(String customerId, String state);

    List<WebExSite> findByCustomerId(String customerId);
}
