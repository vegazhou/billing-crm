package com.kt.repo.mysql.batch;

import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.DbHelper;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Contract;
import com.kt.service.SearchFilter;
import com.kt.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Garfield Chen on 2016/3/24.
 */
@Repository
public class ContractRepositoryImpl implements ContractRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(ContractRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;

    private static final String SQL_1 = "SELECT a.*, b.display_name as company, c.name as salesmanname, b2.DISPLAY_NAME as agentname " +
            "FROM B_CONTRACT a inner join B_CUSTOMER b on a.customer_id= b.pid " +
            "inner join B_SALES c on c.id=a.SALESMAN " +
            "left join B_CUSTOMER b2 on a.AGENT_ID = b2.pid " +
            "WHERE a.pid !=' ' ";
    @Override
    public Page<Contract> listAllByPage(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append(SQL_1);
      
        
        if (StringUtils.isNotBlank(search.getCustomerId())) {
            sql.append(" AND lower(b.DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getCustomerId()) + "%");
        }
        
        if (StringUtils.isNotBlank(search.getCustomerName())) {
            sql.append(" AND lower(b2.DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getCustomerName()) + "%");
        }
        
        if (StringUtils.isNotBlank(search.getSalesman())) {
            sql.append(" AND lower(c.name) like ?");
            params.add("%" + StringUtils.lowerCase(search.getSalesman()) + "%");
        }
        
        
        
    	if (StringUtils.isNotBlank(search.getState())&&(search.getState().equals("IN_EFFECT")||search.getState().equals("WAITING_APPROVAL"))) {
    		sql.append(" and state ='").append(search.getState()).append("'");
		}
    	if (StringUtils.isNotBlank(search.getState())&&search.getState().equals("other")) {
    		sql.append(" and state !='IN_EFFECT'");
		}
        
        if (StringUtils.isNotBlank(search.getDisplayName())) {
            sql.append(" AND lower(a.DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }
   

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), Contract.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }
    @Override
    public Page<Contract> listAllByPageForConfirm(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("select t1.*, t2.FIRST_INSTALLMENT, cu.display_name as agent_name from b_contract t1 inner join "+
		"(select c.PID, sum(o.FIRST_INSTALLMENT) as FIRST_INSTALLMENT from b_contract c inner join b_order o on c.PID = o.CONTRACT_ID "+
		"WHERE c.STATE = 'WAITING_FIN_APPROVAL' " +
		"GROUP BY c.PID) t2 on t1.PID = t2.PID " +
                "left join b_customer cu on t1.agent_id = cu.pid");

//        sql.append("select * from b_contract t1 inner join "+
//                "(select c.PID, sum(o.FIRST_INSTALLMENT) as FIRST_INSTALLMENT from b_contract c inner join b_order o on c.PID = o.CONTRACT_ID "+
//                "WHERE c.STATE = 'WAITING_FIN_APPROVAL' and o.ORIGINAL_ORDER_ID is null "+
//                "GROUP BY c.PID) t2 on t1.PID = t2.PID");

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), Contract.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }


    private static final String SQL_2 = "select * from b_contract c where customer_id = ? " +
            "AND (AGENT_ID IS NULL or AGENT_ID NOT IN (select PID from b_customer where agent_type = 'RESELLER2')) AND STATE = 'IN_EFFECT'";
    @Override
    public List<Contract> findNonReseller2Contracts(String directCustomerId) {
        try {
            return  dbHelper.getBeanList(SQL_2, Contract.class, directCustomerId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static final String SQL_3 = "SELECT * FROM B_CONTRACT C WHERE STATE = 'IN_EFFECT' AND AGENT_ID = ? ";
    @Override
    public List<Contract> findContractsOfReseller2(String resellerId) {
        try {
            return  dbHelper.getBeanList(SQL_3, Contract.class, resellerId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    private static final String SQL_4 = "select * from b_contract c " +
            "inner join " +
            "(" +
            "select distinct o.contract_id from b_order o " +
            "where ((o.EFFECTIVESTARTDATE < ? and o.EFFECTIVEENDDATE >= ?) " +
            "or (o.EFFECTIVESTARTDATE >= ? and o.EFFECTIVESTARTDATE < ?)) " +
            "and o.customer_id = ? " +
            ") t1 on t1.contract_id = c.pid " +
            "AND (c.AGENT_ID IS NULL or c.AGENT_ID NOT IN (select PID from b_customer where agent_type = 'RESELLER2')) AND c.STATE = 'IN_EFFECT'";
    @Override
    public List<Contract> findAccountableNonReseller2Contracts(String directCustomerId, AccountPeriod accountPeriod) {
        try {
            return  dbHelper.getBeanList(SQL_4, Contract.class,
                    DateUtil.formatDate(accountPeriod.beginOfThisPeriod()), DateUtil.formatDate(accountPeriod.beginOfThisPeriod()),
                    DateUtil.formatDate(accountPeriod.beginOfThisPeriod()), DateUtil.formatDate(accountPeriod.nextPeriod().beginOfThisPeriod()),
                            directCustomerId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    private static final String SQL_5 = "select * from b_contract c " +
            "inner join " +
            "(" +
            "select distinct o.contract_id from b_order o " +
            "where ((o.EFFECTIVESTARTDATE < ? and o.EFFECTIVEENDDATE >= ?) " +
            "or (o.EFFECTIVESTARTDATE >= ? and o.EFFECTIVESTARTDATE < ?)) " +
            ") t1 on t1.contract_id = c.pid " +
            "AND c.AGENT_ID = ? AND c.STATE = 'IN_EFFECT'";
    @Override
    public List<Contract> findAccountableReseller2Contracts(String resellerCustomerId, AccountPeriod accountPeriod) {
        try {
            return  dbHelper.getBeanList(SQL_5, Contract.class,
                    DateUtil.formatDate(accountPeriod.beginOfThisPeriod()), DateUtil.formatDate(accountPeriod.beginOfThisPeriod()),
                    DateUtil.formatDate(accountPeriod.beginOfThisPeriod()), DateUtil.formatDate(accountPeriod.nextPeriod().beginOfThisPeriod()),
                            resellerCustomerId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
