package com.kt.repo.mysql.batch;

import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Salesman;
import com.kt.service.SearchFilter;

/**
 * Created by Garfield Chen on 2016/6/2.
 */
public interface SalesmanRepositoryCustom {

    Page<Salesman> listAll(int curPage, SearchFilter search);

    
}
