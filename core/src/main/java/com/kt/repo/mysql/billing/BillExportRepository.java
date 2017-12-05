package com.kt.repo.mysql.billing;


import com.kt.biz.billing.AccountPeriod;
import com.kt.entity.mysql.billing.BillExport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by Garfield Chen  on 2016/8/16.
 */
public interface BillExportRepository extends JpaRepository<BillExport,  Long>, BillExportRepositoryCustom {


	List<BillExport> findByInvoiceNameContainingAndNetInvoicedAmountNotOrderByInvoiceNumberAsc(String date,float data1);
	
	List<BillExport> findByInvoiceNameAndNetInvoicedAmountNotOrderByInvoiceNumberAsc(String date,float data1);
	
	BillExport findByOrderId(String orderId);
	
	 void deleteByInvoiceNameAndProductTypeAndOrderId(String invoiceName, String productType,String orderId);
}
