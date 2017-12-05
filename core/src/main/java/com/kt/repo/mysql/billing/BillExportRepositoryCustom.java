package com.kt.repo.mysql.billing;


import com.kt.biz.bean.SapBillItem;
import com.kt.biz.billing.AccountPeriod;
import com.kt.entity.mysql.billing.BillExport;
import com.kt.entity.mysql.crm.Order;

import java.util.List;

/**
 * Created by Garfield Chen  on 2016/8/16.
 */
public interface BillExportRepositoryCustom {

    List<BillExport> findBills(String date);
    
    List<BillExport> findBiggestDate() ;

    List<BillExport> findSmallestDate() ;

    List<BillExport> findAllBills(String date) ;
    
    List<BillExport> findInvoice(String date) ;
    
    List<BillExport> findInvoiceCountByName(String invoiceName );

    List<SapBillItem> findCreditNotesByAccountPeriod(AccountPeriod accountPeriod);
    
    List<Order> findOrderByChargeType(String date);
}
