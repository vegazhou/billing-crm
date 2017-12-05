package com.kt.api.controller;

import com.kt.api.bean.bill.ChargeForm;
import com.kt.api.common.APIConstants;
import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.bean.FormalBillDetailBean;
import com.kt.biz.bean.OrderTableRow;
import com.kt.biz.bean.SapBillItem;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.PSTNPersonalPacket;
import com.kt.biz.model.charge.impl.PSTNSinglePacket4MultiSites;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.sap.SapPdf;
import com.kt.common.Constants;
import com.kt.entity.mysql.billing.BillConfirmation;
import com.kt.entity.mysql.billing.BillExport;
import com.kt.entity.mysql.crm.Customer;
import com.kt.entity.mysql.crm.Order;
import com.kt.exception.OrderNotFoundException;
import com.kt.exception.ProductNotFoundException;
import com.kt.exception.WafException;
import com.kt.repo.mysql.batch.CustomerRepository;
import com.kt.repo.mysql.billing.BillExportRepository;
import com.kt.service.ContractService;
import com.kt.service.OrderService;
import com.kt.service.ProductService;
import com.kt.service.SapReportService;
import com.kt.service.SearchFilter;
import com.kt.service.billing.BillService;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
import com.kt.service.CustomerService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import wang.huaichao.Global;
import wang.huaichao.PdfCreator;
import wang.huaichao.data.mongo.BillPdf;
import wang.huaichao.data.mongo.BillPdfRepository;
import wang.huaichao.data.repo.BillConfirmationRepository;
import wang.huaichao.data.service.BillingService;
import wang.huaichao.data.service.CrmDataService;
import wang.huaichao.data.service.EmailService;
import wang.huaichao.exception.BillingException;
import wang.huaichao.utils.AsyncTaskUtils;
import wang.huaichao.utils.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Garfield chen on 2016/8/22.
 */
@Controller
@RequestMapping("/billexport")
public class BillExportController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(BillExportController.class);

    @Autowired
    BillService billService;
    
    @Autowired
    private BillingService billingService;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    public BillPdfRepository billPdfRepository;
    
    @Autowired
    private EmailService emailService;

	@Autowired
	private SapReportService sapReportService;
	
	@Autowired
    private CrmDataService crmDataService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	BillExportRepository billExportRepository;
	
	@Autowired
	OrderService orderService;
	 
	@Autowired
	ProductService productService;
	
	@Autowired
	ContractService contractService;
    
    @Autowired
    @Qualifier("BillConfirmationRepository2")
    private BillConfirmationRepository billConfirmationRepository;

    @RequestMapping(value = "/createpdf", method = RequestMethod.POST, produces = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> createpdf(@Valid @RequestBody ChargeForm bean) {
		try {
			String customerId=bean.getCustomerId(); 
			AccountPeriod accountPeriod=new AccountPeriod(bean.getAccountPeriod());
			int accountPeriodInt=accountPeriod.previousPeriod().toInt();
			Future future = billingService.generateBillPdf(accountPeriodInt, customerId);
	        while (!future.isDone()) {
	            Thread.sleep(1000);
	        }
	        future.get();
			
			
			//billService.charge(bean.getCustomerId(), new AccountPeriod(bean.getAccountPeriod()), FeeType.valueOf(bean.getFeeType()));
		} catch (Exception e) {
			LOGGER.error("Exception createpdf", e);
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

	}
    
    
    
    @RequestMapping(value = "/createallpdf", method = RequestMethod.POST, produces = APIConstants.MEDIATYPE)
 	@ResponseStatus(HttpStatus.NO_CONTENT)
 	public ResponseEntity<String> createallpdf(@Valid @RequestBody ChargeForm bean) {
 		try {
 			String customerId=bean.getCustomerId(); 
 			AccountPeriod accountPeriod=new AccountPeriod(bean.getAccountPeriod());
 			int accountPeriodInt=accountPeriod.previousPeriod().toInt();
 			Future future = billingService.generateAllBillPdf(accountPeriodInt);
 	        while (!future.isDone()) {
 	            Thread.sleep(1000);
 	        }
 	        future.get();
 			
 			
 			//billService.charge(bean.getCustomerId(), new AccountPeriod(bean.getAccountPeriod()), FeeType.valueOf(bean.getFeeType()));
 		} catch (Exception e) {
 			LOGGER.error("Exception createallpdf", e);
 			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
 		}

 		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

 	}
    
    
    @RequestMapping(value = "/sendmail", method = RequestMethod.POST, produces = APIConstants.MEDIATYPE)
  	@ResponseStatus(HttpStatus.NO_CONTENT)
  	public ResponseEntity<String> sendMail(@Valid @RequestBody ChargeForm bean) {
  		try {
  			String customerId=bean.getCustomerId(); 
  			AccountPeriod accountPeriod=new AccountPeriod(bean.getAccountPeriod());
  			int accountPeriodInt=accountPeriod.previousPeriod().toInt();
  			emailService.sendPdfBill(customerId, accountPeriodInt, null);
  	      
  			
  			
  			//billService.charge(bean.getCustomerId(), new AccountPeriod(bean.getAccountPeriod()), FeeType.valueOf(bean.getFeeType()));
  		} catch (Exception e) {
  			LOGGER.error("Exception sendMail", e);
  			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
  		}

  		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

  	}
    
    
    @RequestMapping(value = "/sendallmail", method = RequestMethod.POST, produces = APIConstants.MEDIATYPE)
  	@ResponseStatus(HttpStatus.NO_CONTENT)
  	public ResponseEntity<String> sendallMail(@Valid @RequestBody ChargeForm bean) {
  		try {
  			
  			AccountPeriod accountPeriod=new AccountPeriod(bean.getAccountPeriod());
  			int accountPeriodInt=accountPeriod.previousPeriod().toInt();
  	      
  			//Future future = emailService.batchSendPdfBill(accountPeriodInt, null);  	    
  	        //future.get();
  	        
  			//billService.charge(bean.getCustomerId(), new AccountPeriod(bean.getAccountPeriod()), FeeType.valueOf(bean.getFeeType()));
  		} catch (Exception e) {
  			LOGGER.error("Exception sendMail", e);
  			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
  		}

  		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

  	}
    
    
    @RequestMapping(value = "/createinvoicepdf/{accountperiod}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
  	@ResponseStatus(HttpStatus.NO_CONTENT)
  	public ResponseEntity<String> createInvoicePdf(HttpServletResponse response,@PathVariable String accountperiod) {
  		try {
  			
  			String billterm=accountperiod;
  			AccountPeriod accountPeriod=new AccountPeriod(billterm);
			String strAccountPeriod=accountPeriod.previousPeriod().toString();
			List<BillExport> invoiceList=billService.getInvoice(billterm);			
  			
  		
  	        
  	        String strBillterEndDate="";
  	  	    String strBillterDate="";
  	  	    String content="";
  			for (int i = 0, n = invoiceList.size(); i <n;i++){
   				BillExport	billExport=invoiceList.get(i);
   				//BillExport  billExportDetail=billService.getOneInvoiceByName(billExport.getInvoiceName()).get(0);
   				BillExport  billExportDetail=getBillExport(billExport.getInvoiceName());
   				
   				if(billExportDetail!=null&&billExportDetail.getContractTerm()==0){
   					Date billtermDate=DateUtil.toInvoiceDate(strAccountPeriod+"01");
   					strBillterDate=DateUtil.formatShortInvoiceDate(billtermDate);
   					Calendar calendar = Calendar.getInstance();
   			        calendar.setTime(billtermDate);
   			        calendar.add(Calendar.MONTH, 1);    //加一个月
   			        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
   			        calendar.add(Calendar.DATE, -1); 
   			        strBillterEndDate=DateUtil.formatShortInvoiceDate(calendar.getTime());
	   			 	//row3.createCell(1).setCellValue(strBillterDate+" 00:00:00");      
	   			 	//row3.createCell(2).setCellValue(strBillterEndDate+" 23:59:59");
   				}else if(billExportDetail!=null&&billExportDetail.getContractTerm()!=0&&billExportDetail.getPaymentInterval()==1){		   					
   					strBillterDate=billExportDetail.getContractCommence(); 
   					Date billtermDate=null;
   					if(billExportDetail.getContractCommence().indexOf("/")>0){
   						billtermDate=DateUtil.toShortInvoiceDate(billExportDetail.getContractCommence());
   					}else{
   						billtermDate=DateUtil.toShortDate(billExportDetail.getContractCommence());

   					}
   					Calendar calendar1 = Calendar.getInstance();
   			        calendar1.setTime(billtermDate);
   			        calendar1.add(Calendar.MONTH, 1);    //加一个月
   			        calendar1.set(Calendar.DATE, 1);        //设置为该月第一天
   			        calendar1.add(Calendar.DATE, -1); 
   			        int days=Integer.valueOf(billExportDetail.getContractCommence().substring(8, 10));
   			        if(days>15){
   			        	calendar1.add(Calendar.MONTH, billExportDetail.getContractTerm());
   			        }else{
   			        	calendar1.add(Calendar.MONTH, billExportDetail.getContractTerm()-1);
   			        }
   			        strBillterEndDate=DateUtil.formatShortInvoiceDate(calendar1.getTime());
   					
   				}else if(billExportDetail!=null&&billExportDetail.getContractTerm()!=0&&billExportDetail.getPaymentInterval()>1){
   					Date billtermDate=null;		   					
   					if(billExportDetail.getContractCommence().indexOf("/")>0){
   						billtermDate=DateUtil.toShortInvoiceDate(billExportDetail.getContractCommence());
   					}else{
   						billtermDate=DateUtil.toShortDate(billExportDetail.getContractCommence());

   					}
   					String exportContractCommencYearAndMonth=DateUtil.formatInvoiceNameDate(billtermDate).substring(0, 6);
   					if(!exportContractCommencYearAndMonth.equals(billterm)){
   						billtermDate=DateUtil.toInvoiceDate(billterm+"01");
   					}
   					strBillterDate=DateUtil.formatShortInvoiceDate(billtermDate);
   					int months=billExportDetail.getContractTerm()/billExportDetail.getPaymentInterval();
   					
   					Calendar calendar2 = Calendar.getInstance();
   			        calendar2.setTime(billtermDate);
   			        calendar2.add(Calendar.MONTH, months);    //加一个月
   			        calendar2.set(Calendar.DATE, 1);        //设置为该月第一天
   			        calendar2.add(Calendar.DATE, -1); 
   					  
   					strBillterEndDate=DateUtil.formatShortInvoiceDate(calendar2.getTime());
	   			 	
   					
   				}
   				strBillterDate	=strBillterDate	+" 00:00:00";
   	  	        strBillterEndDate	=strBillterEndDate+" 23:59:59";
   	  	      
                content=content+billService.createInvoicePdfHtmlContent(billterm,billExportDetail,strBillterDate,strBillterEndDate);
                
                //if(i==1){break;}
   				
   			}
  	        
  			String all=billService.createInvoicePdfHtmlHeader(content);
            
				
	  	        String pdfName="d:\\zilupdfs\\allone.pdf";
	  	        //String codeStr=URLEncoder.encode(content, "utf-8");
	  	        //System.out.print(all);
	            //sapReportService.saveAsPdf(all, pdfName);
	           
	            
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            PdfCreator.render(all, baos);
	            InputStream inputStream	=	new ByteArrayInputStream(baos.toByteArray());
	   		    String pdfFileName="ALl_"+accountPeriod+ ".pdf";
	   			
	   			response.setContentType("multipart/form-data");  
	   	      
	   	        response.setHeader("Content-Disposition", "attachment;fileName="+pdfFileName); 
		        
		        
		        ServletOutputStream out = response.getOutputStream();  
		        
	   			int len; 
	           
	            byte[] buffer = new byte[2048];
	            while ((len = inputStream.read(buffer)) != -1) {
	            	out.write(buffer, 0, len);
	            }
	          
	            inputStream.close();  
	            out.close();  
	            out.flush();  
  	       


  			
  			
  		} catch (Exception e) {
  			LOGGER.error("Exception sendMail", e);
  			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
  		}

  		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);

  	}
    
    
    
    
    @RequestMapping(value = "/exportpdf/{customerId}/{accountperiod}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
   	@ResponseStatus(HttpStatus.NO_CONTENT)
   	public  void exportpdf(HttpServletResponse response,@PathVariable String customerId,@PathVariable String accountperiod) {
   		try {
   			 			
   			AccountPeriod accountPeriod=new AccountPeriod(accountperiod);
   			int accountPeriodInt=accountPeriod.previousPeriod().toInt();
   			Customer customer = customerRepository.findOne(customerId);
   			
   		    BillPdf pdfFile	= billPdfRepository.findByCustomerIdAndBillingPeriod(customerId, accountPeriodInt);
   		    InputStream inputStream	=	new ByteArrayInputStream(pdfFile.getPdfContent());
   		    String pdfFileName=customer.getCode() + "_" + customerId +"_"+accountPeriod+ ".pdf";
   			
   			response.setContentType("multipart/form-data");  
   	      
   	        response.setHeader("Content-Disposition", "attachment;fileName="+pdfFileName); 
   			//FileInputStream inputStream = new FileInputStream(file);  
   			
       
   			ServletOutputStream out = response.getOutputStream();  
  
   			int len; 
           
            byte[] buffer = new byte[2048];
            while ((len = inputStream.read(buffer)) != -1) {
            	out.write(buffer, 0, len);
            }
          
            inputStream.close();  
            out.close();  
            out.flush();  
  
 			 

   			
   			//billService.charge(bean.getCustomerId(), new AccountPeriod(bean.getAccountPeriod()), FeeType.valueOf(bean.getFeeType()));
   		} catch (Exception e) {
   			LOGGER.error("error export pdf bill ", e);
   		}
   		
   		

   	}



	@RequestMapping(value = "/exportzilupdf/{accountperiod}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void exportZiLuPdf(HttpServletResponse response,@PathVariable String accountperiod) {
		try {

			AccountPeriod accountPeriod=new AccountPeriod(accountperiod);
			List<SapBillItem> items = sapReportService.findSapPostpaidBillsByAccountPeriod(accountPeriod);
			List<String> customerCodes = CollectionUtils.collectString(items, "customerCode");

			int accountPeriodInt = accountPeriod.previousPeriod().toInt();
			List<BillPdf> pdfs = billPdfRepository.findByBillingPeriod(accountPeriodInt);

			ZipOutputStream out = new ZipOutputStream(response.getOutputStream());

			for (BillPdf pdf : pdfs) {
				if (customerCodes.contains(pdf.getCustomerCode())) {
					ZipEntry zipEntry = new ZipEntry(pdf.getCustomerCode() + "-" + pdf.getCustomerId() + ".pdf");
					out.putNextEntry(zipEntry);
					out.write(pdf.getPdfContent());
					out.closeEntry();
				}
			}

			out.close();
			out.flush();



			//billService.charge(bean.getCustomerId(), new AccountPeriod(bean.getAccountPeriod()), FeeType.valueOf(bean.getFeeType()));
		} catch (Exception e) {
			LOGGER.error("error export pdf bill ", e);
		}
	}
    
    
    
    @RequestMapping(value = "/exportallpdf/{accountperiod}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
   	@ResponseStatus(HttpStatus.NO_CONTENT)
   	public  void exportallpdf(HttpServletResponse response,@PathVariable String accountperiod) {
   		try {
   		
   			AccountPeriod accountPeriod=new AccountPeriod(accountperiod);
   			int accountPeriodInt=accountPeriod.previousPeriod().toInt();
   			//Customer customer = customerRepository.findOne(customerId);
   			
   			//List<BillConfirmation> confirmations = billConfirmationRepository.findByAccountPeriod(accountPeriod.toString());
   	        //List<String> customerIds = CollectionUtils.collectString(confirmations, "customerId");
   	        
   	        List<String> formalActiveCustomers = crmDataService.findFormalActiveCustomers(accountPeriod.previousPeriod().toInt());
   	        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
	   	     for (String customerId : formalActiveCustomers) {
	   	    	 	//System.out.println("1----"+customerId);
	    		    BillPdf pdfFile	= billPdfRepository.findByCustomerIdAndBillingPeriod(customerId,accountPeriodInt);
	    		    //System.out.println("2----"+pdfFile.getCustomerCode()+pdfFile.getCustomerId());
	    		    if(pdfFile!=null&&pdfFile.getCustomerCode()!=null&&pdfFile.getCustomerId()!=null){
	    		    	Customer c = customerService.findByCustomerId(pdfFile.getCustomerId());
		    		    ZipEntry zipEntry = new ZipEntry(c.getDisplayName()+"-"+pdfFile.getCustomerCode() + "-" + pdfFile.getCustomerId() + ".pdf");
		    		    
			            out.putNextEntry(zipEntry);
			            out.write(pdfFile.getPdfContent());
			            out.closeEntry();
	    		    }
	         }
	   	     
	   	     
	   	   //List<BillPdf> pdfs = billPdfRepository.findByBillingPeriod(accountPeriodInt);

	       // String zipFile = "c:\\" + File.separator + "all.zip";

	       

		
	        out.close();
	        out.flush();


   			
   			//billService.charge(bean.getCustomerId(), new AccountPeriod(bean.getAccountPeriod()), FeeType.valueOf(bean.getFeeType()));
   		} catch (Exception e) {
   			LOGGER.error("error export pdf bill ", e);
   		}
   		
   		

   	}
    
    @RequestMapping(value = "/createpstnpackage/{billterm}", method = RequestMethod.GET)
   	@ResponseStatus(HttpStatus.NO_CONTENT)
   	public void createPstnPackage(HttpServletRequest request,HttpServletResponse response, @PathVariable String billterm) {
    	billterm="2017-08";
    	List<Order> orderList=billExportRepository.findOrderByChargeType(billterm);
    	for (int i = 0, n = orderList.size(); i <n;i++){
    		Order orderOriginal=orderList.get(i);
    		String customerId = orderOriginal.getCustomerId();
    		try {
				OrderBean order = orderService.findOrderBeanById(orderOriginal
						.getPid());
				Customer customer = customerRepository.findOne(customerId);
				String code = customer.getCode();
				String partName1 = code
						+ "-"
						+ DateUtil.formatInvoiceNameDate(order.getStartDate())
								.substring(0, 6) + "01";
				int countIndex1 = 2 + billService.findInvoiceCountByName(
						partName1).size();
				String invoiceName = partName1 + "-" + countIndex1;

				String startDate = DateUtil.formatShortInvoiceDate(order
						.getStartDate());
				String productDesc = "";
				String productType = "";
				String documentDate = DateUtil
						.formatShortInvoiceDate(new Date());
				String orderId = order.getId();
				float beforeTaxValue = 0;

				productDesc = productService.findById(
						orderOriginal.getProductId()).getDisplayName();
				beforeTaxValue = orderOriginal.getFirstInstallment()
						.floatValue();
				productType = productDesc;

				int contractTerm = 12;
				float amount = 1;
				int unit = 1;
				int interval = 1;
				String revenueType = Constants.PSTN_PERSONAL_PACKET;
				contractService.insertInvoiceData(invoiceName, documentDate,
						code, revenueType, startDate, productDesc, productType,
						interval, unit, contractTerm, amount, orderId,
						beforeTaxValue);
    		  
    		    } catch (OrderNotFoundException e) {
    		        // TODO Auto-generated catch block

    		        e.printStackTrace();
    		    } catch (ProductNotFoundException e) {
    		        // TODO Auto-generated catch block
    		        e.printStackTrace();
    		    } catch (WafException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		
    		 	
		}
    	
 
 
    	
    }
    @RequestMapping(value = "/exportinvoice/{billterm}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void exportInvoiceList(HttpServletRequest request,HttpServletResponse response, @PathVariable String billterm) {
    	
    	 HSSFWorkbook wkb = new HSSFWorkbook();  
    	 CreationHelper createHelper = wkb.getCreationHelper();
    	 CellStyle floatFormat = wkb.createCellStyle();    	
         floatFormat.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
    	 List<BillExport> invoiceList=billService.getInvoice(billterm);
    	 HSSFSheet sheet=wkb.createSheet("sheet");  
    	 HSSFRow rowhead1sheet0=sheet.createRow(0);  
		 rowhead1sheet0.createCell(0).setCellValue("Invoice Numbers"); 
		 rowhead1sheet0.createCell(1).setCellValue("Start Dates of Service"); 
		 rowhead1sheet0.createCell(2).setCellValue("End Dates of Service"); 
		 rowhead1sheet0.createCell(3).setCellValue("Amount"); 
		 rowhead1sheet0.createCell(4).setCellValue("Amount(excl. VAT and Surcharge)"); 
		 
		 String filename="Invoice List.xls";
	      
		    //输出Excel文件  
		        OutputStream output;
				try {
					double sumNetInvoicedAmount =0;
					double  sumInvoiceAmount=0;
					AccountPeriod accountPeriod=new AccountPeriod(billterm);
					String strAccountPeriod=accountPeriod.previousPeriod().toString();
					for (int i = 0, n = invoiceList.size(); i <n;i++){
		   				BillExport	billExport=invoiceList.get(i);
		   				//BillExport  billExportDetail=billService.getOneInvoiceByName(billExport.getInvoiceName()).get(0);
		   				BillExport  billExportDetail=getBillExport(billExport.getInvoiceName());
		   				HSSFRow row3=sheet.createRow(i+1);		   				
		   				row3.createCell(0).setCellValue(billExport.getInvoiceName());  
		   			
		   				if(billExportDetail!=null&&billExportDetail.getContractTerm()==0){
		   					Date billtermDate=DateUtil.toInvoiceDate(strAccountPeriod+"01");
		   					String strBillterDate=DateUtil.formatShortInvoiceDate(billtermDate);
		   					Calendar calendar = Calendar.getInstance();
		   			        calendar.setTime(billtermDate);
		   			        calendar.add(Calendar.MONTH, 1);    //加一个月
		   			        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
		   			        calendar.add(Calendar.DATE, -1); 
		   			        String strBillterEndDate=DateUtil.formatShortInvoiceDate(calendar.getTime());
			   			 	row3.createCell(1).setCellValue(strBillterDate+" 00:00:00");      
			   			 	row3.createCell(2).setCellValue(strBillterEndDate+" 23:59:59");
		   				}else if(billExportDetail!=null&&billExportDetail.getContractTerm()!=0&&billExportDetail.getPaymentInterval()==1){		   					
		   					row3.createCell(1).setCellValue(billExportDetail.getContractCommence()+" 00:00:00"); 
		   					Date billtermDate=null;
		   					if(billExportDetail.getContractCommence().indexOf("/")>0){
		   						billtermDate=DateUtil.toShortInvoiceDate(billExportDetail.getContractCommence());
		   					}else{
		   						billtermDate=DateUtil.toShortDate(billExportDetail.getContractCommence());

		   					}
		   					Calendar calendar1 = Calendar.getInstance();
		   			        calendar1.setTime(billtermDate);
		   			        calendar1.add(Calendar.MONTH, 1);    //加一个月
		   			        calendar1.set(Calendar.DATE, 1);        //设置为该月第一天
		   			        calendar1.add(Calendar.DATE, -1); 
		   			        int days=Integer.valueOf(billExportDetail.getContractCommence().substring(8, 10));
		   			        if(days>15){
		   			        	calendar1.add(Calendar.MONTH, billExportDetail.getContractTerm());
		   			        }else{
		   			        	calendar1.add(Calendar.MONTH, billExportDetail.getContractTerm()-1);
		   			        }
		   			        String strBillterEndDate=DateUtil.formatShortInvoiceDate(calendar1.getTime());
			   			 	row3.createCell(2).setCellValue(strBillterEndDate+" 23:59:59");
		   					
		   				}else if(billExportDetail!=null&&billExportDetail.getContractTerm()!=0&&billExportDetail.getPaymentInterval()>1){
		   					Date billtermDate=null;		   					
		   					if(billExportDetail.getContractCommence().indexOf("/")>0){
		   						billtermDate=DateUtil.toShortInvoiceDate(billExportDetail.getContractCommence());
		   					}else{
		   						billtermDate=DateUtil.toShortDate(billExportDetail.getContractCommence());

		   					}
		   					String exportContractCommencYearAndMonth=DateUtil.formatInvoiceNameDate(billtermDate).substring(0, 6);
		   					if(!exportContractCommencYearAndMonth.equals(billterm)){
		   						billtermDate=DateUtil.toInvoiceDate(billterm+"01");
		   					}
		   					String strBillterDate=DateUtil.formatShortInvoiceDate(billtermDate);
		   					int months=billExportDetail.getContractTerm()/billExportDetail.getPaymentInterval();
		   					
		   					Calendar calendar2 = Calendar.getInstance();
		   			        calendar2.setTime(billtermDate);
		   			        calendar2.add(Calendar.MONTH, months);    //加一个月
		   			        calendar2.set(Calendar.DATE, 1);        //设置为该月第一天
		   			        calendar2.add(Calendar.DATE, -1); 
		   					row3.createCell(1).setCellValue(strBillterDate+" 00:00:00");      
		   					String strBillterEndDate=DateUtil.formatShortInvoiceDate(calendar2.getTime());
			   			 	row3.createCell(2).setCellValue(strBillterEndDate+" 23:59:59");
		   					
		   				}
		   				else{
		   					row3.createCell(1).setCellValue("");      
			   			 	row3.createCell(2).setCellValue("");
		   				}
		   				HSSFCell cell3= row3.createCell(3);
		   				HSSFCell cell4= row3.createCell(4);
		   				float beforeTaxValue=0;
		   				float netInvoicedAmount=0;
			   			 if(billExport.getInvoiceName().equals("KT02282-20170401-2")){
			   				 int aa=0;
			   			 }
		   				if(billExport.getBeforeTaxValue()!=0){
		   					beforeTaxValue=billExport.getBeforeTaxValue();
		   					//netInvoicedAmount=billExport.getBeforeTaxValue()/Constants.TAXRATE;
		   					netInvoicedAmount=billExport.getInvoicedAmount();
		   					cell3.setCellValue(Double.parseDouble(Float.toString(beforeTaxValue)));
		   					sumInvoiceAmount=sumInvoiceAmount+Double.parseDouble(Float.toString(beforeTaxValue));
		   				}else{
		   					beforeTaxValue=billExport.getNetInvoicedAmount()*1.0672f;
		   					netInvoicedAmount=billExport.getInvoicedAmount();
		   					cell3.setCellValue(Double.parseDouble(changeValueToBigDecimalFromFloat(beforeTaxValue).toString()));
		   					sumInvoiceAmount=sumInvoiceAmount+Double.parseDouble(changeValueToBigDecimalFromFloat(beforeTaxValue).toString());
		   				}
		   				
		   				cell3.setCellType(cell3.CELL_TYPE_NUMERIC);
		   				cell3.setCellStyle(floatFormat);
		   				if(billExport.getBeforeTaxValue()!=0){
		   					cell4.setCellValue(Double.parseDouble(changeValueToBigDecimalFromFloat(billExport.getInvoicedAmount()).toString()));
		   				}else{
		   					cell4.setCellValue(Double.parseDouble(changeValueToBigDecimalFromFloat(billExport.getInvoicedAmount()).toString()));
		   			    }
		   			    cell4.setCellType(cell4.CELL_TYPE_NUMERIC);
		   			    cell4.setCellStyle(floatFormat);
		   			    
		   			 	sumNetInvoicedAmount=sumNetInvoicedAmount+Double.parseDouble(changeValueToBigDecimalFromFloat(netInvoicedAmount).toString());
		   				
		   				
		   			}
					
					HSSFRow totalRowsheet=sheet.createRow(invoiceList.size()+1);	
		   			totalRowsheet.createCell(0).setCellValue("Total");
		   			HSSFCell celltotal3= totalRowsheet.createCell(3);
	   				HSSFCell celltotal4= totalRowsheet.createCell(4);
		   			
	   				celltotal3.setCellValue(sumInvoiceAmount);
	   				celltotal3.setCellStyle(floatFormat);
	   				celltotal4.setCellValue(sumNetInvoicedAmount);
					changeColumnStyle(sheet,5);
					output = response.getOutputStream();
				
			        response.reset();  
			        response.setHeader("Content-disposition", "attachment; filename="+filename);  
			        response.setContentType("application/msexcel");          
			        wkb.write(output);  
			        output.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LOGGER.error("error export invoice list ", e);
				}  
    	
    }

	@RequestMapping(value = "/export/{billterm}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void export(HttpServletRequest request,HttpServletResponse response, @PathVariable String billterm) {
		try {
			AccountPeriod accountPeriod=new AccountPeriod(billterm);
			String strAccountPeriod=accountPeriod.previousPeriod().toString();
			Date billtermDatePSTNThisPeriod=DateUtil.toInvoiceDate(strAccountPeriod+"01");
			String strBilltermDatePSTNThisPeriod=DateUtil.formatShortInvoiceDate(billtermDatePSTNThisPeriod);
			InputStream excelFileInputStream=BillExportController.class.getResourceAsStream("/billingtemplate/template.xls");
			//FileInputStream excelFileInputStream = new FileInputStream("F:/template.xls");
			HSSFWorkbook wkb = new HSSFWorkbook(excelFileInputStream);
			// 输入流使用后，及时关闭！这是文件流操作中极好的一个习惯！
			excelFileInputStream.close();
		     //HSSFWorkbook wkb = new HSSFWorkbook();  
		      Date billtermDate=DateUtil.toShortNoDayDate(billterm);
		      String strBillTerm =DateUtil.formatEnDate2(billtermDate);
		      CreationHelper createHelper = wkb.getCreationHelper();
		      CellStyle floatFormat = wkb.createCellStyle();
		      floatFormat.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		      
			  List<BillExport> biggestDatebillExportsList = billService.getBiggestDateBillForExport();
			  List<BillExport> samllestDatebillExportsList = billService.getSamllestDateForExport();
			  BillExport biggestDatebillExports = biggestDatebillExportsList.get(0);
			  BillExport samllestDatebillExports = samllestDatebillExportsList.get(0);
			  String strBiggestDatebillExports=biggestDatebillExports.getBiggestdate().substring(0,7)+"/01";
			  String strSamllestDatebillExports=samllestDatebillExports.getSmallestdate().substring(0,7)+"-01";
			  strSamllestDatebillExports="2015-09-01";
			  Date biggestDate=DateUtil.toShortInvoiceDate(strBiggestDatebillExports);
			  Date smallestDate=DateUtil.toShortDate(strSamllestDatebillExports);
			Calendar smallest = Calendar.getInstance();
			Calendar min = Calendar.getInstance();
			Calendar max = Calendar.getInstance();
			smallest.setTime(smallestDate);
			min.setTime(smallestDate);
            max.setTime(biggestDate);
			  //建立新的sheet对象（excel的表单）  
		    //HSSFSheet sheet=wkb.createSheet("Billing Report");   
		    HSSFSheet sheet = wkb.getSheetAt(0);
			HSSFRow rowhead1sheet0=sheet.createRow(0);  
			rowhead1sheet0.createCell(0).setCellValue("Billing Report - "+billterm); 
		    HSSFRow rowhead2sheet0=sheet.createRow(1);  
		    rowhead2sheet0.createCell(0).setCellValue("(All in RMB)");  
		    HSSFRow rowhead3sheet0=sheet.createRow(2);  
		    rowhead3sheet0.createCell(0).setCellValue("Part I. Invoice . Credit Note . Credit Memo");
		    
		    HSSFRow row1=sheet.createRow(3);  
		    createHead(row1);
		    
		    	
		
			    HSSFRow row2sheet0=sheet.createRow(4);  
			    row2sheet0.createCell(13).setCellValue("A");
			    row2sheet0.createCell(14).setCellValue("B");
			    row2sheet0.createCell(17).setCellValue("C");
			    row2sheet0.createCell(18).setCellValue("D=A+B+C");
			double sumNetInvoicedAmount =0;
			double  sumInvoiceAmount=0;
		    List<BillExport> billExports = billService.listAllBillForExport(billterm);
   			for (int i = 0, n = billExports.size(); i <n;i++){
   				BillExport	billExport=billExports.get(i);
   				HSSFRow row3=sheet.createRow(i+5);
   				float netInvoicedAmount=0;
   				float invoicedAmount=0;
   				if(false&&billExport.getBeforeTaxValue()!=0){
   					netInvoicedAmount=billExport.getBeforeTaxValue()/Constants.TAXRATE;
   					if(billExport.getInvoicedAmount()!=0){   					
   						invoicedAmount=netInvoicedAmount;
   					}
   				}else{
   					netInvoicedAmount=billExport.getNetInvoicedAmount();
   					invoicedAmount=billExport.getInvoicedAmount();
   				}
   				createRowData(row3, billExport,floatFormat,strBilltermDatePSTNThisPeriod);
   				sumNetInvoicedAmount=sumNetInvoicedAmount+Double.parseDouble(changeValueFromFloat(netInvoicedAmount));
   				sumInvoiceAmount=sumInvoiceAmount+Double.parseDouble(changeValueFromFloat(invoicedAmount));
   			}
   			HSSFRow totalRowsheet=sheet.createRow(billExports.size()+5);	
   			totalRowsheet.createCell(0).setCellValue("Total");
   			totalRowsheet.createCell(15).setCellValue(sumInvoiceAmount);
   			totalRowsheet.createCell(18).setCellValue(sumNetInvoicedAmount);
   			
   		 //HSSFSheet sheet1=wkb.createSheet("Rev waterfall");  
   		 HSSFSheet sheet1 = wkb.getSheetAt(1);
		    //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个  
		    HSSFRow rowhead1sheet1=sheet1.createRow(0);  
		    rowhead1sheet1.createCell(0).setCellValue("Revenue Recognition Report - "+strBillTerm); 
		    HSSFRow rowhead2sheet1=sheet1.createRow(1);  
		    rowhead2sheet1.createCell(0).setCellValue("(All in RMB1)");  
		    HSSFRow rowhead3sheet1=sheet1.createRow(2);  
		    rowhead3sheet1.createCell(0).setCellValue("Part I. Invoice . Credit Note . Credit Memo");
		    rowhead3sheet1.createCell(20).setCellValue("Part II. Revenue Recognition Schedule");
		    HSSFRow rowhead4=sheet1.createRow(3);
		    HSSFRow rowhead5=sheet1.createRow(4); 
		    int j=21;
		    Calendar curr = min;
			while (curr.before(max)||curr.equals(max)) {
				Date currentDate=curr.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM ",  
		                Locale.ENGLISH);  
				String strCurrentDate=sdf.format(currentDate);
				String curYear=strCurrentDate.substring(0,4);
				String curMonth=strCurrentDate.substring(5,8);
				rowhead4.createCell(j).setCellValue(curYear);
				rowhead5.createCell(j).setCellValue(curMonth);
				curr.add(Calendar.MONTH, 1);
				j=j+1;
			}
			int columnnumber=j-21;
			double[] totoalArray =new double[columnnumber];
			totoalArray[0]=0;         totoalArray[1]=1075.72;      totoalArray[2]=13418.24;    totoalArray[3]=36883.22;
			totoalArray[4]=45917.61;  totoalArray[5]=57344.62;     totoalArray[6]=85342.21;    totoalArray[7]=100739.75;
			totoalArray[8]=154144.48; totoalArray[9]=171864.32;    totoalArray[10]=185380.76;  totoalArray[11]=214085.02;
			totoalArray[12]=306527.47;totoalArray[13]= 	226123.63 ;totoalArray[14]=	215571.60 ;totoalArray[15]=	198845.99;
			totoalArray[16]=187255.40;totoalArray[17] =	176148.98 ;totoalArray[18]=	164315.18 ;totoalArray[19]=	146888.98;
			totoalArray[20]=136067.68;totoalArray[21]= 	111419.62 ;totoalArray[22]=	98619.79;  totoalArray[23]=	82244.60;
			totoalArray[24]=34047.63; totoalArray[25]= 	15837.37 ; totoalArray[26]=	13196.82 ; totoalArray[27]=	11983.40; 	
			totoalArray[28]=10885.47; totoalArray[29]=	10510.79 ; totoalArray[30]=7839.19;    totoalArray[31]=	5612.81; 
			totoalArray[32]=5612.84 ; totoalArray[33]=	5201.65 ;  totoalArray[34]=	5201.60;   totoalArray[35]=	4977.61;
			totoalArray[36]=4555.88;  totoalArray[37]=	4218.61 ;  totoalArray[38]=	2632.10;   totoalArray[39]=1940.60 ;
			totoalArray[40]=1940.60;  totoalArray[41]= 	1940.60;   totoalArray[42]=1940.30;    totoalArray[43]=223.95;
			totoalArray[44]=223.95;   totoalArray[45]=223.95;      totoalArray[46]=	223.97 ;

			

			 createHead(rowhead4);
			 
			
			 rowhead5.createCell(13).setCellValue("A");
			 rowhead5.createCell(14).setCellValue("B");
			 rowhead5.createCell(17).setCellValue("C");
			 rowhead5.createCell(18).setCellValue("D=A+B+C");
			 rowhead5.createCell(20).setCellValue("T1 MRR");
			 
			 List<BillExport> billExportsAll = billService.getALLBills(billterm+"01");
			 int i = 0;
			 double totalMountFor20=571154.42;
			 double  sumNetInvoicedAmountWaterFall =0;
			 double  sumInvoiceAmountWaterFall=0;
	   			for (int n = billExportsAll.size(); i <n;i++){
	   				BillExport	billExport=billExportsAll.get(i);
	   				HSSFRow row3=sheet1.createRow(i+832);
	   				float netInvoicedAmount=0;
	   				float invoicedAmount=0;
	   				if(billExport.getBeforeTaxValue()!=0){
	   					netInvoicedAmount=billExport.getBeforeTaxValue()/Constants.TAXRATE;
	   					if(billExport.getInvoicedAmount()!=0){   					
	   						invoicedAmount=netInvoicedAmount;
	   					}
	   				}else{
	   					netInvoicedAmount=billExport.getNetInvoicedAmount();
	   					invoicedAmount=billExport.getInvoicedAmount();
	   				}
	   				createRowData(row3, billExport,floatFormat,strBilltermDatePSTNThisPeriod);
		   			 if(billExport.getInvoiceName().equals("KT04695-20171001-2")){
		   				 int aa=0;
		   			 }
	   				sumNetInvoicedAmountWaterFall=sumNetInvoicedAmountWaterFall+Double.parseDouble(changeValueFromFloat(netInvoicedAmount));
	   				sumInvoiceAmountWaterFall=sumInvoiceAmountWaterFall+Double.parseDouble(changeValueFromFloat(invoicedAmount));
	   				int payInterval=billExport.getPaymentInterval();
	   				int contractTerm=billExport.getContractTerm();
	   				String invoiceName=billExport.getInvoiceName();	   				
	   				String bill  = invoiceName.substring(invoiceName.indexOf("-")+1,invoiceName.lastIndexOf("-"));
	   				Date contractCommenctDate=null;
	   				String contractCommenctStr="";
	   				Boolean isSameMonth=true;
	   				//System.out.println(billExport.getInvoiceName());
	   				if(billExport.getContractCommence()!=null&&billExport.getContractCommence().indexOf("/")>0){
	   					contractCommenctDate=DateUtil.toShortInvoiceDate(billExport.getContractCommence());
	   					contractCommenctStr=DateUtil.formatInvoiceNameDate(contractCommenctDate);
	   					isSameMonth=bill.substring(0, 6).equals(contractCommenctStr.substring(0, 6));
   					}else if(billExport.getContractCommence()!=null){
   						contractCommenctDate=DateUtil.toShortDate(billExport.getContractCommence());
   						contractCommenctStr=DateUtil.formatInvoiceNameDate(contractCommenctDate);
   						isSameMonth=bill.substring(0, 6).equals(contractCommenctStr.substring(0, 6));
   					}	 
	   				
   					Date billtermDateForData=DateUtil.toInvoiceDate(bill);
   					Calendar now = Calendar.getInstance();
   					if(billExport.getCreditType()!=null&&billExport.getContractCommence()!=null&&billExport.getCreditType().equals(Constants.CREDITTYPE)){   				
	   					Date billtermDateForDataUpselling=DateUtil.toShortInvoiceDate(billExport.getContractCommence());;
	   					now.setTime(billtermDateForDataUpselling); 						
   						
   					}else{
   						now.setTime(billtermDateForData);
   						
   					}
   					int monthInterval = (now.get(Calendar.YEAR)-smallest.get(Calendar.YEAR))*12+now.get(Calendar.MONTH) - smallest.get(Calendar.MONTH);
	   				
	   				if((payInterval==1&&contractTerm==0)||(payInterval==1&&billExport.getRevenueType().equals(Constants.AUDIO_PACKAGE_FEE))||(payInterval==1&&billExport.getRevenueType().equals(Constants.PSTN_PERSONAL_PACKET))||(payInterval==1&&billExport.getRevenueType().equals(Constants.SITE_CONFIGURATION_FEE))){	   					
	   					totoalArray[monthInterval]=totoalArray[monthInterval]+Double.parseDouble(changeValueFromFloat(netInvoicedAmount));
	   					HSSFCell cell21= row3.createCell(21+monthInterval);
	   					HSSFCell cell20= row3.createCell(20);
	   					
	   					cell21.setCellValue(Double.parseDouble(changeValueFromFloat(netInvoicedAmount)));	   					
	   					
	   					cell20.setCellValue(Double.parseDouble(changeValueFromFloat(netInvoicedAmount)));
	   					cell20.setCellStyle(floatFormat);
	   					cell21.setCellStyle(floatFormat);
	   					totalMountFor20=totalMountFor20+Double.parseDouble(changeValueFromFloat(netInvoicedAmount));
	   				}else if(contractTerm!=0&&!billExport.getRevenueType().equals(Constants.AUDIO_PACKAGE_FEE)&&!billExport.getRevenueType().equals(Constants.SITE_CONFIGURATION_FEE)){
	   					Double monthlyAmount=Double.parseDouble(changeValueFromFloat(netInvoicedAmount*payInterval/contractTerm));
	   					Double origMonthlyAmount=Double.parseDouble(changeValueFromFloat(netInvoicedAmount*payInterval/contractTerm));
	   					Double totalRowValue=Double.parseDouble(changeValueFromFloat(netInvoicedAmount));
	   					Double lastMonthData;
	   					int count=contractTerm/payInterval;
	   					if(billExport.getInvoiceName().equals("KT01379-20170301-5")){
	   						count=10;
	   					}
	   					if(billExport.getInvoiceName().equals("KT01844-20170501-5")||billExport.getInvoiceName().equals("KT01104-20170801-49")){
	   						count=11;
	   					}
	   					if(billExport.getInvoiceName().equals("KT01105-20170601-23")||billExport.getInvoiceName().equals("KT01491-20171001-4-C")||billExport.getInvoiceName().equals("KT01844-20171001-15-C")){
	   						count=10;
	   					}
	   					if(billExport.getInvoiceName().equals("SM40303HB3-20170601-1-C")||billExport.getInvoiceName().equals("KT01491-20170801-30")||billExport.getInvoiceName().equals("KT01844-20170801-17")){
	   						count=10;
	   					}
	   					if(billExport.getInvoiceName().equals("KT01844-20170701-5")){
	   						count=21;
	   					}
	   					if(billExport.getInvoiceName().equals("KT01107-20170801-9")){
	   						count=9;
	   					}
	   					if(billExport.getInvoiceName().equals("KT01379-20170701-6")){
	   						count=8;
	   					}
	   					if(billExport.getInvoiceName().equals("KG80351HB6-20170501-2")){
	   						monthInterval=21;
	   					}
	   					if(billExport.getInvoiceName().equals("KT01108-20170801-16")){
	   						count=5;
	   					}
	   					if(billExport.getInvoiceName().equals("KT01105-20170801-14")){
	   						count=6;
	   					}
	   					if(billExport.getInvoiceName().equals("KT01108-20170801-15")){
	   						count=3;
	   					}
	   					
	   					
	   					for (int l = 0; l <count;l++){
	   							if(l ==0){
	   								HSSFCell cell20= row3.createCell(20);
	   								cell20.setCellValue(monthlyAmount);
	   								totalMountFor20=totalMountFor20+monthlyAmount;
	   								cell20.setCellStyle(floatFormat);
	   							}
	   							   						
	   							
	   							if((billExport.getPaymentInterval()==1&&Integer.parseInt(billExport.getContractCommence().substring(8, 10))>15)||(isSameMonth==true&&(contractTerm/payInterval==1||contractTerm/payInterval==12||contractTerm/payInterval==6||contractTerm/payInterval==3)&&Integer.parseInt(billExport.getContractCommence().substring(8, 10))>15)&&(!Constants.SHLXCODE.equals(billExport.getCustomerId()))){
	   								HSSFCell cell22=row3.createCell(22+monthInterval+l);
	   								if(l==count-1){
	   									lastMonthData=totalRowValue-monthlyAmount*(contractTerm/payInterval-1);
	   									cell22.setCellValue(lastMonthData);
	   									totoalArray[monthInterval+l+1]=totoalArray[monthInterval+l+1]+lastMonthData;
	   								}else{
	   									if(billExport.getInvoiceName().equals("KT01491-20170801-30")&&l==0||billExport.getInvoiceName().equals("KT01844-20170801-17")&&l==0){
	   										monthlyAmount=monthlyAmount*3;
	   									}else if(billExport.getInvoiceName().equals("KT01107-20170801-9")&&l==0){
	   										monthlyAmount=monthlyAmount*4;
	   									}else if(billExport.getInvoiceName().equals("KT01104-20170801-49")&&l==0){
	   										monthlyAmount=monthlyAmount*2;
	   									}else if(billExport.getInvoiceName().equals("KT01108-20170801-16")&&l==0||billExport.getInvoiceName().equals("KT01105-20170801-14")&&l==0||billExport.getInvoiceName().equals("KT01108-20170801-15")&&l==0){
	   										monthlyAmount=monthlyAmount*5;
	   									}else{
	   										monthlyAmount=origMonthlyAmount;
	   									}
	   									cell22.setCellValue(monthlyAmount);
	   									totoalArray[monthInterval+l+1]=totoalArray[monthInterval+l+1]+monthlyAmount;
	   								}
	   								cell22.setCellStyle(floatFormat);
	   								
	   							}else{
	   								HSSFCell cell21=row3.createCell(21+monthInterval+l);
	   								if(l==count-1){
	   									lastMonthData=totalRowValue-monthlyAmount*(contractTerm/payInterval-1);	   								
	   									cell21.setCellValue(lastMonthData);
	   									totoalArray[monthInterval+l]=totoalArray[monthInterval+l]+lastMonthData;
	   								}else{
	   									if(billExport.getInvoiceName().equals("KT01379-20170301-5")&&l==0||billExport.getInvoiceName().equals("KT01379-20170701-6")&&l==0||billExport.getInvoiceName().equals("KT01844-20171001-15-C")&&l==0){
	   										monthlyAmount=monthlyAmount*3;
	   									}else if(billExport.getInvoiceName().equals("KT01844-20170701-5")&&l==0){
	   										monthlyAmount=monthlyAmount*4;
	   									}else if(billExport.getInvoiceName().equals("KT01844-20170501-5")&&l==0){
	   										monthlyAmount=monthlyAmount*2;
	   									}else if(billExport.getInvoiceName().equals("KT01105-20170601-23")&&l==0||billExport.getInvoiceName().equals("SM40303HB3-20170601-1-C")&&l==0||billExport.getInvoiceName().equals("KT01491-20171001-4-C")&&l==0){
	   										monthlyAmount=monthlyAmount*3;
	   									}else{
	   										monthlyAmount=origMonthlyAmount;
	   									}
	   									cell21.setCellValue(monthlyAmount);
	   									totoalArray[monthInterval+l]=totoalArray[monthInterval+l]+monthlyAmount;
	   								}
	   								cell21.setCellStyle(floatFormat);
	   								
	   							}
	   		   					

	   						
	   					}
	   					
	   					
	   					
	   				}
	   		
	   			}
	   		HSSFRow totalRowsheet1=sheet1.createRow(billExportsAll.size()+832);	
	   		totalRowsheet1.createCell(0).setCellValue("Total");
	   		HSSFCell cell20=totalRowsheet1.createCell(20);
	   		HSSFCell cell15=totalRowsheet1.createCell(15);
	   		HSSFCell cell18=totalRowsheet1.createCell(18);
	   		cell20.setCellValue(totalMountFor20);
	   		cell15.setCellValue(sumInvoiceAmountWaterFall+3309609.16);
	   		cell18.setCellValue(sumNetInvoicedAmountWaterFall+3267196.56);
	   		
	   		cell20.setCellStyle(floatFormat);
	   		cell15.setCellStyle(floatFormat);
	   		cell18.setCellStyle(floatFormat);
	   		for(int m=0;m<columnnumber;m++){
	   			totalRowsheet1.createCell(21+m).setCellValue(totoalArray[m]);
	   		}
	   		
	   		changeColumnStyle(sheet,18); 
	   		changeColumnStyle(sheet1,20);
		    //在sheet里创建第二行  
		    
		     String filename="Billing Report "+billterm+".xls";
		      
		    //输出Excel文件  
		        OutputStream output=response.getOutputStream();  
		        response.reset();  
		        response.setHeader("Content-disposition", "attachment; filename="+filename);  
		        response.setContentType("application/msexcel");          
		        wkb.write(output);  
		        output.close(); 
			
			
		} catch (Exception e) {
			LOGGER.error("error export excel bill ", e);
			
		}
	}
	
	
	 @RequestMapping(value = "/exportpstnexcel/{customerId}/{accountperiod}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
	   	@ResponseStatus(HttpStatus.NO_CONTENT)
	   	public  void exportpstnexcel(HttpServletResponse response,@PathVariable String customerId,@PathVariable String accountperiod) {
	   		try {
	   			 			
	   			AccountPeriod accountPeriod=new AccountPeriod(accountperiod);
	   			int accountPeriodInt=accountPeriod.previousPeriod().toInt();
	   			String filename=customerId+" - pstn detail - "+accountPeriodInt+".xlsx";
	   			response.setHeader("Content-disposition", "attachment; filename="+filename);  
		        response.setContentType("application/msexcel"); 
		        response.setCharacterEncoding("UTF-8");
	   			OutputStream output=response.getOutputStream(); 
	   	       
	   			billingService.exportPstnFeeAsExcel(customerId,accountPeriodInt,output);		         
			  

	   			//response.reset();  
		           
		       
		        output.close(); 
	  
	 			 

	   			
	   			//billService.charge(bean.getCustomerId(), new AccountPeriod(bean.getAccountPeriod()), FeeType.valueOf(bean.getFeeType()));
	   		} catch (Exception e) {
	   			LOGGER.error("error export pdf bill ", e);
	   		}
	   		
	   		

	   	}
	 
	 
	 private  void  createHead(HSSFRow row1){
		 	row1.createCell(0).setCellValue("Document # (Invoice、Credit Note、Credit Memo)");  
		 	row1.createCell(1).setCellValue("Document Type");      
		 	row1.createCell(2).setCellValue("Document Date");
		 	row1.createCell(3).setCellValue("Corresponding Invoice # for Credit Notes、Credit Memos");
		 	row1.createCell(4).setCellValue("Customer / Reseller ID (Note 1)");
		 	row1.createCell(5).setCellValue("Sales Channel");
		 	row1.createCell(6).setCellValue("Agent ID");		
		 	row1.createCell(7).setCellValue("Contract Commence");
		 	row1.createCell(8).setCellValue("Contract Term");
		 	row1.createCell(9).setCellValue("Payment Interval");
		 	row1.createCell(10).setCellValue("Revenue Type (Note 2)");
		 	row1.createCell(11).setCellValue("Product Desc");
		 	row1.createCell(12).setCellValue("Unit");
		 	row1.createCell(13).setCellValue("Listed Price");
		 	row1.createCell(14).setCellValue("Discount");
		 	row1.createCell(15).setCellValue("Invoiced Amount (excl. VAT and Surcharge)");
		 	row1.createCell(16).setCellValue("Credit Type (Note 3)");
		 	row1.createCell(17).setCellValue("Credit Amount");
		 	row1.createCell(18).setCellValue("Net Invoiced Amount");
		 
		 
	 }
	 
	 
	 private  void  createRowData(HSSFRow row3,BillExport billExport,CellStyle floatFormat,String strBilltermDatePSTNThisPeriod){
		 float netInvoicedAmount=0;
		 float invoicedAmount=0;
		 float creditAmount=0;
		 float listPrice=0;
		 
		 if(billExport.getInvoiceName().equals("KT00636-20170201-2")){
			 int aa=0;
		 }
		 if(false&&billExport.getBeforeTaxValue()!=0){
				netInvoicedAmount=billExport.getBeforeTaxValue()/Constants.TAXRATE;
				if(billExport.getInvoicedAmount()!=0){   					
					invoicedAmount=netInvoicedAmount;
				}
				if(billExport.getCreditAmount()!=0){   					
					creditAmount=netInvoicedAmount;
				}
				if(billExport.getListedPrice()!=0){   					
					listPrice=netInvoicedAmount;
				}
			}else{
				netInvoicedAmount=billExport.getNetInvoicedAmount();
				invoicedAmount=billExport.getInvoicedAmount();
				listPrice=billExport.getListedPrice();
			}
		 
		 row3.createCell(0).setCellValue(billExport.getInvoiceName());  
		 	row3.createCell(1).setCellValue(billExport.getDocumentType());      
		 	row3.createCell(2).setCellValue(billExport.getDocumentDate());
		 	row3.createCell(3).setCellValue(billExport.getInvoiceMemo());      
		 	row3.createCell(4).setCellValue(billExport.getCustomerId());
		 	if(billExport.getCustomerId().equals("SM40600HB8")||billExport.getCustomerId().equals("SM40125HN4")){
		 		row3.createCell(5).setCellValue("Reseller"); 
		 	}else{
		 		row3.createCell(5).setCellValue(billExport.getSalesChannel()); 
		 	}
		 	//row3.createCell(6).setCellValue(billExport.getContractTerm());
		 	if(billExport.getRevenueType().equals(Constants.AUDIO_USAGE_OR_OVERAGE)||billExport.getRevenueType().equals(Constants.NETWORK_USAGE_OR_OVERAGE)){
		 		row3.createCell(7).setCellValue(strBilltermDatePSTNThisPeriod);
		 	}else{
		 		row3.createCell(7).setCellValue(billExport.getContractCommence());
		 	}
		 	row3.createCell(8).setCellValue(billExport.getContractTerm());
			row3.createCell(9).setCellValue(billExport.getPaymentInterval());
			row3.createCell(10).setCellValue(billExport.getRevenueType());
			row3.createCell(11).setCellValue(billExport.getProductDesc());
			row3.createCell(12).setCellValue(billExport.getUnit());
			
			HSSFCell cell13= row3.createCell(13);
			cell13.setCellValue(Double.parseDouble(changeValueFromFloat(listPrice)));
			HSSFCell cell14= row3.createCell(14);
			cell14.setCellValue(0);
			
			HSSFCell cell15= row3.createCell(15);
			cell15.setCellValue(Double.parseDouble(changeValueFromFloat(invoicedAmount)));
			row3.createCell(16).setCellValue(billExport.getCreditType());
			//row3.createCell(16).setCellValue("0");
			HSSFCell cell17= row3.createCell(17);
			cell17.setCellValue(Double.parseDouble(changeValueFromFloat(creditAmount)));
			HSSFCell cell18= row3.createCell(18);
			
			cell18.setCellValue(Double.parseDouble(changeValueFromFloat(netInvoicedAmount)));
			//row3.createCell(19).setCellValue(billExport.getOrderId());
			cell13.setCellStyle(floatFormat);
			cell14.setCellStyle(floatFormat);
			cell15.setCellStyle(floatFormat);
			cell17.setCellStyle(floatFormat);
			cell18.setCellStyle(floatFormat);
		 
	 }
	 
	 private String changeValueFromFloat(float data){
		 BigDecimal totalAmount = new BigDecimal(String.valueOf(data));
			//totalAmount = totalAmount.add(new BigDecimal(data));
			return totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	 }
	 
	 private BigDecimal changeValueToBigDecimalFromFloat(float data){
		 BigDecimal totalAmount = new BigDecimal(0);
			totalAmount = totalAmount.add(new BigDecimal(data));
			return totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
	 }
	 
	 private void changeColumnStyle(HSSFSheet sheet,int columnSize){
		 for(int i=0;i<=columnSize;i++){
			 sheet.autoSizeColumn(i); 
		 }		 
		   
	 }
	 
	 private BillExport  getBillExport(String name){
		 BillExport returnDate=null;
		 List<BillExport>  billExportDetailList=billService.getOneInvoiceByName(name);
		 int i=0;
		 for (int n = billExportDetailList.size(); i <n;i++){
			 BillExport	billExport=billExportDetailList.get(i);
			 if(!billExport.getRevenueType().equals(Constants.SITE_CONFIGURATION_FEE)){
				 returnDate=billExport;
			 }
			 if(i==n-1&&returnDate==null){
					returnDate=billExport;
			 }
			 
			 
		 }
		 return returnDate;
	 }
}
