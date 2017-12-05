package com.kt.repo.mysql.billing;

import com.kt.biz.bean.TempBillDetailBean;
import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.DbHelper;

import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.BillTemp;
import com.kt.service.SearchFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**

 * Created by garfield chen on 2016/5/5.

 */
@Repository
public class BillTempRepositoryImpl implements BillTempRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(BillTempRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;


    @Override
    public Page<BillTemp> listAllByPage(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT a.*, b.display_name as company FROM BB_BILL_TEMP a, B_CUSTOMER b WHERE a.customer_id= b.pid ");
      
        
     
        
        if (StringUtils.isNotBlank(search.getCustomerId())) {
    		sql.append(" and b.pid ='").append(search.getCustomerId()).append("'");
		}
        
    	if (StringUtils.isNotBlank(search.getAccountPeriod())) {
    		sql.append(" and a.ACCOUNT_PERIOD ='").append(search.getAccountPeriod()).append("'");
		}
        
        
   

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), BillTemp.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        
        }
    }  

//    private static final String SQL_LIST_TEMP_BILLS = "select t1.*, t2.DISPLAY_NAME as company, t2.pid as companyId from " +
//            "(select b.CUSTOMER_ID, b.ACCOUNT_PERIOD, sum(b.amount) as AMOUNT " +
//            "from bb_bill_temp b %s group by b.CUSTOMER_ID, b.ACCOUNT_PERIOD) t1 " +
//            "right outer join B_CUSTOMER t2 " +
//            "on t1.CUSTOMER_ID = t2.PID WHERE 1=1 %s";

//    private static final String SQL_LIST_TEMP_BILLS = "select t1.customer_id, decode(t1.account_period, null, ?, t1.account_period) as ACCOUNT_PERIOD, decode(t1.amount, null, 0, amount) as AMOUNT, t2.DISPLAY_NAME as company, t2.pid as companyId from \n" +
//            "(select b.CUSTOMER_ID, b.ACCOUNT_PERIOD, sum(b.amount) as AMOUNT from bb_bill_temp b  %s group by b.CUSTOMER_ID, b.ACCOUNT_PERIOD) t1 \n" +
//            "right outer join B_CUSTOMER t2 on t1.CUSTOMER_ID = t2.PID WHERE 1=1 %s";

    private static final String SQL_LIST_TEMP_BILLS = "select t2.PID as CUSTOMER_ID, t2.code as customer_code, decode(t1.account_period, null, ?, t1.account_period) as ACCOUNT_PERIOD, "+
            "decode(t1.amount, null, 0, amount) as AMOUNT, t2.DISPLAY_NAME as company, t2.pid as companyId, decode(t3.confirmed, null, 0, t3.confirmed) as ISCONFIRMED "+
            "from "+
            "(select b.CUSTOMER_ID, b.ACCOUNT_PERIOD, sum(b.amount) as AMOUNT from bb_bill_temp b  %s  group by b.CUSTOMER_ID, b.ACCOUNT_PERIOD) t1 "+
            "right outer join B_CUSTOMER t2 on t1.CUSTOMER_ID = t2.PID "+
            "left outer join "+
            "(select distinct customer_id, 1 as confirmed from bb_bill_confirmation %s) t3 on t3.customer_id = t2.pid WHERE 1=1 %s";


    
    
    @Override
    public Page<BillTemp> listAllByPageForConfirm(int curPage, SearchFilter search) {

        ArrayList<Object> params = new ArrayList<>();
        String whereClause1 = "";
        String whereClause2 = "";
        String whereClause3 = "";

        if (StringUtils.isNotBlank(search.getAccountPeriod())) {
            whereClause1 = "where b.ACCOUNT_PERIOD = ? ";
            whereClause2 = "where ACCOUNT_PERIOD = ? ";
            params.add(search.getAccountPeriod());
            params.add(search.getAccountPeriod());
            params.add(search.getAccountPeriod());
        } else {
            params.add(null);
        }

        if (StringUtils.isNotBlank(search.getCustomerId())) {
            whereClause3 = " AND lower(t2.DISPLAY_NAME) like ?";
            params.add("%" + StringUtils.lowerCase(search.getCustomerId()) + "%");
        }

        StringBuilder sql = new StringBuilder();
        sql.append(String.format(SQL_LIST_TEMP_BILLS, whereClause1, whereClause2, whereClause3));

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), BillTemp.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        
        }
    }



    private static final String SQL_FIND_TEMP_BILL_DETAIL = "select bt.AMOUNT, bt.FEE_TYPE as FEETYPE, bt.ACCOUNT_PERIOD as ACCOUNTPERIOD, " +
            "p.DISPLAY_NAME as PRODUCTNAME, " +
            "c.DISPLAY_NAME as CUSTOMERNAME, " +
            "ct.DISPLAY_NAME as CONTRACTNAME, ct.PID as CONTRACTID " +
            "from bb_bill_temp_detail bt inner join b_order o on bt.order_id = o.PID inner join b_contract ct on ct.PID = o.CONTRACT_ID " +
            "inner join b_product p on p.PID = o.PRODUCT_ID inner join b_customer c on c.PID = bt.CUSTOMER_ID " +
            "where c.PID = ? and bt.ACCOUNT_PERIOD = ?";

    @Override
    public List<TempBillDetailBean> findTempBillDetail(String customerId, AccountPeriod accountPeriod, SearchFilter search) {
        StringBuilder sql = new StringBuilder(SQL_FIND_TEMP_BILL_DETAIL);
        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            List<TempBillDetailBean> result = dbHelper.getBeanList(sql.toString(), TempBillDetailBean.class, customerId, accountPeriod.toString());
            return result;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }



    private static final String SQL_QUERY_PERIODIC_BILLS_GROUP_BY_CUSTOMER =
            "select customer_id, customer_name, account_period, sum(amount) as amount from " +
                    "(select c.pid as customer_id, c.display_name as customer_name, b.account_period, amount, fee_type, con.display_name as contract_name from bb_bill_temp_detail b " +
                    "inner join b_order o on b.ORDER_ID = o.pid " +
                    "inner join b_contract con on con.pid = o.contract_id " +
                    "inner join b_customer c on c.pid = b.customer_id " +
                    "where (b.FEE_TYPE = 10 or b.fee_type =12 or b.fee_type = 15 or b.fee_type = 22) and b.ACCOUNT_PERIOD = ?)" +
                    "group by customer_id, customer_name, account_period";
    public List<TempBillDetailBean> findPeriodicBillGroupByCustomer(AccountPeriod accountPeriod) {
        try {
            List<TempBillDetailBean> result = dbHelper.getBeanList(SQL_QUERY_PERIODIC_BILLS_GROUP_BY_CUSTOMER, TempBillDetailBean.class, accountPeriod.toString());
            return result;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }


    private static final String SQL_QUERY_PERIODIC_BILLS_BY_CUSTOMER =
            "select c.pid as customer_id, c.display_name as customer_name, b.account_period, amount, fee_type, con.display_name as contract_name, " +
                    "p.display_name as product_name, o.pay_interval, substr(o.effectivestartdate, 0, 10) as start_date, substr(o.effectiveenddate, 0, 10) as end_date from bb_bill_temp_detail b " +
                    "inner join b_order o on b.ORDER_ID = o.pid " +
                    "inner join b_contract con on con.pid = o.contract_id " +
                    "inner join b_customer c on c.pid = b.customer_id " +
                    "inner join b_product p on p.pid = o.product_id " +
                    "where (b.FEE_TYPE = 10 or b.fee_type =12 or b.fee_type = 15 or b.fee_type = 22) and b.ACCOUNT_PERIOD = ? and c.pid = ?";
    public List<TempBillDetailBean> findPeriodicBillByCustomer(AccountPeriod accountPeriod, String customerId) {
        try {
            List<TempBillDetailBean> result = dbHelper.getBeanList(SQL_QUERY_PERIODIC_BILLS_BY_CUSTOMER, TempBillDetailBean.class, accountPeriod.toString(), customerId);
            return result;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }
}
