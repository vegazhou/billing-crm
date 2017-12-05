package com.kt.api.controller;

import com.kt.api.bean.bill.AccountPeriodAndCustomerIdForm;
import com.kt.api.bean.bill.AccountPeriodForm;
import com.kt.api.bean.bill.TuneTempBillBean;
import com.kt.api.common.APIConstants;

import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.bean.TempBillDetailBean;
import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.billing.AccountOperation;
import com.kt.entity.mysql.billing.BillTemp;
import com.kt.exception.WafException;
import com.kt.repo.mysql.billing.BillTempRepository;
import com.kt.service.SearchFilter;
import com.kt.service.billing.BillService;

import com.kt.util.MathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/5/4.
 */
@Controller
@RequestMapping("/tbill")
public class TempBillController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(BillController.class);

    @Autowired
    BillService billService;
    @Autowired
    BillTempRepository billTempRepository;

    @RequestMapping(value = "/tune/{id}", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void tune(@Valid @RequestBody TuneTempBillBean bean, @PathVariable String id, HttpServletRequest request) {
        try {
            billService.tuneTempBill(bean.getId(), new BigDecimal(bean.getAmount()), bean.getComments());
        } catch (WafException e) {
            LOGGER.error("error occurred while tuning formal bill " + id, e);
            throw new ApiException(e.getKey());
        }
    }

    @RequestMapping(value = "/query")
    @ResponseBody
    public String list(HttpServletRequest request) throws SQLException {
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
            handleDataTable(dt, customerId, accountPeriod, searchorg);
        } catch (Exception e) {
            LOGGER.error("list orgs failed", e);
            return FAIL;
        }
        // dt.setsEcho(String.valueOf(curpage+1));
        String json = this.formateData2DT(dt);
        return json;

    }

    public void handleDataTable(DataTableVo dt, String customerId, String accountPeriod,
                                SearchFilter searchorg) throws Exception {
        List<TempBillDetailBean> bills;
        try {
            bills = billService.listAllByPageTemp(customerId, new AccountPeriod(accountPeriod), searchorg);
            TempBillDetailBean summary = summarize(bills);
            bills.add(summary);
        } catch (Exception e) {
            throw new Exception("Error in list orgs!", e);
        }

        dt.setData(bills);
        dt.setiTotalRecords(bills.size());
    }

    private TempBillDetailBean summarize(List<TempBillDetailBean> bills) {
        TempBillDetailBean totalBean = new TempBillDetailBean();
        totalBean.setAccountPeriod("");
        totalBean.setCustomerName("总计");
        totalBean.setProductName("");
        totalBean.setContractName("");
        totalBean.setFeeType(-1);
        BigDecimal total = new BigDecimal(0);
        if (bills != null) {
            for (TempBillDetailBean bill : bills) {
                total = total.add(new BigDecimal(bill.getAmount()));
            }
        }
        totalBean.setAmount(MathUtil.scale(total.floatValue()));
        return totalBean;
    }


    @RequestMapping(value = "/periodicFeeGrouped", method = RequestMethod.POST)
    @ResponseBody
    public String periodicFeeGrouped(HttpServletRequest request) {
        try {
        	String strAccountPeriod = request.getParameter("s_searchStartTime_STANDSITE");
       		AccountPeriod accountPeriod = new AccountPeriod(strAccountPeriod);
       		List<TempBillDetailBean> results = billTempRepository.findPeriodicBillGroupByCustomer(accountPeriod);            
            DataTableVo dt = this.parseData4DT(request);          
            dt.setData(results);
            dt.setiTotalRecords(results.size());
            return formateData2DT(dt);

        } catch (ParseException e) {
            return null;
        }
    }


    @RequestMapping(value = "/periodicFeeDetailed",  method = RequestMethod.POST)
    @ResponseBody
    public String periodicFeeDetailed(HttpServletRequest request) {
        try {
        	 DataTableVo dt = this.parseData4DT(request);
             String customerId = (String) request.getParameter("s_sserviceid");
             String strAccountPeriod = (String) request.getParameter("s_searchStartTime_STANDSITE");
             AccountPeriod accountPeriod = new AccountPeriod(strAccountPeriod);
             List<TempBillDetailBean>  results = billTempRepository.findPeriodicBillByCustomer(accountPeriod, customerId);
             dt.setData(results);
             dt.setiTotalRecords(results.size());
             return formateData2DT(dt);
        } catch (ParseException e) {
            return null;
        }
    }
}
