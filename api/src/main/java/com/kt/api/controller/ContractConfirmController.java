package com.kt.api.controller;

import com.kt.api.bean.contract.ContractConfirmDetail;
import com.kt.api.common.APIConstants;
import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.bean.ContractFirstInstallments;
import com.kt.biz.bean.ContractRefunds;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.Contract;
import com.kt.exception.WafException;
import com.kt.service.ContractService;
import com.kt.service.SearchFilter;
import com.kt.util.MathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garfield Chen on 2016/5/11.
 */
@Controller
@RequestMapping("/contractconfirm")
public class ContractConfirmController extends BaseController {
    private static final Logger LOGGER = Logger.getLogger(ContractConfirmController.class);

    @Autowired
    private ContractService contractService;


	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ContractConfirmDetail getConfirmDetail(@PathVariable String id, HttpServletRequest request) {
		try {
			Contract c = contractService.findByContractId(id);
			ContractRefunds refund = contractService.calcTotalRefundAmount(c);
			ContractFirstInstallments firstInstallments = contractService.summarizeFirstInstallment(id);

			ContractConfirmDetail result = new ContractConfirmDetail();
			result.setId(id);

			result.setWebexFirstInstallment(MathUtil.scale(firstInstallments.getWebexFirstInstallment().floatValue()));
			result.setCcFirstInstallment(MathUtil.scale(firstInstallments.getCcFirstInstallment().floatValue()));

			result.setWebexRefund(MathUtil.scale(refund.getWebexRefund().floatValue()));
			result.setCcRefund(MathUtil.scale(refund.getCcRefund().floatValue()));

			return result;
		} catch (WafException e) {
			LOGGER.error("error occurred while getting contract confirm detail " + id, e);
			throw new ApiException(e.getKey());
		}
	}
    
    
    @RequestMapping(value = "/query")
   	@ResponseBody
   	public String list(HttpServletRequest request) throws SQLException {
   		DataTableVo dt = this.parseData4DT(request);
   		String sserviceid = request.getParameter("s_sserviceid1");
   		String sname = request.getParameter("s_sname");
   		String sortedColumnId = request.getParameter("iSortCol_0");
   		String sortedOrder = request.getParameter("sSortDir_0");
   		String state = request.getParameter("s_sstate");
   		int curpage = 0;
   		int totalSize = 0;
   		if (dt.getiDisplayStart() > 0) {
   			curpage = dt.getiDisplayStart() / dt.getiDisplayLength();
   		}
   		// logger.debug("list systemuser =" + saccount + "; sphone=" + sphone +
   		// "; sname=" + sname); // "aphone=" + aphone + "; aacount=" + aacount
   		// );

   		SearchFilter searchorg = new SearchFilter();
   		curpage = curpage + 1;
   		if (StringUtils.isNotBlank(sserviceid)) {
   			searchorg.setCustomerId(sserviceid);
   		}
   	
   		if (StringUtils.isNotBlank(sname)) {
   			searchorg.setDisplayName(sname);
   		}
   		
   		if (StringUtils.isNotBlank(state)) {
			searchorg.setState(state);
		}

   		String[] supportedOrderFields = new String[] {"t1.DISPLAY_NAME", null,"FIRST_INSTALLMENT" ,  "t1.DRAFTDATE"};

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
   			handleDataTable(dt, curpage, totalSize, searchorg);
   		} catch (Exception e) {
   			LOGGER.error("list orgs failed", e);
   			return FAIL;
   		}
   		// dt.setsEcho(String.valueOf(curpage+1));
   		String json = this.formateData2DT(dt);
   		return json;

   	}

   	private void handleDataTable(DataTableVo dt, int curpage, int totalSize,
   			SearchFilter searchorg) throws Exception {
   		Page<Contract> page = null;
   		List<Contract> contracts = null;
   		try {
   			page = contractService.listAllByPageForConfirm(curpage, searchorg);
   			contracts = page.getData();
   			totalSize = page.getRecordCount();
   		}  catch (Exception e) {
   			// logger.error("list Exception:"+e);
   			throw new Exception("Error in list orgs!", e);
   		}

   		if (contracts != null) {
   			List<TableData> tds = new ArrayList<TableData>();
   			for (int i = 0, n = contracts.size(); i < dt.getiDisplayLength()
   					&& i < n; i++) {
   				Contract contract = contracts.get(i);

   				TableData td = new TableData();

   				td.setPid(contract.getPid());
   				td.setDisplayName(contract.getDisplayName());
   				td.setStatus(contract.getState());
				td.setIsChanging(contract.getIsChanging() > 0);
				td.setDraftDate(truncateDate(contract.getDraftDate()));
				td.setCompany(contract.getCompany());
                td.setFirstInstallment(new BigDecimal(contract.getFIRST_INSTALLMENT()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				td.setAgent(contract.getAgentName());
   				tds.add(td);
   			}
   			dt.setData(tds);
   			dt.setiTotalRecords(totalSize);

   			// logger.debug("completed table Handling");
   		}
   	}

	private String truncateDate(String date) {
		if (StringUtils.isBlank(date)) {
			return "";
		}
		if (date.length() >= 10) {
			return StringUtils.left(date, 10);
		} else {
			return date;
		}
	}

   	private class TableData {

   		private String pid = "b1";
   		private String displayName = "b2";
   		private String status="b3";
		private boolean isChanging = false;
		private String draftDate = "";
		private String company = "";
		private String firstInstallment = "";
		private String agent = "";

   		public TableData() {

   		}

   		public String getPid() {
   			return pid;
   		}

   		public void setPid(String pid) {
   			this.pid = pid;
   		}

   		public String getDisplayName() {
   			return displayName;
   		}

   		public void setDisplayName(String displayName) {
   			this.displayName = displayName;
   		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public boolean isChanging() {
			return isChanging;
		}

		public void setIsChanging(boolean isChanging) {
			this.isChanging = isChanging;
		}

		public String getDraftDate() {
			return draftDate;
		}

		public void setDraftDate(String draftDate) {
			this.draftDate = draftDate;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getFirstInstallment() {
			return firstInstallment;
		}

		public void setFirstInstallment(String firstInstallment) {
			this.firstInstallment = firstInstallment;
		}

		public String getAgent() {
			return agent;
		}

		public void setAgent(String agent) {
			this.agent = agent;
		}
	}
}
