package com.kt.api.controller;

import com.kt.api.bean.account.CustomerAccounts4Get;
import com.kt.api.bean.account.DepositForm;
import com.kt.api.common.APIConstants;
import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.bean.CustomerAccountsBean;
import com.kt.biz.types.AccountType;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.billing.CustomerAccount;
import com.kt.exception.WafException;
import com.kt.service.CustomerService;
import com.kt.service.SearchFilter;
import com.kt.service.billing.AccountService;
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
import java.util.List;

/**
 * Created by Vega Zhou on 2016/5/5.
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;
    @Autowired
    CustomerService customerService;


    @RequestMapping(value = "/bycustomer/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public CustomerAccounts4Get getAccountByCustomerId(@PathVariable String customerId) {
        try {
            customerService.findByCustomerId(customerId);
            CustomerAccount prepaid = accountService.findOrCreate(customerId, AccountType.PREPAID);
            CustomerAccount deposit = accountService.findOrCreate(customerId, AccountType.DEPOSIT);
            CustomerAccount ccDeposit = accountService.findOrCreate(customerId, AccountType.CC_DEPOSIT);
            CustomerAccounts4Get response = new CustomerAccounts4Get();
            response.setPrepaid(prepaid);
            response.setDeposit(deposit);
            response.setCcDeposit(ccDeposit);
            return response;
        } catch (WafException e) {
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/deposit", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
    public ResponseEntity<String> deposit(@Valid @RequestBody DepositForm bean, HttpServletRequest request) {

        if (StringUtils.isNotBlank(bean.getAccountPeriod())) {
//            try {
//                AccountPeriod accountPeriod = new AccountPeriod(bean.getAccountPeriod());
//                accountService.depositAndCharge(bean.getCustomerId(), AccountType.valueOf(bean.getAccountType()), new BigDecimal(bean.getAmount()), accountPeriod);
                accountService.deposit(bean.getCustomerId(), AccountType.valueOf(bean.getAccountType()), new BigDecimal(bean.getAmount()));
                return new ResponseEntity<String>(HttpStatus.OK);
//            } catch (ParseException ignore) {
//
//            }
        } else {
            accountService.deposit(bean.getCustomerId(), AccountType.valueOf(bean.getAccountType()), new BigDecimal(bean.getAmount()));
            return new ResponseEntity<String>(HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/query")
    @ResponseBody
    public String list(HttpServletRequest request) throws SQLException {
        DataTableVo dt = this.parseData4DT(request);
        String sserviceid = request.getParameter("s_sserviceid1");
        String customerid = request.getParameter("s_customerid");
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
        if (StringUtils.isNotBlank(sserviceid)) {
            searchorg.setDisplayName(sserviceid);
        }

        if (StringUtils.isNotBlank(customerid)) {
   			searchorg.setCustomerId(customerid);
   		}


        String[] supportedOrderFields = new String[]{"CUSTOMER_NAME", null, "DEPOSIT_BALANCE", "PREPAID_BALANCE"};

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
                                SearchFilter searchorg) throws Exception {
        Page<CustomerAccountsBean> page;
        List<CustomerAccountsBean> accounts;
        int totalSize;
        try {
            page = accountService.listAllByPageTemp(curpage, searchorg);
            accounts = page.getData();
            totalSize = page.getRecordCount();
        } catch (Exception e) {
            throw new Exception("Error in list orgs!", e);
        }

        if (accounts != null) {
            for (CustomerAccountsBean account : accounts) {
                scale(account);
            }
            dt.setData(accounts);
            dt.setiTotalRecords(totalSize);
        }
    }


    private void scale(CustomerAccountsBean account) {
        Double prepaidBalance = account.getPrepaidBalance();
        Double depositBalance = account.getDepositBalance();
        Double ccDepositBalance = account.getCcDepositBalance();

        if (prepaidBalance != null) {
            account.setPrepaidBalance(new BigDecimal(prepaidBalance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        } else {
            account.setPrepaidBalance(0d);
        }

        if (depositBalance != null) {
            account.setDepositBalance(new BigDecimal(depositBalance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        } else {
            account.setDepositBalance(0d);
        }

        if (ccDepositBalance != null) {
            account.setCcDepositBalance(new BigDecimal(ccDepositBalance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        } else {
            account.setCcDepositBalance(0d);
        }
    }

}
