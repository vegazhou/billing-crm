package com.kt.repo.mysql.batch;

import com.kt.common.dbhelp.DbHelper;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Customer;
import com.kt.service.SearchFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Garfield Chen on 2016/3/24.
 */
@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(CustomerRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;

    @Override
    public Page<Customer> listAllNonAgentCustomers(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT * FROM B_CUSTOMER  WHERE (AGENT_TYPE = 'DIRECT' OR AGENT_TYPE = 'RESELLER') ");
        if (StringUtils.isNotBlank(search.getDisplayName())) {
            sql.append(" AND lower(DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }
   

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), Customer.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }


    @Override
    public Page<Customer> listAllAgentCustomers(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT * FROM B_CUSTOMER  WHERE AGENT_TYPE = 'AGENT' ");
        if (StringUtils.isNotBlank(search.getDisplayName())) {
            sql.append(" AND lower(DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }


        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), Customer.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }


    @Override
    public Page<Customer> paginateReseller2(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT * FROM B_CUSTOMER  WHERE AGENT_TYPE = 'RESELLER2' ");
        if (StringUtils.isNotBlank(search.getDisplayName())) {
            sql.append(" AND lower(DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }


        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), Customer.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }

    @Override
    public List<Customer> listAllAgents() {
        try {
            return dbHelper.getBeanList("SELECT * FROM B_CUSTOMER WHERE AGENT_TYPE = 'AGENT' ", Customer.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }


    @Override
    public List<Customer> listAllAgentsAndReseller2() {
        try {
            return dbHelper.getBeanList("SELECT * FROM B_CUSTOMER WHERE AGENT_TYPE = 'AGENT' OR AGENT_TYPE = 'RESELLER2' ORDER BY DISPLAY_NAME ASC", Customer.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    @Override
    public String nextCode() {
        try {
            Map<String, String> kv = dbHelper.getMap("select SEQ_CUSTOMER.nextval as code from dual");
            Integer nextCodeSequence = Integer.valueOf(kv.get("code"));
            return String.format("KT%05d", nextCodeSequence);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }
}
