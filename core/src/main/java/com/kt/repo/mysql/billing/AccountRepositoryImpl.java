package com.kt.repo.mysql.billing;

import com.kt.biz.bean.CustomerAccountsBean;
import com.kt.common.dbhelp.DbHelper;

import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.AccountOperation;
import com.kt.entity.mysql.billing.CustomerAccount;
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
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(AccountRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;


    public Page<CustomerAccountsBean> paginate(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT * FROM V_ACCOUNTS WHERE 1=1 ");

        if (StringUtils.isNotBlank(search.getDisplayName()) && StringUtils.isBlank(search.getCustomerId())) {
            sql.append(" AND lower(CUSTOMER_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }

        if (StringUtils.isNotBlank(search.getCustomerId())) {
            sql.append(" and customer_id ='").append(search.getCustomerId()).append("'");
        }

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }

        try {
            Page page = dbHelper.getPage(sql.toString(), CustomerAccountsBean.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }


    @Override
    public Page<CustomerAccount> listAllByPage(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT a.*, b.display_name as company, b.code as customer_code FROM BB_ACCOUNT a, B_CUSTOMER b WHERE a.customer_id= b.pid ");


        if (StringUtils.isNotBlank(search.getDisplayName()) && StringUtils.isBlank(search.getCustomerId())) {
            sql.append(" AND lower(b.DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }

        if (StringUtils.isNotBlank(search.getCustomerId())) {
            sql.append(" and a.customer_id ='").append(search.getCustomerId()).append("'");
        }
        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), CustomerAccount.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;

        }
    }

    @Override
    public Page<AccountOperation> listAllByPageForDetail(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT * FROM BB_ACCOUNT_OPERATION where ACCOUNT_ID!=0 AND CURRENT_AMOUNT <> 0 ");


        if (StringUtils.isNotBlank(search.getCustomerId())) {
            sql.append("and  ACCOUNT_ID ='").append(search.getCustomerId()).append("'");
        }
        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType())
                    .append(" ,SEQUENCE_ID ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), AccountOperation.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;

        }
    }

}
