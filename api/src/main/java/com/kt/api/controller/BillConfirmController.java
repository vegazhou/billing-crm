package com.kt.api.controller;

import com.kt.api.bean.bill.TuneTempBillBean;
import com.kt.api.common.APIConstants;

import com.kt.api.controller.datatable.DataTableVo;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.billing.BillTemp;
import com.kt.exception.WafException;
import com.kt.service.SearchFilter;
import com.kt.service.billing.BillService;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garfield Chen on 2016/5/9.
 */
@Controller
@RequestMapping("/confirmbill")
public class BillConfirmController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(BillController.class);

    @Autowired
    BillService billService;

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
        String sserviceid = request.getParameter("s_sserviceid1");
        String sname = request.getParameter("s_searchStartTime_STANDSITE");
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
        if (StringUtils.isNotBlank(sserviceid)) {
            searchorg.setCustomerId(sserviceid);
        }

        if (StringUtils.isNotBlank(sname)) {
            searchorg.setAccountPeriod(sname);
        }


        String[] supportedOrderFields = new String[]{null,"t2.DISPLAY_NAME", null, "t1.ACCOUNT_PERIOD", "amount", "ISCONFIRMED"};

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
        Page<BillTemp> page;
        List<BillTemp> bills;
        try {
            page = billService.listAllByPageTempForConfirm(curpage, searchorg);
            bills = page.getData();
            totalSize = page.getRecordCount();
        } catch (Exception e) {
            // logger.error("list Exception:"+e);
            throw new Exception("Error in list orgs!", e);
        }

        if (bills != null) {
            List<TableData> tds = new ArrayList<TableData>();
            for (int i = 0, n = bills.size(); i < dt.getiDisplayLength()
                    && i < n; i++) {
                BillTemp bill = bills.get(i);

                TableData td = new TableData();

                td.setPid(bill.getCustomerId());
                td.setFeeType(bill.getFeeType());
                if (bill.getAmount() != null) {
                    td.setAmount(new BigDecimal(bill.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                } else {
                    td.setAmount("无");
                }
                td.setAccountPeriod(bill.getAccountPeriod());
                td.setCompany(bill.getCompany());
                td.setCompanyId(bill.getCompanyId());
                td.setIsConfirmed(bill.isConfirmed());
                td.setCompanyCode(bill.getCustomerCode());
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

        private String pid;
        private String accountPeriod = "b2";
        private String amount;
        private int feeType = 0;
        private String company = "";
        private String companyId = "";
        private String companyCode = "";
        private boolean isConfirmed = false;
        public TableData() {

        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
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

        public int getFeeType() {
            return feeType;
        }

        public void setFeeType(int feeType) {
            this.feeType = feeType;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

        public boolean isConfirmed() {
            return isConfirmed;
        }

        public void setIsConfirmed(boolean isConfirmed) {
            this.isConfirmed = isConfirmed;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }
    }
}
