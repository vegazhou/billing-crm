package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.WebExSiteOrderRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/29.
 */
public interface WebExSiteDraftOrderRelationRepository extends JpaRepository<WebExSiteOrderRelation, String> {

    void deleteByOrderId(String orderId);

    WebExSiteOrderRelation findFirstBySiteId(String siteId);

    List<WebExSiteOrderRelation> findBySiteId(String siteId);

    void deleteBySiteId(String siteId);

    WebExSiteOrderRelation findFirstByOrderId(String orderId);

}
