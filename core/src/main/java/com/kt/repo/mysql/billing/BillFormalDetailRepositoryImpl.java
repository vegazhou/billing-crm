package com.kt.repo.mysql.billing;


import com.kt.biz.bean.*;
import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.DbHelper;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.BillFormalDetail;
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
 * Created by Vega Zhou on 2016/6/16.
 */
@Repository
public class BillFormalDetailRepositoryImpl implements BillFormalDetailRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(BillFormalDetailRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;

    private static final String SQL_FIND_ALL_UNPAID_BILLS =
            "SELECT * FROM BB_BILL_FORMAL_DETAIL WHERE unpaid_amount > 0 ORDER BY account_period ASC";

    @Override
    public List<BillFormalDetail> findUnpaidBills() {
        try {
            return dbHelper.getBeanList(SQL_FIND_ALL_UNPAID_BILLS, BillFormalDetail.class);
        } catch (SQLException e) {
            LOGGER.error("error occured in BillFormalDetailRepositoryImpl.findUnpaidBills", e);

            return null;
        }
    }


    @Override
    public Page<MonthlyBillBean> paginateMonthlyBill(AccountPeriod accountPeriod, int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("select b.*, p.TYPE, p.FAILED_TASKS, p.STATUS as PDFSTATUS, decode(c.confirmed_date, null, 0, 1) as confirmed from " +
                "(select t2.PID as customer_ID, t2.display_name as company, t2.code as customer_code, ? as account_period, " +
                "decode(t1.itemcount, null, 0, t1.itemcount) as itemcount, " +
                "decode(t1.SYNCEDCOUNT, null, 0, t1.SYNCEDCOUNT) as SYNCEDCOUNT, " +
                "decode(t1.amount, null, 0, t1.amount) as AMOUNT, " +
                "decode(t1.unpaid_amount, null, 0, t1.unpaid_amount) as unpaid_amount from " +
                "(select  customer_id, account_period, sum(itemcount) as itemcount, sum(SYNCEDCOUNT) as SYNCEDCOUNT, " +
                "sum(amount) as amount, sum(unpaid_amount) as unpaid_amount from view_bill_formal where account_period = ? " +
                "group by customer_id, account_period) t1 right join b_customer t2 on t2.pid = t1.customer_id ) b left join " +
                "billing_progress p on p.customer_ID = b.customer_ID and (b.account_period = to_char(add_months(trunc(to_date(p.billing_period, 'yyyymm')),1),'yyyymm')) " +
                "and p.type = 'SINGLE_PDF_GENERATION' " +
                "left join bb_bill_confirmation c on c.customer_ID = b.customer_ID and c.account_period = b.account_period " +
                "where 1=1 ");
        params.add(accountPeriod.toString());
        params.add(accountPeriod.toString());


        if (StringUtils.isNotBlank(search.getCustomerName())) {
            sql.append(" AND lower(COMPANY) like ?");
            params.add("%" + StringUtils.lowerCase(search.getCustomerName()) + "%");
        }

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), MonthlyBillBean.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }


    private static final String SQL_PAGINATE_BILL_ =
            "select b.*, p.TYPE, p.FAILED_TASKS, p.STATUS as PDFSTATUS, decode(c.confirmed_date, null, 0, 1) as confirmed from " +
            "(select t2.PID as customer_ID, t2.display_name as company, t2.code as customer_code, account_period, " +
            "decode(t1.itemcount, null, 0, t1.itemcount) as itemcount, " +
            "decode(t1.SYNCEDCOUNT, null, 0, t1.SYNCEDCOUNT) as SYNCEDCOUNT, " +
            "decode(t1.amount, null, 0, t1.amount) as AMOUNT, " +
            "decode(t1.unpaid_amount, null, 0, t1.unpaid_amount) as unpaid_amount from " +
            "(select  customer_id, account_period, sum(itemcount) as itemcount, sum(SYNCEDCOUNT) as SYNCEDCOUNT, " +
            "sum(amount) as amount, sum(unpaid_amount) as unpaid_amount from view_bill_formal " +
            "group by customer_id, account_period) t1 join b_customer t2 on t2.pid = t1.customer_id) b left join " +
            "billing_progress p on p.customer_ID = b.customer_ID and (b.account_period = to_char(add_months(trunc(to_date(p.billing_period, 'yyyymm')),1),'yyyymm')) " +
            "and p.type = 'SINGLE_PDF_GENERATION' " +
            "left join bb_bill_confirmation c on c.customer_ID = b.customer_ID and c.account_period = b.account_period " +
            "where 1=1 ";


    @Override
    public Page<MonthlyBillBean> paginateBillByCustomerName(String customerName, int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder(SQL_PAGINATE_BILL_);
        ArrayList<Object> params = new ArrayList<>();

        if (StringUtils.isNotBlank(customerName)) {
            sql.append(" AND lower(COMPANY) like ?");
            params.add("%" + StringUtils.lowerCase(customerName) + "%");
        }


        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), MonthlyBillBean.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;

        }
    }

    private static final String SQL_FIND_FORMAL_BILL_DETAIL =
            "select t1.*, decode(t2.tunecount, null, 0, t2.tunecount) as TUNECOUNT from " +
                    "(select bt.ID, bt.AMOUNT, bt.UNPAID_AMOUNT as UNPAIDAMOUNT, bt.FEE_TYPE as FEETYPE, bt.ACCOUNT_PERIOD as ACCOUNTPERIOD, " +
                    "p.DISPLAY_NAME as PRODUCTNAME, c.DISPLAY_NAME as CUSTOMERNAME, ct.DISPLAY_NAME as CONTRACTNAME, ct.PID as CONTRACTID, " +
                    "substr(o.effectivestartdate, 0, 10) as START_DATE, substr(o.effectiveenddate, 0, 10) as END_DATE " +
                    "from bb_bill_formal_detail bt " +
                    "inner join b_order o on bt.order_id = o.PID " +
                    "inner join b_contract ct on ct.PID = o.CONTRACT_ID " +
                    "inner join b_product p on p.PID = o.PRODUCT_ID " +
                    "inner join b_customer c on c.PID = bt.CUSTOMER_ID " +
                    "where c.PID = ? and bt.ACCOUNT_PERIOD = ? " +
                    ") t1 left join " +
                    "(select bill_id, sum(1) as tunecount from bb_fbill_tunelog group by bill_id) t2 on t1.ID = t2.bill_id " +
                    "ORDER BY t1.ID desc";

    @Override
    public List<FormalBillDetailBean> findBillDetail(String customerId, AccountPeriod accountPeriod, SearchFilter search) {
        try {
            List<FormalBillDetailBean> result = dbHelper.getBeanList(SQL_FIND_FORMAL_BILL_DETAIL, FormalBillDetailBean.class, customerId, accountPeriod.toString());
            return result;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }

    private static final String SQL_PAGINATE_BILL_DETAIL =
            "SELECT bt.ID, bt.AMOUNT, bt.UNPAID_AMOUNT AS UNPAIDAMOUNT, bt.FEE_TYPE AS FEETYPE, bt.ACCOUNT_PERIOD AS ACCOUNTPERIOD, " +
                    "p.DISPLAY_NAME AS PRODUCTNAME, " +
                    "c.DISPLAY_NAME AS CUSTOMERNAME, " +
                    "ct.DISPLAY_NAME AS CONTRACTNAME, ct.PID AS CONTRACTID " +
                    "FROM bb_bill_formal_detail bt INNER JOIN b_order o ON bt.order_id = o.PID INNER JOIN b_contract ct ON ct.PID = o.CONTRACT_ID " +
                    "INNER JOIN b_product p ON p.PID = o.PRODUCT_ID INNER JOIN b_customer c ON c.PID = bt.CUSTOMER_ID " +
                    "WHERE c.DISPLAY_NAME LIKE ? ";

    public Page<FormalBillDetailBean> paginateBillDetail(String customerName, AccountPeriod accountPeriod, int curPage, SearchFilter searchFilter) {
        String sql = SQL_PAGINATE_BILL_DETAIL;
        ArrayList<Object> params = new ArrayList<>();
        params.add("%" + customerName + "%");
        if (accountPeriod != null) {
            sql += " AND bt.ACCOUNT_PERIOD = ? ";
            params.add(accountPeriod.toString());
        }
        sql += "ORDER BY ID DESC";
        try {
            Page page = dbHelper.getPage(sql, FormalBillDetailBean.class,
                    curPage, searchFilter.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }

    
    
    @Override
    public Page<BillAge> listAllByPageForBillAge(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("select ta.*, tb.display_name from "+
			"(select t.customer_id, "+
			"sum(decode(t.age, 0, t.total_unpaid, 0)) as UNPAID_0, "+
			"sum(decode(t.age, 30, t.total_unpaid, 0)) as WITHIN30,"+ 
			"sum(decode(t.age, 60, t.total_unpaid, 0)) as EXCEEDWITHIN30,"+
			"sum(decode(t.age, 90, t.total_unpaid, 0)) as EXCEEDWITHIN60,"+
			"sum(decode(t.age, 120, t.total_unpaid, 0)) as EXCEEDWITHIN90,"+
			"sum(decode(t.age, 150, t.total_unpaid, 0)) as EXCEEDWITHIN120,"+
			"sum(decode(t.age, 180, t.total_unpaid, 0)) as EXCEEDWITHIN150,"+
			"sum(decode(t.age, 210, t.total_unpaid, 0)) as EXCEEDWITHIN180,"+
			"sum(decode(t.age, 999, t.total_unpaid, 0)) as EXCEEDOVER180 "+
			"from view_bill_age t group by t.customer_id) ta inner join b_customer tb on ta.customer_id = tb.PID");
      
        
      
        
        if (StringUtils.isNotBlank(search.getCustomerName())) {
            sql.append(" where lower(tb.DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getCustomerName()) + "%");
        }

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }


        try {
            Page page = dbHelper.getPage(sql.toString(), BillAge.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }


    private static final String SQL_QUERY_FIRST_INSTALLMENT_OF_CONTRACT =
            "select b.* from bb_bill_formal_detail b inner join b_order o on b.ORDER_ID = o.PID " +
                    "where o.CONTRACT_ID = ? and b.FEE_TYPE = o.FI_TYPE";

    @Override
    public List<BillFormalDetail> findFirstInstallmentBillOfContract(String contractId) {
        try {
            return dbHelper.getBeanList(SQL_QUERY_FIRST_INSTALLMENT_OF_CONTRACT, BillFormalDetail.class, contractId);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();

        }
    }

    private static final String SQL_1 = "select b.* from bb_bill_formal_detail b where b.customer_id = ? and unpaid_amount > 0 order by account_period asc";

    @Override
    public List<BillFormalDetail> listUnpaidBills(String customerId) {
        try {
            return dbHelper.getBeanList(SQL_1, BillFormalDetail.class, customerId);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();

        }
    }

    private static final String SQL_QUERY_UNPAID_BILLS = "select b.* from bb_bill_formal_detail b where b.customer_id = ? and account_period = ? and unpaid_amount > 0";

    @Override
    public List<BillFormalDetail> listUnpaidBills(String customerId, AccountPeriod accountPeriod) {
        try {
            return dbHelper.getBeanList(SQL_QUERY_UNPAID_BILLS, BillFormalDetail.class, customerId, accountPeriod.toString());
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();

        }
    }


    private static final String SQL_ = "select t.customer_id, c.code as customer_code, c.display_name as customer_name, t.account_type, t.ACCOUNT_PERIOD, sum(t.amount) as AMOUNT, sum(t.unpaid_amount) as UNPAID_AMOUNT from " +
            "(select b.customer_id, b.amount, decode(fee_type, 11, 'DEPOSIT', 13, 'DEPOSIT', 14, 'DEPOSIT', 21, 'CC_DEPOSIT', 22, 'CC_DEPOSIT', 'PREPAID') as account_type, " +
            "b.account_period, b.unpaid_amount from bb_bill_formal_detail b) t inner join " +
            "b_customer c on t.customer_id = c.pid " +
            "where c.display_name like ? " +
            "group by t.customer_id, c.code, c.display_name, t.account_type, t.account_period " +
            "order by account_period desc";

    @Override
    public List<BillFormalDetail> listBillsByAccountType(String customerName) {
        try {
            return dbHelper.getBeanList(SQL_, BillFormalDetail.class, "%" + customerName + "%");
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();

        }
    }


    private static final String SQL_2 =
            "select ? as account_period,  t.code || '-' || ? || '01-1' as bill_number, t.code as customer_code, t.feetype, t.totalamount, t.paidamount, t.unpaidamount, c.display_name as customer_name " +
            "from " +
            "(select * from %s " +
            "union all " +
            "select * from %s) t inner join b_customer c on t.code = c.code " +
            "order by t.code asc, t.feetype asc";
    @Override
    public List<SapBillItem> listSapBillReport(AccountPeriod accountPeriod) {
        try {
            String thisAp = accountPeriod.toString();
            String nextAp = accountPeriod.nextPeriod().toString();
            String sql = String.format(SQL_2, "monthlyfee_" + thisAp, "MONTHLYPSTNFEE_" + thisAp);
            return dbHelper.getBeanList(sql, SapBillItem.class, thisAp, nextAp);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();
        }
    }


    private static final String SQL_3 =
            "select ? as account_period,  t.code || '-' || ? || '01-1' as bill_number, t.code as customer_code, t.feetype, t.totalamount, t.paidamount, t.unpaidamount, c.display_name as customer_name " +
                    "from " +
                    "%s t inner join b_customer c on t.code = c.code " +
                    "order by t.code asc, t.feetype asc";
    @Override
    public List<SapBillItem> listSapPrepaidBillReport(AccountPeriod accountPeriod) {
        try {
            String thisAp = accountPeriod.toString();
            String nextAp = accountPeriod.nextPeriod().toString();
            String sql = String.format(SQL_3, "monthlyfee_" + thisAp);
            return dbHelper.getBeanList(sql, SapBillItem.class, thisAp, nextAp);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();
        }
    }




    @Override
    public List<SapBillItem> listSapPostpaidBillReport(AccountPeriod accountPeriod) {
        try {
            String thisAp = accountPeriod.toString();
            String nextAp = accountPeriod.nextPeriod().toString();
            String sql = String.format(SQL_3, "MONTHLYPSTNFEE_" + thisAp);
            return dbHelper.getBeanList(sql, SapBillItem.class, thisAp, nextAp);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();
        }
    }

    private static final String SQL_3_1 =
            "select ? as account_period,  t.code || '-' || ? || '01-1' as bill_number, t.code as customer_code, t.feetype, t.totalamount, t.paidamount, t.unpaidamount, c.display_name as customer_name " +
                    "from " +
                    "%s t inner join b_customer c on t.code = c.code " +
                    "where c.code in (select * from table(split(?))) " +
                    "order by t.code asc, t.feetype asc";

    @Override
    public List<SapBillItem> listSapPostpaidBillReport(AccountPeriod accountPeriod, List<String> customerCodes) {
        try {
            String thisAp = accountPeriod.toString();
            String nextAp = accountPeriod.nextPeriod().toString();
//            StringBuilder codes = new StringBuilder("(");
//            for (int i = 0; i < customerCodes.size(); i++) {
//                codes.append("'").append(customerCodes.get(i)).append("'");
//                if (i != customerCodes.size() -  1) {
//                    codes.append(",");
//                }
//            }
//            codes.append(")");

            String sql = String.format(SQL_3_1, "MONTHLYPSTNFEE_" + thisAp);
            return dbHelper.getBeanList(sql, SapBillItem.class, thisAp, nextAp, StringUtils.join(customerCodes, ","));
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();
        }
    }



    @Override
    public List<SapBillItem> listSapPrepaidBillReport(AccountPeriod accountPeriod, List<String> customerCodes) {
        try {
            String thisAp = accountPeriod.toString();
            String nextAp = accountPeriod.nextPeriod().toString();

            String sql = String.format(SQL_3_1, "MONTHLYFEE_" + thisAp);
            return dbHelper.getBeanList(sql, SapBillItem.class, thisAp, nextAp, StringUtils.join(customerCodes, ","));
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();
        }
    }


    //    private static final String SQL_4 = "select o.pid as order_id, o.contract_id,  o.product_id, o.effectivestartdate, o.effectiveenddate, " +
//            "c.is_registered, b.amount, b.fee_type, b.account_period, b.cleared_date from b_order o " +
//            "inner join b_contract c on c.pid = o.contract_id " +
//            "inner join bb_bill_formal_detail b on b.order_id = o.pid " +
//            "where c.AGENT_ID = ? " +
//            "and cleared_date like ?";

    private static final String SQL_4 = "select t1.*, t2.hosts as unit, t2.unitprice, t2.site, t3.unitprice as agent_unit_price from " +
            "(" +
            "select o.pid as order_id, o.contract_id, o.product_id, o.effectivestartdate, o.effectiveenddate, " +
            "c.is_registered, b.amount, b.fee_type, b.account_period, b.cleared_date, " +
            "cu.display_name as customer_name, c.display_name as contract_name, " +
            "p.display_name as product_name, o.charge_id, p.CHARGE_SCHEME_ID as agent_product_charge_scheme, " +
            "cu2.display_name as agent_name " +
            "from b_order o " +
            "inner join b_contract c on c.pid = o.contract_id " +
            "inner join b_customer cu on cu.pid = c.customer_id " +
            "inner join bb_bill_formal_detail b on b.order_id = o.pid " +
            "inner join b_product p on p.pid = o.product_id " +
            "inner join b_customer cu2 on cu2.pid = c.agent_id " +
            "where " +
            "c.AGENT_ID = ? and " +
            "(" +
            "(b.cleared_date is not null and b.fee_type = 1 and o.effectivestartdate >= ? and o.effectivestartdate <= ?) or " +
            "(b.fee_type <> 1 and cleared_date like ?) " +
            ")" +
            ") t1 " +
            "left join CHARGES_OVERALL t2 on t2.scheme_id = t1.charge_id " +
            "left join CHARGES_OVERALL t3 on t3.scheme_id = t1.agent_product_charge_scheme";

    @Override
    public List<AgentRebateBean> listRebateBillsOfAgent(String agentId, AccountPeriod accountPeriod) {
        try {
//            return dbHelper.getBeanList(SQL_4, AgentRebateBean.class, agentId);
            return dbHelper.getBeanList(SQL_4, AgentRebateBean.class, agentId,
                    DateUtil.formatDate(accountPeriod.beginOfThisPeriod()),
                    DateUtil.formatDate(accountPeriod.endOfThisPeriod()),
                    accountPeriod.toFormat2() + "%");
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    private static final String SQL_5 = "select account_period, customer_code, customer_name, fee_type, total_amount from " +
            "(" +
            "select c.code as customer_code, c.display_name as customer_name, b.account_period, b.amount as total_amount," +
            "b.fee_type, o.EFFECTIVESTARTDATE, o.EFFECTIVEENDDATE, " +
            "to_number(substr(o.EFFECTIVESTARTDATE, 9, 2)) as start_day_of_month " +
            "from bb_bill_formal_detail b " +
            "inner join b_customer c on b.customer_id = c.pid " +
            "inner join b_order o on b.order_id = o.pid " +
            "inner join b_product p on p.pid = o.product_id " +
            "inner join b_contract con on con.pid = o.contract_id " +
            "inner join b_sales s on s.id = con.salesman " +
            "left join b_customer c2 on o.customer_id = c2.pid " +
            "left join charges_overall charge on o.CHARGE_ID = charge.scheme_id " +
            "where (c.agent_type = 'RESELLER' or c.agent_type = 'RESELLER2') and b.amount > 0 " +
            ") " +
            "where " +
            "((" +
            "  (fee_type <> 1 and fee_type <> 11) and " +
            "  (" +
            "    (start_day_of_month < 9 and account_period = ?) " +
            "    or " +
            "    (start_day_of_month >= 9 and start_day_of_month < = 15 and account_period = ?) " +
            "    or " +
            "    (start_day_of_month >= 16 and account_period = ?) " +
            "  ) " +
            ") " +
            "or " +
            "(fee_type = 1 and (EFFECTIVESTARTDATE >= ? and EFFECTIVESTARTDATE <= ? ))) ";
    //"(fee_type = 1 and (EFFECTIVESTARTDATE >= '2017-01-09 00:00:00' and EFFECTIVESTARTDATE <= '2017-02-08 00:00:00'))";
    @Override
    public List<SapBillItem> listResellerVoipBills(AccountPeriod accountPeriod, String[] resellerCodes) {
        try {
            List<String> codes = new ArrayList<>();
            for (String code : resellerCodes) {
                codes.add("'" + code + "'");
            }
            String customer_code_clause = "and (customer_code in (" + StringUtils.join(codes, ",") + "))";
            return dbHelper.getBeanList(SQL_5 + customer_code_clause, SapBillItem.class,
                    accountPeriod.nextPeriod().toString(),
                    accountPeriod.toString(),
                    accountPeriod.nextPeriod().toString(),
                    accountPeriod.toFormat2() + "-09 00:00:00",
                    accountPeriod.nextPeriod().toFormat2() + "-08 00:00:00");
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private static final String SQL_6 = "select b.account_period, c.code as customer_code, c.display_name as customer_name, b.fee_type, b.unpaid_amount as total_amount " +
            "from bb_bill_formal_detail b " +
            "inner join b_customer c on c.pid = b.customer_id " +
            "inner join b_order o on o.pid = b.order_id " +
            "inner join b_product p on p.pid = o.product_id " +
            "inner join b_contract con on con.pid = o.contract_id " +
            "where (c.agent_type = 'RESELLER' or c.agent_type = 'RESELLER2') " +
            "and b.fee_type = 11 " +
            "and b.account_period = ?";
    @Override
    public List<SapBillItem> listResellerPstnBills(AccountPeriod accountPeriod, String[] resellerCodes) {
        try {
            List<String> codes = new ArrayList<>();
            for (String code : resellerCodes) {
                codes.add("'" + code + "'");
            }
            String customer_code_clause = "and (c.code in (" + StringUtils.join(codes, ",") + "))";
            return dbHelper.getBeanList(SQL_6 + customer_code_clause, SapBillItem.class, accountPeriod.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    private static final String SQL_LIST_PERSONAL_WEBEX_BILL =
            "select bb.id, p.display_name as product_name, o.EFFECTIVESTARTDATE as orderstartdate, o.EFFECTIVEENDDATE as orderenddate, " +
                    "bb.amount as totalAmount, bb.unpaid_amount, bb.cleared_date as writtenOffDate " +
                    "from b_order o " +
                    "inner join B_CHARGE_SCHEME c on o.charge_id = c.id and c.type = 'PSTN_PERSONAL_CHARGE' " +
                    "inner join B_CHARGE_SCHEME_ATTRIBUTES ca on c.id = ca.scheme_id and ca.attribute_name = 'DISPLAY_NAME' " +
                    "inner join b_product p on o.product_id = p.pid " +
                    "inner join bb_bill_formal_detail bb on bb.order_id = o.pid " +
                    "where o.state in ('IN_EFFECT', 'END_OF_LIFE') and ca.attribute_value = ? " +
                    "order by bb.id asc";
    @Override
    public List<PersonalWebExBill> listPersonWebExBill(String email) {
        try {
            return dbHelper.getBeanList(SQL_LIST_PERSONAL_WEBEX_BILL, PersonalWebExBill.class, email);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    private static final String SQL_RESELLER_VOIP_BILLS =
            "select id, customer_name, code as customer_code, product_name, account_period, amount, UNPAID_AMOUNT, SUBSTR(EFFECTIVESTARTDATE,0,10) AS START_DATE, SUBSTR(EFFECTIVEENDDATE,0,10) AS END_DATE from " +
                    "(" +
                    "select b.id, c.display_name as customer_name, c.code, " +
                    "p.display_name as product_name, b.account_period, b.amount, b.UNPAID_AMOUNT, b.fee_type, o.EFFECTIVESTARTDATE, o.EFFECTIVEENDDATE," +
                    "to_number(substr(o.EFFECTIVESTARTDATE, 9, 2)) as start_day_of_month " +
                    "from bb_bill_formal_detail b " +
                    "inner join b_customer c on b.customer_id = c.pid " +
                    "inner join b_order o on b.order_id = o.pid " +
                    "inner join b_product p on p.pid = o.product_id " +
                    "inner join b_contract con on con.pid = o.contract_id " +
                    "where (c.agent_type = 'RESELLER' or c.agent_type = 'RESELLER2') and b.amount > 0 and c.display_name like ? " +
                    ") " +
                    "where " +
                    "(" +
                    "  (fee_type <> 1 and fee_type <> 11) and " +
                    "  (" +
                    "    (start_day_of_month < 9 and account_period = ?) " +
                    "    or " +
                    "    (start_day_of_month >= 9 and start_day_of_month < =15 and account_period = ?) " +
                    "    or " +
                    "    (start_day_of_month >= 16 and account_period = ?) " +
                    "  )" +
                    ")" +
                    "or " +
                    "(fee_type = 1 and (EFFECTIVESTARTDATE >= ? and EFFECTIVESTARTDATE < ?)) order by amount asc";

    public List<FormalBillDetailBean> listResellerVoipBills(String customerNamePattern, AccountPeriod accountPeriod) {
        try {
            return dbHelper.getBeanList(SQL_RESELLER_VOIP_BILLS, FormalBillDetailBean.class, "%" + customerNamePattern + "%", accountPeriod.nextPeriod().toInt(), accountPeriod.toInt(), accountPeriod.nextPeriod().toInt(),
                    DateUtil.formatDate(DateUtil.setDayOfMonth(accountPeriod.beginOfThisPeriod(), 9)), DateUtil.formatDate(DateUtil.setDayOfMonth(accountPeriod.nextPeriod().beginOfThisPeriod(), 9)));
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



}
