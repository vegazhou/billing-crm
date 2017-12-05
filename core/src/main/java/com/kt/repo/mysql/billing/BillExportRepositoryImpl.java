package com.kt.repo.mysql.billing;


import com.kt.biz.bean.SapBillItem;
import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.DbHelper;
import com.kt.entity.mysql.billing.BillExport;
import com.kt.entity.mysql.crm.Order;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garfield Chen  on 2016/8/16.
 */
@Repository
public class BillExportRepositoryImpl implements BillExportRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(BillExportRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;





    //private static final String SQL_QUERY_BILLS = "select * from (select substr(invoice_name,instr(invoice_name,'-20')+1,8) as indesdate, b_Bill_invoice.*  from b_Bill_invoice where net_invoiced_amount!=0 and product_type!='AUDIO_USEAGE_OR_OVERAGE_INVOICE' and product_type!='NETWORK_USAGE_OR_OVERAGE_INVOICE' and customer_id!='KT00203' order by indesdate asc) t where t.invoice_name like ? order by t.invoice_name asc ";
    private static final String SQL_QUERY_SMALLEST_BILLS = "select * from (select b_bill_invoice.* ,to_char(to_date(contract_commence, 'yyyy/mm/dd'),'yyyy-mm-dd') as smallestdate from b_bill_invoice order by contract_commence asc) where rownum=1";
    private static final String SQL_QUERY_BIGGEST_BILLS = "select * from (select b_bill_invoice.*, to_char(add_months(to_date(contract_commence, 'yyyy/mm/dd'), contract_term),'yyyy/mm/dd') as biggestdate from b_Bill_invoice where contract_commence!='00' and before_tax_value!=0 order by biggestdate desc) where rownum=1";
    //private static final String SQL_QUERY_ALL_BILLS = "select * from (select substr(invoice_name,instr(invoice_name,'-20')+1,8) as indesdate, b_Bill_invoice.*  from b_Bill_invoice where net_invoiced_amount!=0 and product_type!='AUDIO_USEAGE_OR_OVERAGE_INVOICE' and product_type!='NETWORK_USAGE_OR_OVERAGE_INVOICE' and customer_id!='KT00203'  order by indesdate asc) t where t.indesdate<=? and t.indesdate>='20161001'";
    //private static final String SQL_QUERY_ALL_BILLS = "select * from (select substr(invoice_name,instr(invoice_name,'-20')+1,8) as indesdate, b_Bill_invoice.*  from b_Bill_invoice where net_invoiced_amount!=0 and product_type='AUDIO_USEAGE_OR_OVERAGE_INVOICE' or product_type='NETWORK_USAGE_OR_OVERAGE_INVOICE' and customer_id!='KT00203'  order by indesdate asc) t where t.indesdate<=? and t.indesdate>='20161001'";
    //private static final String SQL_QUERY_BILLS = "select * from (select substr(invoice_name,instr(invoice_name,'-20')+1,8) as indesdate, b_Bill_invoice.*  from b_Bill_invoice where net_invoiced_amount!=0 and product_type='AUDIO_USEAGE_OR_OVERAGE_INVOICE' or product_type='NETWORK_USAGE_OR_OVERAGE_INVOICE' and customer_id!='KT00203' order by indesdate asc) t where t.invoice_name like ? order by t.invoice_name asc ";
    
    private static final String SQL_QUERY_ALL_BILLS = "select * from (select substr(invoice_name,instr(invoice_name,'-20')+1,8) as indesdate, b_Bill_invoice.*  from b_Bill_invoice where net_invoiced_amount!=0 and  customer_id!='KT00203'  order by indesdate asc) t where t.indesdate<=? and t.indesdate>='20161001'";
    private static final String SQL_QUERY_BILLS = "select * from (select substr(invoice_name,instr(invoice_name,'-20')+1,8) as indesdate, b_Bill_invoice.*  from b_Bill_invoice where net_invoiced_amount!=0 and  customer_id!='KT00203' order by indesdate asc) t where t.invoice_name like ? order by t.invoice_name asc ";
    //private static final String SQL_QUERY_INVOICE_ALL ="select  invoice_name,sum(net_invoiced_amount) as net_invoiced_amount ,sum(round(net_invoiced_amount,2)) as INVOICED_AMOUNT ,sum(BEFORE_TAX_VALUE) as BEFORE_TAX_VALUE from b_bill_invoice where  (revenue_type='Audio usage or overage' or revenue_type='Network usage or overage') and net_invoiced_amount!=0 and  customer_id!='KT00203' and invoice_name like ? group by invoice_name order by invoice_name asc";
    //private static final String SQL_QUERY_INVOICE_ALL ="select  invoice_name,sum(net_invoiced_amount) as net_invoiced_amount ,sum(round(net_invoiced_amount,2)) as INVOICED_AMOUNT ,sum(BEFORE_TAX_VALUE) as BEFORE_TAX_VALUE from b_bill_invoice where  (revenue_type!='Audio usage or overage' and revenue_type!='Network usage or overage') and net_invoiced_amount!=0 and  customer_id!='KT00203' and invoice_name like ? group by invoice_name order by invoice_name asc";
    private static final String SQL_QUERY_INVOICE_ALL ="select  invoice_name,sum(net_invoiced_amount) as net_invoiced_amount ,sum(round(net_invoiced_amount,2)) as INVOICED_AMOUNT ,sum(BEFORE_TAX_VALUE) as BEFORE_TAX_VALUE from b_bill_invoice where  net_invoiced_amount!=0 and  customer_id!='KT00203' and invoice_name like ? group by invoice_name order by invoice_name asc";
    private static final String SQL_QUERY_BILLING_COUNT ="select invoice_name from b_bill_invoice where invoice_name like ? group by invoice_name";

    @Override
    public List<BillExport> findBills(String date) {
        try {        	
            return dbHelper.getBeanList(SQL_QUERY_BILLS, BillExport.class, "%"+date+"%");
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();

        }
    }
    @Override
    public List<BillExport> findBiggestDate() {
        try {        	
            return dbHelper.getBeanList(SQL_QUERY_BIGGEST_BILLS, BillExport.class);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();

        }
    }
    @Override
    public List<BillExport> findSmallestDate() {
        try {        	
            return dbHelper.getBeanList(SQL_QUERY_SMALLEST_BILLS, BillExport.class);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();

        }
    }
    
    
    @Override
    public List<BillExport> findAllBills(String date ) {
        try {        	
            return dbHelper.getBeanList(SQL_QUERY_ALL_BILLS, BillExport.class,date);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();

        }
    }
    
    
    @Override
    public List<BillExport> findInvoice(String date ) {
        try {        	
            return dbHelper.getBeanList(SQL_QUERY_INVOICE_ALL, BillExport.class,"%"+date+"%");
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();

        }
    }
    
    @Override
    public List<BillExport> findInvoiceCountByName(String invoiceName ) {
        try {        	
            return dbHelper.getBeanList(SQL_QUERY_BILLING_COUNT, BillExport.class,"%"+invoiceName+"%");
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();
        }
    }


    private static final String SQL_QUERY_CREDITNOTES = "";
    @Override
    public List<SapBillItem> findCreditNotesByAccountPeriod(AccountPeriod accountPeriod) {
        try {
            return dbHelper.getBeanList(SQL_QUERY_CREDITNOTES, SapBillItem.class, accountPeriod);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    
    private static final String SQL_QUERY_ORDERS = "select  b.* from b_charge_scheme a, b_order b ,b_customer c  where b.charge_id=a.id and a.type='PSTN_PERSONAL_PACKET' and b.effectivestartdate like ? and c.pid= b.customer_id and b.effectivestartdate<'2017-08-15' order by b.effectivestartdate";
    @Override
    public List<Order> findOrderByChargeType(String date) {
        try {
            return dbHelper.getBeanList(SQL_QUERY_ORDERS, Order.class, "%"+date+"%");
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
