package com.kt.repo.mysql.batch;

import com.kt.common.dbhelp.DbHelper;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Salesman;
import com.kt.service.SearchFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Garfield Chen on 2016/6/2.
 */
@Repository
public class SalesmanRepositoryImpl implements SalesmanRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(BizRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;

    @Override
    public Page<Salesman> listAll(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT * FROM B_SALES WHERE ID !=' '");
        if (StringUtils.isNotBlank(search.getDisplayName())) {
            sql.append(" AND lower(NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }
        
        if (StringUtils.isNotBlank(search.getState())) {
            sql.append(" AND ENABLED = ?");
            params.add(StringUtils.upperCase(search.getState()));
        }

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), Salesman.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }

   

 
}
