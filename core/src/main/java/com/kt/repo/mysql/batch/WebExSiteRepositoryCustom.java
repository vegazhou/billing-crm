package com.kt.repo.mysql.batch;

import java.util.List;

import com.kt.biz.bean.WebExRequestRecord;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.WebExSiteDraft;
import com.kt.entity.mysql.crm.WebExSiteDraftReport;
import com.kt.service.SearchFilter;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
public interface WebExSiteRepositoryCustom {

    List<String> findAvailableSiteNames4Contract(String contractId);
    List<WebExSiteDraft> listAllByPage(int curPage, SearchFilter search);
    Page<WebExSiteDraftReport> listAllByPageForReport(int curPage, SearchFilter search);
    Page<WebExSiteDraftReport> listAllSiteByPageForReport(int curPage, SearchFilter search);
    Page<WebExSiteDraftReport> paginateAllSites(int curPage, SearchFilter search);

    Page<WebExRequestRecord> paginateWebExRequests(int curPage, SearchFilter search);
}
