package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.MigrateWebExSite;
import com.kt.entity.mysql.crm.WebExSite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public interface MigrateWebExSiteRepository extends JpaRepository<MigrateWebExSite, String> {
    MigrateWebExSite findOneBySiteName(String siteName);

    MigrateWebExSite findOneBySiteNameAndState(String siteName, String state);

    MigrateWebExSite findOneBySiteNameAndCustomerId(String siteName, String customerId);

    MigrateWebExSite findOneBySiteNameAndCustomerIdAndState(String siteName, String customerId, String state);

    List<MigrateWebExSite> findByCustomerIdAndState(String customerId, String state);

    List<MigrateWebExSite> findByCustomerId(String customerId);
}
