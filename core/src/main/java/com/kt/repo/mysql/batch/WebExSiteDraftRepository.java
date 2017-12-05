package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.crm.WebExSiteDraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
public interface WebExSiteDraftRepository extends JpaRepository<WebExSiteDraft, String> {

    List<WebExSiteDraft> findByContractId(String contractId);

    WebExSiteDraft findBySiteNameAndContractId(String siteName, String contractId);

    void deleteByContractId(String contractId);
}
