package com.kt.repo.mysql.billing;

import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.WebExRequest;
import com.kt.service.SearchFilter;

/**
 * Created by Garfield Chen on 2016/6/14..
 */
public interface RequestRepositoryCustom {

  
    Page<WebExRequest> listAllByPage(int curPage, SearchFilter search);
}
