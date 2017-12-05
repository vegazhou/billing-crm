package com.kt.repo.mysql.batch;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.types.AgentType;
import com.kt.common.dbhelp.DbHelper;
import com.kt.entity.mysql.crm.Order;
import com.kt.service.SearchFilter;

import com.kt.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @Autowired
    private DbHelper dbHelper;

    private static final Logger LOGGER = Logger.getLogger(OrderRepositoryImpl.class);

//    private static final String QUERY_ORDER_CONTEXT =
//            "SELECT b_order.* FROM b_customer , b_contract, b_order " +
//                    "WHERE b_customer.PID = b_contract.CUSTOMER_ID AND b_contract.PID = b_order.CONTRACT_ID " +
//                    "AND (b_contract.STATE = 'IN_EFFECT' OR b_contract.pid = ?) AND b_order.pid NOT IN " +
//                    "(SELECT pid FROM b_order WHERE contract_id = (" +
//                    "SELECT pid FROM b_contract WHERE pid = (SELECT alter_target_id FROM b_contract WHERE pid = ?)))";

    private static final String  QUERY_ORDER_CONTEXT = "SELECT b_order.* FROM b_customer , b_contract, b_order " +
            "WHERE b_customer.PID = b_contract.CUSTOMER_ID AND b_contract.PID = b_order.CONTRACT_ID " +
            "AND (b_contract.pid = ? OR (b_contract.STATE = 'IN_EFFECT' AND b_customer.PID = ?)) " +
            "AND b_order.pid NOT IN " +
            "(SELECT pid FROM b_order WHERE contract_id = (" +
            "SELECT pid FROM b_contract WHERE pid = (SELECT alter_target_id FROM b_contract WHERE pid = ?)))";

    @Override
    public List<Order> findOrderContext(String contractId) {

        try {
            String customerId = findCustomerOfContract(contractId);
            return dbHelper.getBeanList(QUERY_ORDER_CONTEXT, Order.class, contractId, customerId, contractId);
        } catch (SQLException e) {
            LOGGER.error("OrderRepositoryImpl.findOrderContext error", e);
            return null;
        }
    }

    private String findCustomerOfContract(String contractId) {
        try {
            return dbHelper.getMap("SELECT distinct(b_customer.pid) as customerid FROM b_customer , b_contract, b_order " +
                    "WHERE b_customer.PID = b_contract.CUSTOMER_ID AND b_contract.PID = b_order.CONTRACT_ID AND CONTRACT_ID = ?", contractId).get("customerid");

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Order> listAllByPage(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT a.*,c.SITENAME FROM B_ORDER a LEFT JOIN B_SITE_ORDER_RELATIONS b  ON (a.pid=b.ORDER_ID or a.ORIGINAL_ORDER_ID = b.ORDER_ID) LEFT JOIN " +
                "(select pid, sitename from b_webex_sites union all select id, sitename from b_webex_site_draft) c  ON c.pid=b.site_id  WHERE a.pid !=' ' ");
        if (StringUtils.isNotBlank(search.getCustomerId())) {
            sql.append(" and a.CONTRACT_ID = ? ");
            params.add(search.getCustomerId());
        }
   

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            return dbHelper.getBeanList(sql.toString(), Order.class, params.toArray());
//            Page page = dbHelper.getPage(sql.toString(), Order.class,
//                    curPage, search.getPageSize(), params.toArray());
//            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }
    
    
    @Override
    public List<Order> listAllByPageForReport(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT o.*, c.DISPLAY_NAME AS CONTRACTNAME FROM B_ORDER o INNER JOIN B_CONTRACT c ON o.CONTRACT_ID = c.PID  WHERE o.pid !=' ' ");
        if (StringUtils.isNotBlank(search.getCustomerId())) {
            sql.append(" and o.CUSTOMER_ID = ? ");
            params.add(search.getCustomerId());
        }
   

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            return dbHelper.getBeanList(sql.toString(), Order.class, params.toArray());
//            Page page = dbHelper.getPage(sql.toString(), Order.class,
//                    curPage, search.getPageSize(), params.toArray());
//            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }


    private static final String MAIN_PRODUCT_QUERY =
            "select o.* from b_order o, b_charge_scheme c where o.charge_id = c.ID " +
                    "and (o.state = 'IN_EFFECT' or o.state = 'END_OF_LIFE') " +
                    "and c.type in ('MONTHLY_PAY_BY_HOSTS', 'EC_PAY_PER_USE', 'MONTHLY_PAY_BY_PORTS', 'EC_PREPAID', 'MONTHLY_PAY_BY_ACTIVEHOSTS') " +
                    "and o.CUSTOMER_ID = ? " +
                    "and (" +
                    "(o.EFFECTIVEENDDATE <= ? and o.EFFECTIVEENDDATE >= ?) " +
                    "or " +
                    "(o.EFFECTIVESTARTDATE <= ? and o.EFFECTIVESTARTDATE >= ?) " +
                    "or " +
                    "(o.EFFECTIVESTARTDATE < ? and o.EFFECTIVEENDDATE > ?) " +
                    ")";
    @Override
    public List<Order> findMainProductOrders(String customerId, AccountPeriod accountPeriod) {
        try {
            String end = DateUtil.formatDate(accountPeriod.endOfThisPeriod());
            String begin = DateUtil.formatDate(accountPeriod.beginOfThisPeriod());
            return dbHelper.getBeanList(MAIN_PRODUCT_QUERY, Order.class,
                    customerId, end, begin, end, begin, begin, end);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static final String PSTN_PRODUCT_QUERY =
            "select o.* from b_order o, b_charge_scheme c where o.charge_id = c.ID " +
                    "and (o.state = 'IN_EFFECT' or o.state = 'END_OF_LIFE') " +
                    "and c.type in ('PSTN_STANDARD_CHARGE') " +
                    "and o.CUSTOMER_ID = ? " +
                    "and (" +
                    "(o.EFFECTIVEENDDATE <= ? and o.EFFECTIVEENDDATE >= ?) " +
                    "or " +
                    "(o.EFFECTIVESTARTDATE <= ? and o.EFFECTIVESTARTDATE >= ?) " +
                    "or " +
                    "(o.EFFECTIVESTARTDATE < ? and o.EFFECTIVEENDDATE > ?) " +
                    ")";
    @Override
    public List<Order> findPstnProductOrders(String customerId, AccountPeriod accountPeriod) {
        try {
            String end = DateUtil.formatDate(accountPeriod.endOfThisPeriod());
            String begin = DateUtil.formatDate(accountPeriod.beginOfThisPeriod());
            return dbHelper.getBeanList(PSTN_PRODUCT_QUERY, Order.class,
                    customerId, end, begin, end, begin, begin, end);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static final String PORTS_PRODUCT_QUERY =
            "select o.* from b_order o, b_charge_scheme c where o.charge_id = c.ID " +
                    "and (o.state = 'IN_EFFECT' or o.state = 'END_OF_LIFE') " +
                    "and c.type in ('MONTHLY_PAY_BY_PORTS') " +
                    "and o.CUSTOMER_ID = ? " +
                    "and (" +
                    "(o.EFFECTIVEENDDATE <= ? and o.EFFECTIVEENDDATE >= ?) " +
                    "or " +
                    "(o.EFFECTIVESTARTDATE <= ? and o.EFFECTIVESTARTDATE >= ?) " +
                    "or " +
                    "(o.EFFECTIVESTARTDATE < ? and o.EFFECTIVEENDDATE > ?) " +
                    ")";
    @Override
    public List<Order> findPortsProductOrders(String customerId, AccountPeriod accountPeriod) {
        try {
            String end = DateUtil.formatDate(accountPeriod.endOfThisPeriod());
            String begin = DateUtil.formatDate(accountPeriod.beginOfThisPeriod());
            return dbHelper.getBeanList(PORTS_PRODUCT_QUERY, Order.class,
                    customerId, end, begin, end, begin, begin, end);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Order> findInEffectTrialProductOrdersByEndDate(String endDate){
        String sql = "select o.* from b_order o, b_product p where o.product_id=p.pid " +
                "and p.is_trial=1 and o.state='IN_EFFECT' " +
                "and o.effectiveenddate= ? order by o.customer_id, o.contract_id";
        try {
            return dbHelper.getBeanList(sql, Order.class, endDate);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }

    @Override
    public List<Order> findInEffectNormalProductOrdersByEndDateAndAgentType(String endDate, String agentType){
        String sql = "select c.agent_id || ',' || o.customer_id as customer_id, o.pid, o.contract_id, o.product_id," +
                "o.EFFECTIVESTARTDATE,o.EFFECTIVEENDDATE,o.biz_id,o.CHARGE_ID " +
                "from b_order o, b_product p, b_contract c, b_customer cst " +
                "where o.contract_id=c.pid and o.product_id=p.pid " +
                "and p.is_trial=0 and o.state='IN_EFFECT' " +
                "and o.effectiveenddate= ? ";

        if(AgentType.DIRECT.toString().equals(agentType)){
            sql = sql + "and o.customer_id=cst.pid ";
            sql = sql + "and c.agent_id is null ";
            sql = sql + "and cst.AGENT_TYPE in ('DIRECT', 'RESELLER') ";
            sql = sql + "order by o.customer_id,o.contract_id";
        }else{
            sql = sql + "and c.agent_id=cst.pid ";
            sql = sql + "and c.agent_id is not null ";
            sql = sql + "and cst.AGENT_TYPE in ('" + agentType + "') ";
            sql = sql + "order by c.agent_id,o.customer_id,o.contract_id";
        }
        try {
            return dbHelper.getBeanList(sql, Order.class, endDate);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }

    @Override
    public List<Order> findOrderByStateAndDateAndProductIdOrderByHost(String orderState, String date, List<String> productIds){

        String tmpProductIds="";

        for(String productId: productIds){
            if(tmpProductIds.length()==0){
                tmpProductIds = "'" + productId + "'";
            }else{
                tmpProductIds = tmpProductIds + ",'" + productId + "'";
            }
        }

        if(productIds==null || productIds.size()==0){
            return null;
        }

//        String sql = "select o.*, c.attribute_value as siteName from b_Order o,B_CHARGE_SCHEME_ATTRIBUTES c where o.STATE = '" + orderState + "' and " +
//                "((o.EFFECTIVESTARTDATE like '" + date + "%' and o.PROVISION_STATE IS NULL)" + " or " +
//                "(o.EFFECTIVEENDDATE like '" + date + "%' and o.TERMINATE_STATE IS NULL))" + " and " +
//                "o.PRODUCT_ID in (" + tmpProductIds + ") and o.charge_id=c.scheme_id" + " and " +
//                "c.attribute_name='DISPLAY_NAME'" + " order by c.attribute_value asc, o.PRODUCT_ID asc, o.PLACED_DATE asc";

        String sql = "select a.pid,a.contract_id,a.state,a.product_id,a.effectivestartdate,a.effectiveenddate," +
                "a.biz_id,a.charge_id,a.pay_interval,a.first_installment,a.total_amount,a.provision_state,a.terminate_state,a.placed_date," +
                "a.customer_id, b.attribute_value || ',' || siteName as siteName from " +
                "(select o.*, c.attribute_value as siteName from b_Order o,B_CHARGE_SCHEME_ATTRIBUTES c where o.STATE = '" + orderState + "' and " +
                "((o.EFFECTIVESTARTDATE like '" + date + "%' and o.PROVISION_STATE IS NULL)" + " or " +
                "(o.EFFECTIVEENDDATE like '" + date + "%' and o.TERMINATE_STATE IS NULL))" + " and " +
                "o.PRODUCT_ID in (" + tmpProductIds + ") and o.charge_id=c.scheme_id" + " and " +
                "c.attribute_name='DISPLAY_NAME'" + ") a, B_CHARGE_SCHEME_ATTRIBUTES b" +
                " where a.charge_id=b.scheme_id and b.attribute_name='COMMON_SITE'" +
                " order by siteName, a.PRODUCT_ID asc, a.PLACED_DATE asc";

        try {
            return dbHelper.getBeanList(sql, Order.class);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }
}
