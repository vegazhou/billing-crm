package com.kt.api.controller;


import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.bean.BillAge;
import com.kt.biz.bean.WebExRequestRecord;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.WebExRequest;
import com.kt.entity.mysql.crm.WebExSiteDraftReport;
import com.kt.service.RequestService;
import com.kt.service.SearchFilter;
import com.kt.service.WebExSiteService;
import com.kt.service.billing.BillService;

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
 * Created by Garfield Chen on 2016/6/17.
 */
@Controller
@RequestMapping("/billage")
public class BillAgeController extends BaseController {
	
	@Autowired
	BillService billService;
	
	private static final Logger LOGGER = Logger.getLogger(BillAgeController.class);

    
    
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

   		String[] supportedOrderFields = new String[] {"DISPLAY_NAME" ,"WITHIN30",  "EXCEEDWITHIN30", "EXCEEDWITHIN60", "EXCEEDWITHIN90",
				"EXCEEDWITHIN120", "EXCEEDWITHIN150", "EXCEEDWITHIN180", "EXCEEDOVER180"};

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
   		Page<BillAge> page = null;
   		List<BillAge> billAges = null;
   		try {
   			page = billService.listAllByPageForBillAge(curpage, searchorg);
   			billAges = page.getData();
   			totalSize = page.getRecordCount();
   		}  catch (Exception e) {
   			// logger.error("list Exception:"+e);
   			throw new Exception("Error in list orgs!", e);
   		}

   		
   			dt.setData(billAges);
   			dt.setiTotalRecords(totalSize);

   			// logger.debug("completed table Handling");
   		
   	}


	

   	

}
