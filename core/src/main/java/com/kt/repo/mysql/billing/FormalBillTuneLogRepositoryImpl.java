package com.kt.repo.mysql.billing;

import com.kt.common.dbhelp.DbHelper;

import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.AccountOperation;
import com.kt.entity.mysql.billing.CustomerAccount;
import com.kt.entity.mysql.billing.FormalBillTuneLog;
import com.kt.service.SearchFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

import java.util.ArrayList;

/**

 * Created by garfield chen on 2016/5/5.

 */
@Repository
public class FormalBillTuneLogRepositoryImpl implements FormalBillTuneLogRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(FormalBillTuneLogRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;


    @Override
    public Page<FormalBillTuneLog> listAllByPage(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT * FROM BB_FBILL_TUNELOG where BILL_ID !=0   ");
      
        
        if (StringUtils.isNotBlank(search.getCustomerId())) {
    		sql.append("and BILL_ID ='").append(search.getCustomerId()).append("'");
		}
        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), FormalBillTuneLog.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        
        }
    }
    
  
   
}
