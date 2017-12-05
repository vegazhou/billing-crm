package com.kt.repo.mysql.billing;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.AccountOperation;
import com.kt.entity.mysql.billing.CustomerAccount;
import com.kt.entity.mysql.billing.FormalBillTuneLog;
import com.kt.service.SearchFilter;



/**
 * Created by Garfield Chen on 2016/5/5.
 */
public interface FormalBillTuneLogRepositoryCustom {
   
    Page<FormalBillTuneLog> listAllByPage(int curPage, SearchFilter search);
   
    
   
}
