package com.kt.api.controller;

import com.kt.api.bean.bill.*;
import com.kt.api.common.APIConstants;
import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.bean.FormalBillDetailBean;
import com.kt.biz.bean.MonthlyBillBean;
import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.billing.BillFormalDetail;
import com.kt.exception.CustomerNotFoundException;
import com.kt.exception.InvalidAccountPeriodFormatException;
import com.kt.exception.WafException;
import com.kt.repo.mysql.billing.BillFormalDetailRepository;
import com.kt.service.SearchFilter;
import com.kt.service.billing.BillService;
import com.kt.util.MathUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Vega Zhou on 2016/5/4.
 */
@Controller
@RequestMapping("/fbill")
public class BillController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(BillController.class);

    @Autowired
    BillService billService;
	@Autowired
	BillFormalDetailRepository billFormalDetailRepository;

    @RequestMapping(value = "/tune/{id}", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void tune(@Valid @RequestBody TuneFormalBillBean bean, @PathVariable String id, HttpServletRequest request) {
        try {
            billService.tuneBill(bean.getId(), new BigDecimal(bean.getAmount()), bean.getComments());
        } catch (WafException e) {
            LOGGER.error("error occurred while tuning formal bill " + id, e);
            throw new ApiException(e.getKey());
        }
    }


	@RequestMapping(value = "/confirm", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void tempBillConfirm(@Valid @RequestBody TempBillConfirmRequest bean, HttpServletRequest request) {
		try {
			List<TempBillConfirmBean> confirmations = bean.getConfirmations();
			List<BillService.TempBillConfirmForm> forms = new LinkedList<BillService.TempBillConfirmForm>();
			for (TempBillConfirmBean confirmation : confirmations) {
				BillService.TempBillConfirmForm form = new BillService.TempBillConfirmForm();
				form.setCustomerId(confirmation.getCustomerId());
				try {
					form.setAccountPeriod(new AccountPeriod(confirmation.getAccountPeriod()));
				} catch (ParseException e) {
					throw new InvalidAccountPeriodFormatException(e);
				}
				forms.add(form);
			}
			billService.confirmBill(forms);
		} catch (WafException e) {
			LOGGER.error("error occurred while confirming temp bill ", e);
			throw new ApiException(e.getKey());
		}
	}


	@RequestMapping(value = "/recalc", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void recalc(@Valid @RequestBody TempBillRecalcForm bean, HttpServletRequest request) {
		try {
			try {
				billService.recalculateBill(bean.getCustomerIds().get(0), new AccountPeriod(bean.getAccountPeriod()));
			} catch (ParseException e) {
				throw new InvalidAccountPeriodFormatException(e);
			}
		} catch (WafException e) {
			LOGGER.error("error occurred while recalculating temp bill -- customerId:" +
					bean.getCustomerIds().get(0) +
					" accountPeriod:" + bean.getAccountPeriod(), e);
			throw new ApiException(e.getKey());
		}
	}


	@RequestMapping(value = "/recalcall", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void recalcAll(@Valid @RequestBody TempBillRecalcForm bean, HttpServletRequest request) {
		try {
			try {
				billService.recalculateAll(new AccountPeriod(bean.getAccountPeriod()));
			} catch (ParseException e) {
				throw new InvalidAccountPeriodFormatException(e);
			}
		} catch (WafException e) {
			LOGGER.error("error occurred while recalculating temp bill -- customerId:" +
					bean.getCustomerIds().get(0) +
					" accountPeriod:" + bean.getAccountPeriod(), e);
			throw new ApiException(e.getKey());
		}
	}





	@RequestMapping(value = "/charge/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> charge(@PathVariable String id) {
		try {
			billService.charge(Long.valueOf(id));
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} catch (WafException e) {
			throw new ApiException(e.getKey());
		}
	}


	@RequestMapping(value = "/charge", method = RequestMethod.POST, produces = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> charge(@Valid @RequestBody ChargeForm bean) {
		try {
			billService.charge(bean.getCustomerId(), new AccountPeriod(bean.getAccountPeriod()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (WafException e) {
			throw new ApiException(e.getKey());
		}
	}


	@RequestMapping(value = "/charge2", method = RequestMethod.POST, produces = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> charge2(@Valid @RequestBody ChargeForm2 bean) {
		List<Long> billSequences = bean.getBillSequences();
		try {
			billService.charge(billSequences.toArray(new Long[billSequences.size()]));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (WafException e) {
			throw new ApiException(e.getKey());
		}
	}

    
    @RequestMapping(value = "/query")
   	@ResponseBody
   	public String list(HttpServletRequest request) throws SQLException {
   		DataTableVo dt = this.parseData4DT(request);
   		String customerName = request.getParameter("s_sserviceid1");

   		String accountPeriod = request.getParameter("s_searchStartTime_STANDSITE");
   		String sortedColumnId = request.getParameter("iSortCol_0");
   		String sortedOrder = request.getParameter("sSortDir_0");
   		int curpage = 0;
   		if (dt.getiDisplayStart() > 0) {
   			curpage = dt.getiDisplayStart() / dt.getiDisplayLength();
   		}
   		// logger.debug("list systemuser =" + saccount + "; sphone=" + sphone +
   		// "; sname=" + sname); // "aphone=" + aphone + "; aacount=" + aacount
   		// );

   		SearchFilter searchorg = new SearchFilter();
   		curpage = curpage + 1;
   		if (StringUtils.isNotBlank(customerName)) {
   			searchorg.setCustomerName(customerName);
   		}
   	
   		if (StringUtils.isNotBlank(accountPeriod)) {
   			searchorg.setAccountPeriod(accountPeriod);
   		}

   		String[] supportedOrderFields = new String[] {"COMPANY", null, null, "AMOUNT", "UNPAID_AMOUNT", "CONFIRMED"};

   		if (sortedColumnId != null && StringUtils.isNumeric(sortedColumnId)) {
   			int orderIndex = Integer.parseInt(sortedColumnId);

   			if (orderIndex < supportedOrderFields.length) {
   				String orderFieldName = supportedOrderFields[orderIndex];
   				if (orderFieldName != null) {
   					searchorg.setOrderByField(orderFieldName);
   					if (sortedOrder != null
   							&& sortedOrder.toLowerCase().equals(ORDER_DESC)) {
   						searchorg.setOrderType(ORDER_DESC);
   					} else {
						searchorg.setOrderType(ORDER_ASC);
					}
   				}
   			}
   		}

   		searchorg.setPageSize(dt.getiDisplayLength());
   		try {
   			handleDataTable(dt, curpage, searchorg);
   		} catch (Exception e) {
   			LOGGER.error("list orgs failed", e);
   			return FAIL;
   		}
   		// dt.setsEcho(String.valueOf(curpage+1));
   		String json = this.formateData2DT(dt);
   		return json;

   	}

	public void handleDataTable(DataTableVo dt, int curpage,
								SearchFilter filter) throws Exception {
		Page<MonthlyBillBean> page;
		List<MonthlyBillBean> bills;
		int totalSize;
		try {
			String accountPeriod = filter.getAccountPeriod();
			if (StringUtils.isNotBlank(accountPeriod)) {
				page = billService.paginateMonthlyBillByAccountPeriod(new AccountPeriod(accountPeriod), curpage, filter);
			} else {
				page = billService.paginateBillByCustomerName(filter.getCustomerName(), curpage, filter);
			}
			bills = page.getData();
			totalSize = page.getRecordCount();
		}  catch (Exception e) {
			// logger.error("list Exception:"+e);
			throw new Exception("Error in list orgs!", e);
		}

		if (bills != null) {
			List<TableRow> tds = new ArrayList<TableRow>();
			for (int i = 0, n = bills.size(); i < dt.getiDisplayLength()
					&& i < n; i++) {
				MonthlyBillBean bill = bills.get(i);

				TableRow td = new TableRow();

				if (bill.getUnpaidAmount() != null) {
					td.setUnpaidAmount(new BigDecimal(bill.getUnpaidAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				} else {
					td.setUnpaidAmount(new BigDecimal(0).toString());
				}

				if (bill.getAmount() != null) {
					td.setAmount(new BigDecimal(bill.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				} else {
					td.setAmount(new BigDecimal(0).toString());
				}
				if (StringUtils.isNotBlank(bill.getAccountPeriod())) {
					td.setAccountPeriod(bill.getAccountPeriod());
				} else {
					td.setAccountPeriod(filter.getAccountPeriod());
				}
				td.setConfirmed(bill.getConfirmed() == 1);
				td.setCustomerName(bill.getCompany());

				td.setSap(bill.getItemCount() > bill.getSyncedCount() ? 0 : 1);
				td.setCompanyId(bill.getCustomerId());
				td.setPdfFailTasks(bill.getFailedTasks());
				td.setPdfStatus(bill.getPdfStatus());
				td.setCustomerCode(bill.getCustomerCode());
				tds.add(td);
			}
			dt.setData(tds);
			dt.setiTotalRecords(totalSize);

			// logger.debug("completed table Handling");
		}
	}


	private static final class TableRow {
		private String customerName;

		private String customerCode;
		
		private String companyId;

		private String accountPeriod;

		private String amount;

		private String unpaidAmount;

		private int sap;

		private int pdfFailTasks;

		private String pdfStatus;

		private boolean confirmed;
		
		private String amountType;

		private int rowSpan;

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getAccountPeriod() {
			return accountPeriod;
		}

		public void setAccountPeriod(String accountPeriod) {
			this.accountPeriod = accountPeriod;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getUnpaidAmount() {
			return unpaidAmount;
		}

		public void setUnpaidAmount(String unpaidAmount) {
			this.unpaidAmount = unpaidAmount;
		}

		public int getSap() {
			return sap;
		}

		public void setSap(int sap) {
			this.sap = sap;
		}

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

		public int getPdfFailTasks() {
			return pdfFailTasks;
		}

		public void setPdfFailTasks(int pdfFailTasks) {
			this.pdfFailTasks = pdfFailTasks;
		}

		public String getPdfStatus() {
			return pdfStatus;
		}

		public void setPdfStatus(String pdfStatus) {
			this.pdfStatus = pdfStatus;
		}

		public boolean isConfirmed() {
			return confirmed;
		}

		public void setConfirmed(boolean confirmed) {
			this.confirmed = confirmed;
		}

		public String getAmountType() {
			return amountType;
		}

		public void setAmountType(String amountType) {
			this.amountType = amountType;
		}

		public String getCustomerCode() {
			return customerCode;
		}

		public void setCustomerCode(String customerCode) {
			this.customerCode = customerCode;
		}

		public int getRowSpan() {
			return rowSpan;
		}

		public void setRowSpan(int rowSpan) {
			this.rowSpan = rowSpan;
		}
	}


	@RequestMapping(value = "/billsgbat", method = RequestMethod.POST, produces = APIConstants.MEDIATYPE)
	@ResponseBody
	public String listBillsGroupByAccountType(@Valid @RequestBody CustomerNameAndAccountPeriod bean) throws ParseException {
		String customerName = bean.getCustomerName();
		Map<String, List<BillFormalDetail>> results = new HashMap<String, List<BillFormalDetail>>();
		List<BillFormalDetail> bills = billService.listBillsByAccountType(customerName);
		if (bills.size() >= 20) {
			throw new ApiException("too_many_records_found");
		}
		for (BillFormalDetail bill : bills) {
			String key = bill.getCustomerId();
			List<BillFormalDetail> list = results.get(key);
			if (list == null) {
				list = new LinkedList<BillFormalDetail>();
				results.put(key, list);
			}
			list.add(bill);
		}
		return formateData2DT(results);
	}
	
	
	@RequestMapping(value = "/billsgbattable")
	@ResponseBody
	public String listBillsGroupByAccountType(HttpServletRequest request) throws ParseException {
		DataTableVo dt = this.parseData4DT(request);
		String customerName = (String) request.getParameter("s_sserviceid1");
		if(customerName.equals("")){
			customerName="serachcustomerhardcode";
		}
   		String accountPeriod = (String) request.getParameter("s_searchStartTime_STANDSITE");

		List<BillFormalDetail> bills = billService.listBillsByAccountType(customerName);

		TableRow cursor = null;
		String tempCode = null;
		String tempAccountPeriod = null;
		int counter = 1;
		List<TableRow> tds = new ArrayList<TableRow>();
		for (BillFormalDetail bill : bills) {
			TableRow td = new TableRow();
			td.setCompanyId(bill.getCustomerId());
			td.setCustomerName(bill.getCustomerName());
			td.setCustomerCode(bill.getCustomerCode());
			td.setAmountType(bill.getAccountType());
			String ap = StringUtils.isNotBlank(bill.getAccountPeriod()) ? bill.getAccountPeriod() : accountPeriod;
			td.setAccountPeriod(ap);
			if (cursor == null) {
				cursor = td;
			}

			if (StringUtils.equalsIgnoreCase(tempCode, bill.getCustomerCode()) && StringUtils.equalsIgnoreCase(tempAccountPeriod, ap)) {
				counter++;
			} else {
				cursor.setRowSpan(counter);
				counter = 1;
				tempCode = bill.getCustomerCode();
				tempAccountPeriod = ap;
				cursor = td;
			}
			cursor.setRowSpan(counter);

			if (bill.getUnpaidAmount() != null) {
				td.setUnpaidAmount(new BigDecimal(bill.getUnpaidAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			} else {
				td.setUnpaidAmount(new BigDecimal(0).toString());
			}

			if (bill.getAmount() != null) {
				td.setAmount(new BigDecimal(bill.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			} else {
				td.setAmount(new BigDecimal(0).toString());
			}


			tds.add(td);			
		}
		dt.setData(tds);
		dt.setiTotalRecords(bills.size());
		return formateData2DT(dt);
	}
	
	
	@RequestMapping(value = "/querydetail")
    @ResponseBody
    public String listdetail(HttpServletRequest request) throws SQLException {
        DataTableVo dt = this.parseData4DT(request);
        String customerId = (String) request.getParameter("s_sserviceid3");
        String accountPeriod = (String) request.getParameter("s_searchStartTime_STANDSITE3");


        SearchFilter searchorg = new SearchFilter();

        if (StringUtils.isNotBlank(customerId)) {
            searchorg.setCustomerId(customerId);
        }

        if (StringUtils.isNotBlank(accountPeriod)) {
            searchorg.setAccountPeriod(accountPeriod);
        }
        try {
        	handleDataTableDetail(dt, customerId, accountPeriod, searchorg);
        } catch (Exception e) {
            LOGGER.error("list orgs failed", e);
            return FAIL;
        }
        // dt.setsEcho(String.valueOf(curpage+1));
        String json = this.formateData2DT(dt);
        return json;

    }


	@RequestMapping(value = "/pagidetail")
	@ResponseBody
	public String paginateBillDetails(HttpServletRequest request) {
		try {
			DataTableVo dt = this.parseData4DT(request);
			String customerName = request.getParameter("s_sserviceid3");
			String accountPeriodString = request.getParameter("s_searchStartTime_STANDSITE3");
			AccountPeriod accountPeriod = null;

			SearchFilter searchorg = new SearchFilter();

			if (!StringUtils.isNotBlank(customerName)) {
				throw new CustomerNotFoundException();
			}

			if (StringUtils.isNotBlank(accountPeriodString)) {
				accountPeriod = new AccountPeriod(accountPeriodString);
			}

			int curpage = 0;
			if (dt.getiDisplayStart() > 0) {
				curpage = dt.getiDisplayStart() / dt.getiDisplayLength();
			}
			curpage = curpage + 1;

			try {
				Page<FormalBillDetailBean> bills;
				try {
					bills = billService.paginateBillFormalDetail(customerName, accountPeriod, curpage , searchorg);
				} catch (Exception e) {
					throw new Exception("Error in list orgs!", e);
				}

				dt.setData(bills.getData());
				dt.setiTotalRecords(bills.getRecordCount());
			} catch (Exception e) {
				LOGGER.error("list orgs failed", e);
				return FAIL;
			}
			// dt.setsEcho(String.valueOf(curpage+1));
			String json = this.formateData2DT(dt);
			return json;
		} catch (CustomerNotFoundException e) {
			throw new ApiException(e.getKey());
		} catch (ParseException e) {
			throw new ApiException("无效的时间格式");
		}
	}


    public void handleDataTableDetail(DataTableVo dt, String customerId, String accountPeriod,
                                SearchFilter searchorg) throws Exception {
        List<FormalBillDetailBean> bills;
        try {
            bills = billService.listAllByPageForFormalDetail(customerId, new AccountPeriod(accountPeriod), searchorg);
			FormalBillDetailBean summary = summarize(bills);
            bills.add(summary);
        } catch (Exception e) {
            throw new Exception("Error in list orgs!", e);
        }

        dt.setData(bills);
        dt.setiTotalRecords(bills.size());
    }

    private FormalBillDetailBean summarize(List<FormalBillDetailBean> bills) {
		FormalBillDetailBean totalBean = new FormalBillDetailBean();
		totalBean.setId("");
        totalBean.setAccountPeriod("");
        totalBean.setCustomerName("总计");
        totalBean.setProductName("");
        totalBean.setContractName("");
        totalBean.setFeeType(-1);
        BigDecimal total = new BigDecimal(0);
		BigDecimal totalUnpaid = new BigDecimal(0);
        if (bills != null) {
            for (FormalBillDetailBean bill : bills) {
                total = total.add(new BigDecimal(bill.getAmount()));
				totalUnpaid = totalUnpaid.add(new BigDecimal(bill.getUnpaidAmount()));
            }
        }
        totalBean.setAmount(MathUtil.scale(total.floatValue()));
		totalBean.setUnpaidAmount(MathUtil.scale(totalUnpaid.floatValue()));
        return totalBean;
    }


	@RequestMapping(value = "/resellervoip", method = RequestMethod.POST)
	@ResponseBody
	public String listResellerVoipBills(HttpServletRequest request) {
		try {
			 DataTableVo dt = this.parseData4DT(request);
             String customerName = (String) request.getParameter("s_sserviceid");
             String strAccountPeriod = (String) request.getParameter("s_searchStartTime_STANDSITE");
             AccountPeriod accountPeriod = new AccountPeriod(strAccountPeriod);
             List<FormalBillDetailBean> results=billFormalDetailRepository.listResellerVoipBills(customerName, accountPeriod);
			 dt.setData(results);
             dt.setiTotalRecords(results.size());
             return formateData2DT(dt);
		} catch (ParseException e) {
			 DataTableVo dt = this.parseData4DT(request);
			 List<TableRow> tds = new ArrayList<TableRow>();			
			 dt.setData(tds);
             dt.setiTotalRecords(0);
             return formateData2DT(dt);
		}
	}
}
