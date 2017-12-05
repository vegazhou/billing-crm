package com.kt.repo.mysql.billing;

import com.kt.common.dbhelp.DbHelper;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.WebExRequest;
import com.kt.service.SearchFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Garfield Chen on 2016/6/14.
 */
@Repository
public class RequestRepositoryImpl implements RequestRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(RequestRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;


    @Override
    public Page<WebExRequest> listAllByPage(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT a.*, c.display_name  " +
                "FROM B_WBX_PROVISION_TASK a , B_CONTRACT b, B_CUSTOMER c " +
                "where a.CONTRACT_ID=b.PID and b.CUSTOMER_ID=c.pid ");
      
        
        if (StringUtils.isNotBlank(search.getDisplayName())) {
            sql.append(" AND lower(a.SITE_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }
        
        if (StringUtils.isNotBlank(search.getCustomerName())) {
            sql.append(" AND lower(c.DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getCustomerName()) + "%");
        }
        
    
   

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), WebExRequest.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        
        }
    }
}
