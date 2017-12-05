package com.kt.api.controller;

import com.kt.api.bean.order.*;
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
 * Created by Vega Zhou on 2016/3/19.
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
    public ResponseEntity<String> add(@Valid @RequestBody Order4Create bean, HttpServletRequest request) {
        try {
            Date startDate;
            try {
                startDate = DateUtil.toDate(bean.getStartDate());
            } catch (ParseException e) {
                throw new InvalidDateFormatException();
            }
            List<Order> order = orderService.placeOrders(bean.getContractId(), bean.getProductIds(), startDate, PayInterval.valueOf(bean.getPayInterval()), bean.getBizChance());
            return APIUtil.createdResonse(request, "");
        } catch (WafException e) {
            LOGGER.error("error occurred while creating Order ", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Order4Get get(@PathVariable String id) {
        try {
            Order order = orderService.findOrderById(id);
            Product product = productService.findById(order.getProductId());
            Order4Get response = new Order4Get();
            response.setProductId(product.getPid());
            response.setBizId(order.getBizId());
            response.setChargeSchemeId(order.getChargeId());
            response.setProductName(product.getDisplayName());
            response.setPayInterval(String.valueOf(order.getPayInterval()));
            response.setEffectiveStartDate(order.getEffectiveStartDate());
			response.setBizChance(order.getBizChance());
            return response;
        } catch (WafException e) {
            LOGGER.error("error occurred while retrieving Customer " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id, HttpServletRequest request) {
        try {
            orderService.deleteOrderById(id);
        } catch (WafException e) {
            LOGGER.error("error occurred while deleting Customer " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@Valid @RequestBody Order4Put bean, @PathVariable String id) {
        try {
            Date startDate;
            try {
                startDate = DateUtil.toDate(bean.getStartDate());
            } catch (ParseException e) {
                throw new InvalidDateFormatException();
            }
            PayInterval payInterval = PayInterval.valueOf(bean.getPayInterval());
            orderService.updateOrderById(id, startDate, payInterval, bean.getBizChance());
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred while updating Customer " + id, e);
            throw new ApiException(e.getKey());
        } catch (ParseException e) {
			throw new ApiException("");
		}
	}

	@RequestMapping(value = "/terminate/{id}", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> terminate(@Valid @RequestBody Order4Terminate bean, @PathVariable String id) {
		try {
			Date date = null;
			try {
				date = DateUtil.toShortDate(bean.getDate());
			} catch (ParseException e) {
				throw new InvalidDateFormatException();
			}
			if (StringUtils.equals("bymonth", bean.getType())) {
				orderService.terminateOrderOnAccountPeriod(id, new AccountPeriod(date));
			} else {
                orderService.terminateOrderOnSpecificDay(id, date);
			}
            return new ResponseEntity<String>(HttpStatus.OK);
		} catch (WafException e) {
			LOGGER.error("error occurred while terminating Order " + id, e);
			throw new ApiException(e.getKey());
		}
	}


	@RequestMapping(value = "/tunefi", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void tune(@Valid @RequestBody TuneFirstInstallmentForm form, HttpServletRequest request) {
		try {
			orderService.tuneFirstInstallment(form.getOrderId(), new BigDecimal(form.getAmount()));
		} catch (WafException e) {
			LOGGER.error("error occurred while tuning first installment " + form.getOrderId(), e);
			throw new ApiException(e.getKey());
		}
	}


	/**
	 * 该接口用于查询某个合同下的所有订单，
	 * @param id
	 * @param request
	 * @return
	 * @throws SQLException
	 */
    @RequestMapping(value = "/query/{contractId}")
   	@ResponseBody
   	public String listOrdersOfContract(@PathVariable String contractId,HttpServletRequest request) throws SQLException {
   		DataTableVo dt = this.parseData4DT(request);

   		String sortedColumnId = request.getParameter("iSortCol_0");
   		String sortedOrder = request.getParameter("sSortDir_0");

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
   		if (StringUtils.isNotBlank(contractId)) {
   			searchorg.setCustomerId(contractId);
   		}



   		String[] supportedOrderFields = new String[] { "a.product_id", "c.SITENAME", "a.EFFECTIVESTARTDATE",
				"a.EFFECTIVEENDDATE", null, "a.FIRST_INSTALLMENT", "a.TOTAL_AMOUNT" };

   		if (sortedColumnId != null && StringUtils.isNumeric(sortedColumnId)) {
   			int orderIndex = Integer.parseInt(sortedColumnId);
			searchorg.setOrderType(ORDER_ASC);
   			if (orderIndex < supportedOrderFields.length) {
   				String orderFieldName = supportedOrderFields[orderIndex];
   				if (orderFieldName != null) {
   					searchorg.setOrderByField(orderFieldName);
   					if (sortedOrder != null
   							&& sortedOrder.toLowerCase().equals(ORDER_DESC)) {
   						searchorg.setOrderType(ORDER_DESC);
   					}
   				}
   			}
   		}

   		searchorg.setPageSize(dt.getiDisplayLength());
   		try {
   			handleDataTable(dt, curpage, totalSize, searchorg);
   		} catch (Exception e) {
   			LOGGER.error("list orgs failed", e);
			e.printStackTrace();
//   			return FAIL;
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
   			page = orderService.paginateOrders(curpage, searchorg);
   			orders = page;
   			totalSize = page.size();
   		}  catch (Exception e) {
   			// logger.error("list Exception:"+e);
   			throw new Exception("Error in list orgs!", e);
   		}


		BigDecimal totalFirstInstallment = new BigDecimal(0);
		BigDecimal totalAmount = new BigDecimal(0);
		for (OrderTableRow order : orders) {
//			if (!order.isFromOriginalContract()) {
				totalFirstInstallment = totalFirstInstallment.add(new BigDecimal(order.getFirstInstallment()));
//			}
			totalAmount = totalAmount.add(new BigDecimal(order.getTotalAmount()));
		}
		OrderTableRow total = new OrderTableRow();
		total.setSiteName("");
		total.setProductName("总计");
		total.setOk(true);
		total.setPayInterval("");
		total.setStartTime("");
		total.setFirstInstallment(totalFirstInstallment.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		total.setTotalAmount(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		orders.add(total);
		dt.setData(orders);
		dt.setiTotalRecords(totalSize);
	}

}
