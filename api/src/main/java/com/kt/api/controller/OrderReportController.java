package com.kt.api.controller;

import com.kt.api.bean.order.Order4Create;
import com.kt.api.bean.order.Order4Get;
import com.kt.api.bean.order.Order4Put;
import com.kt.api.bean.order.Order4Terminate;
import com.kt.api.common.APIConstants;
import com.kt.api.common.APIUtil;
import com.kt.api.controller.datatable.DataTableVo;


import com.kt.biz.bean.OrderTableRow;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.types.PayInterval;

import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.Order;
import com.kt.entity.mysql.crm.Product;
import com.kt.exception.InvalidDateFormatException;
import com.kt.exception.WafException;
import com.kt.service.OrderService;
import com.kt.service.ProductService;
import com.kt.service.SearchFilter;
import com.kt.util.DateUtil;

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
import java.util.Date;
import java.util.List;

/**
 * Created by Garfield Chen on 2016/5/13.
 */
@Controller
@RequestMapping("/orderreport")
public class OrderReportController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(OrderReportController.class);

    @Autowired
    private OrderService orderService;





	/**
	 * 该接口用于查询某个合同下的所有订单，
	 * @param id
	 * @param request
	 * @return
	 * @throws SQLException
	 */
    @RequestMapping(value = "/query")
   	@ResponseBody
   	public String list(HttpServletRequest request) throws SQLException {
   		DataTableVo dt = this.parseData4DT(request);
   		
   		String customerid = (String) request.getParameter("s_customerid");
   		String sortedColumnId = (String) request.getParameter("iSortCol_0");
   		String sortedOrder = (String) request.getParameter("sSortDir_0");

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
   		if (StringUtils.isNotBlank(customerid)) {
   			searchorg.setCustomerId(customerid);
   		}



   		String[] supportedOrderFields = new String[] {"", "o.EFFECTIVESTARTDATE" };
   		if (sortedColumnId != null && StringUtils.isNumeric(sortedColumnId)) {
   			int orderIndex = Integer.parseInt(sortedColumnId);

   			if (orderIndex < supportedOrderFields.length) {
   				String orderFieldName = supportedOrderFields[orderIndex];
   				if (orderFieldName != null) {
   					searchorg.setOrderByField(orderFieldName);
   					if (sortedOrder != null
   							&& sortedOrder.toLowerCase().equals(ORDER_DESC)) {
   						searchorg.setOrderType(ORDER_DESC);
   					}else{
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

   	public void handleDataTable(DataTableVo dt, int curpage, int totalSize,
   			SearchFilter searchorg) throws Exception {
   		List<OrderTableRow> page = null;
   		List<OrderTableRow> orders = null;
   		try {
   			page = orderService.paginateOrdersForReport(curpage, searchorg);
   			orders = page;
   			totalSize = page.size();
   		}  catch (Exception e) {
   			// logger.error("list Exception:"+e);
   			throw new Exception("Error in list orgs!", e);
   		}


		BigDecimal totalFirstInstallment = new BigDecimal(0);
		BigDecimal totalAmount = new BigDecimal(0);
		for (OrderTableRow order : orders) {
			if (!order.isFromOriginalContract()) {
				totalFirstInstallment = totalFirstInstallment.add(new BigDecimal(order.getFirstInstallment()));
			}
			totalAmount = totalAmount.add(new BigDecimal(order.getTotalAmount()));
		}
		OrderTableRow total = new OrderTableRow();
		total.setProductName("总计");
		total.setOk(true);
		total.setPayInterval("");
		total.setStartTime("");
		total.setEndTime("");
		total.setPlacedDate("");
		total.setFirstInstallment(totalFirstInstallment.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		total.setTotalAmount(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		total.setContractName("");
		total.setContractId("");
		total.setSiteName("");
		orders.add(total);
		dt.setData(orders);
		dt.setiTotalRecords(totalSize);
	}



}
