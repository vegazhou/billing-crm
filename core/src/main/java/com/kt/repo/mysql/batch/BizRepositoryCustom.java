package com.kt.repo.mysql.batch;

import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Biz;
import com.kt.service.SearchFilter;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/14.
 */
public interface BizRepositoryCustom {

    Page<Biz> listAllTemplates(int curPage, SearchFilter search);

    List<Biz> listAllUnusedInEffectTemplates();
}
