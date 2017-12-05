package com.kt.repo.mysql.batch;

import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Biz;
import com.kt.entity.mysql.crm.ChargeScheme;
import com.kt.service.SearchFilter;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/14.
 */
public interface ChargeSchemeRepositoryCustom {

    Page<ChargeScheme> listAllTemplates(int curPage, SearchFilter search);

    List<ChargeScheme> listCandidateTemplates4Biz(Biz biz);
}
