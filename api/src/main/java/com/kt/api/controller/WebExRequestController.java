package com.kt.api.controller;


import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.bean.WebExRequestRecord;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.WebExRequest;
import com.kt.entity.mysql.crm.WebExSiteDraftReport;
import com.kt.service.RequestService;
import com.kt.service.SearchFilter;
import com.kt.service.WebExSiteService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/6/7.
 */
@Controller
@RequestMapping("/wbxreq")
public class WebExRequestController extends BaseController {
	
	@Autowired
	private RequestService requestService;
	
	private static final Logger LOGGER = Logger.getLogger(WebExRequestController.class);

    
    
    @RequestMapping(value = "/query")
   	@ResponseBody
   	public String listOneSite(HttpServletRequest request) throws SQLException {
   		DataTableVo dt = this.parseData4DT(request);
   		String scustomer = (String) request.getParameter("s_customer");
   		String sname = (String) request.getParameter("s_sname");
   		String sortedColumnId = (String) request.getParameter("iSortCol_0");
   		String sortedOrder = (String) request.getParameter("sSortDir_0");
   		String state = (String) request.getParameter("s_sstate");
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
   		if (StringUtils.isNotBlank(sname)) {
   			searchorg.setDisplayName(sname);
   		}
   	
   		if (StringUtils.isNotBlank(scustomer)) {
   			searchorg.setCustomerName(scustomer);
   		}
   		
   		if (StringUtils.isNotBlank(state)) {
			searchorg.setState(state);
		}

   		String[] supportedOrderFields = new String[] {"c.DISPLAY_NAME" ,"a.SITE_NAME",  null,null, "a.CREATETIME"};

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
   			handleDataTableOneSite(dt, curpage, totalSize, searchorg);
   		} catch (Exception e) {
   			LOGGER.error("list orgs failed", e);
   			return FAIL;
   		}
   		// dt.setsEcho(String.valueOf(curpage+1));
   		String json = this.formateData2DT(dt);
   		return json;

   	}

   	public void handleDataTableOneSite(DataTableVo dt, int curpage, int totalSize,
   			SearchFilter searchorg) throws Exception {
   		Page<WebExRequest> page = null;
   		List<WebExRequest> contracts = null;
   		try {
   			page = requestService.listAll(curpage, searchorg);
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
   				WebExRequest request = contracts.get(i);

   				TableData td = new TableData();

   			
   				td.setSiteName(request.getSITE_NAME());   				
				td.setCUSTOMER_NAME(request.getDISPLAY_NAME());
				td.setCALL_BACK_STR(request.getCALL_BACK_STR());
				td.setCMD_RESULT(request.getCMD_RESULT());
				td.setWBX_PROV_STR(request.getWBX_PROV_STR());
				td.setTYPE(request.getTYPE());
				td.setSTATE(request.getSTATE());
			
				td.setCREATETIME(request.getCREATETIME());
   				tds.add(td);
   			}
   			dt.setData(tds);
   			dt.setiTotalRecords(totalSize);

   			// logger.debug("completed table Handling");
   		}
   	}


	

   	private class TableData {

   		private String pid = "b1";
   		private String siteName = "b2";
   		private String CREATETIME="b3";
		private String CUSTOMER_NAME = "";
		private String WBX_PROV_STR = "";
		private String CALL_BACK_STR = "";
		private String CMD_RESULT;
		private String TYPE;
		private String STATE;
   		public TableData() {

   		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getSiteName() {
			return siteName;
		}

		public void setSiteName(String siteName) {
			this.siteName = siteName;
		}

		public String getCREATETIME() {
			return CREATETIME;
		}

		public void setCREATETIME(String cREATETIME) {
			CREATETIME = cREATETIME;
		}

		public String getCUSTOMER_NAME() {
			return CUSTOMER_NAME;
		}

		public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
			CUSTOMER_NAME = cUSTOMER_NAME;
		}

		public String getWBX_PROV_STR() {
			return WBX_PROV_STR;
		}

		public void setWBX_PROV_STR(String wBX_PROV_STR) {
			WBX_PROV_STR = wBX_PROV_STR;
		}

		public String getCALL_BACK_STR() {
			return CALL_BACK_STR;
		}

		public void setCALL_BACK_STR(String cALL_BACK_STR) {
			CALL_BACK_STR = cALL_BACK_STR;
		}

		public String getCMD_RESULT() {
			return CMD_RESULT;
		}

		public void setCMD_RESULT(String cMD_RESULT) {
			CMD_RESULT = cMD_RESULT;
		}

		public String getTYPE() {
			return TYPE;
		}

		public void setTYPE(String tYPE) {
			TYPE = tYPE;
		}

		public String getSTATE() {
			return STATE;
		}

		public void setSTATE(String sTATE) {
			STATE = sTATE;
		}

   	
	}

}
