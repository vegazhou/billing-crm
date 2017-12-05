package com.kt.api.controller;


import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.bean.SapBillItem;
import com.kt.biz.billing.AccountPeriod;
import com.kt.entity.mysql.billing.AccountOperation;
import com.kt.service.SapReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/10/20.
 */
@Controller
@RequestMapping("/sap")
public class SapController extends BaseController {
    @Autowired
    SapReportService sapReportService;

    @RequestMapping(value = "/billsgbattable")
    @ResponseBody
    public String listSapBills(HttpServletRequest request) throws ParseException {
        
   		String strAccountPeriod = request.getParameter("s_searchStartTime_STANDSITE");
   		AccountPeriod accountPeriod = new AccountPeriod(strAccountPeriod);
        List<SapBillItem> results = sapReportService.findSapBillsByAccountPeriod(accountPeriod);
        DataTableVo dt = this.parseData4DT(request);
        
        List<SapBillItem> tds = new ArrayList<SapBillItem>();
		for (SapBillItem bill : results) {		
			tds.add(bill);			
		}
		dt.setData(tds);
		dt.setiTotalRecords(results.size());
		return formateData2DT(dt);
        
        
    }



    @RequestMapping(value = "/payments")
    @ResponseBody
    public String listSapPayments(HttpServletRequest request) throws ParseException {

        String strAccountPeriod = (String) request.getParameter("s_searchStartTime_STANDSITE");
        List<AccountOperation> results = sapReportService.findUnsyncedSapPayments();
        DataTableVo dt = this.parseData4DT(request);

        List<AccountOperation> tds = new ArrayList<AccountOperation>();
        for (AccountOperation bill : results) {
            tds.add(bill);
        }
        dt.setData(tds);
        dt.setiTotalRecords(results.size());
        return formateData2DT(dt);


    }
}
