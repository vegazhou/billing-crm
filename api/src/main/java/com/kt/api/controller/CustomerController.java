package com.kt.api.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kt.api.bean.customer.Customer4Create;
import com.kt.api.bean.customer.Customer4Get;
import com.kt.api.bean.customer.Customer4Put;
import com.kt.api.common.APIConstants;
import com.kt.api.common.APIUtil;

import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.customer.CustomerLevel;
import com.kt.biz.customer.CustomerOptionalFields;
import com.kt.biz.customer.CustomerPrimaryFields;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.Customer;
import com.kt.exception.WafException;
import com.kt.service.CustomerService;
import com.kt.service.SearchFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Vega Zhou on 2016/3/19.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
    public ResponseEntity<String> add(@Valid @RequestBody Customer4Create bean, HttpServletRequest request) {
        try {
            Customer newCustomer = customerService.provisionCustomer(bean2primary(bean), bean2optional(bean));
            return APIUtil.createdResonse(request, newCustomer.getPid());
        } catch (WafException e) {
            LOGGER.error("error occurred while creating Product ", e);
            throw new ApiException(e.getKey());
        }
    }

    private CustomerPrimaryFields bean2primary(Customer4Create bean) {
        CustomerPrimaryFields primary = new CustomerPrimaryFields();
        primary.setDisplayName(bean.getDisplayName());
        primary.setIsVat(bean.isVat());
        primary.setContactName(bean.getContactName());
        primary.setContactEmail(bean.getContactEmail());
        primary.setContactPhone(bean.getContactPhone());
        primary.setLevel(CustomerLevel.valueOf(bean.getLevel()));
        return primary;
    }

    private CustomerOptionalFields bean2optional(Customer4Create bean) {
        CustomerOptionalFields optional = new CustomerOptionalFields();
        optional.setAddress(bean.getAddress());
        optional.setBankAccount(bean.getBankAccount());
        optional.setBank(bean.getBank());
        optional.setPhone(bean.getPhone());
        optional.setVatNo(bean.getVatNo());
        optional.setRel(bean.getRel());
        optional.setIndustry(bean.getIndustry());
        return optional;
    }
    
    
    private String getLevel(int levelNumber) {
    	switch (levelNumber) {
        case 1:
            return "ONE";
        case 2:
            return "TWO";
        case 3:
            return "THREE";
        case 4:
            return "FOUR";
        case 5:
            return "FIVE";
    	}
        
       return "";
    }


    private CustomerPrimaryFields bean2primary(Customer4Put bean) {
        CustomerPrimaryFields primary = new CustomerPrimaryFields();
        primary.setDisplayName(bean.getDisplayName());
        primary.setIsVat(bean.isVat());
        primary.setLevel(CustomerLevel.valueOf(bean.getLevel()));
        primary.setContactName(bean.getContactName());
        primary.setContactEmail(bean.getContactEmail());
        primary.setContactPhone(bean.getContactPhone());
        return primary;
    }

    private CustomerOptionalFields bean2optional(Customer4Put bean) {
        CustomerOptionalFields optional = new CustomerOptionalFields();
        optional.setAddress(bean.getAddress());
        optional.setBankAccount(bean.getBankAccount());
        optional.setBank(bean.getBank());
        optional.setPhone(bean.getPhone());
        optional.setVatNo(bean.getVatNo());
        optional.setRel(bean.getRel());
        optional.setIndustry(bean.getIndustry());
        return optional;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Customer4Get get(@PathVariable String id) {
        try {
            Customer c = customerService.findByCustomerId(id);
            Customer4Get response = new Customer4Get();
            response.setDisplayName(c.getDisplayName());
            response.setAddress(c.getAddress());
            response.setBank(c.getBank());
            response.setBankAccount(c.getBankAccount());
            response.setLevel(getLevel(c.getLevel()));
            response.setPhone(c.getPhone());
            response.setVat(c.getIsVat() == 1);
            response.setVatNo(c.getVatNo());
            response.setContactName(c.getContactName());
            response.setContactEmail(c.getContactEmail());
            response.setContactPhone(c.getContactPhone());
            response.setRel(c.getIsRel()==1);
            response.setIndustry(c.getIndustry());
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
            customerService.deleteByCustomerId(id);
        } catch (WafException e) {
            LOGGER.error("error occurred while deleting Customer " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@Valid @RequestBody Customer4Put bean, @PathVariable String id) {
        try {

            customerService.updateCustomer(id, bean2primary(bean), bean2optional(bean));
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred while updating Customer " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/sapsync/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> sapSync(@PathVariable String id) {
        try {
            boolean syncSuccess = customerService.syncCustomer2SAP(id);
            if (syncSuccess) {
                return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }
        } catch (WafException e) {
            LOGGER.error("error occurred while retrieving Customer " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/query")
    @ResponseBody
    public String list(HttpServletRequest request) throws SQLException {
        DataTableVo dt = this.parseData4DT(request);

        String sname = request.getParameter("s_sname").toLowerCase();
        String sortedColumnId = request.getParameter("iSortCol_0");
        String sortedOrder = request.getParameter("sSortDir_0");

        int curpage = 0;
        int totalSize = 0;
        if (dt.getiDisplayStart() > 0) {
            curpage = dt.getiDisplayStart() / dt.getiDisplayLength();
        }
        SearchFilter searchorg = new SearchFilter();
        curpage = curpage + 1;


        if (StringUtils.isNotBlank(sname)) {
            searchorg.setDisplayName(sname);
        }

        String[] supportedOrderFields = new String[]{"DISPLAY_NAME","CREATEDATE"};

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

    private void handleDataTable(DataTableVo dt, int curpage, int totalSize,
                                SearchFilter searchorg) throws Exception {
        Page<Customer> page = null;
        List<Customer> customers = null;
        try {
            page = customerService.listAllNonAgentByPage(curpage, searchorg);
            customers = page.getData();
            totalSize = page.getRecordCount();
        } catch (Exception e) {
            // logger.error("list Exception:"+e);
            throw new Exception("Error in list orgs!", e);
        }

        if (customers != null) {
            List<TableData> tds = new ArrayList<TableData>();
            for (int i = 0, n = customers.size(); i < dt.getiDisplayLength()
                    && i < n; i++) {
                Customer customer = customers.get(i);

                TableData td = new TableData();

                td.setPid(customer.getPid());
                td.setDisplayName(customer.getDisplayName());
                td.setCode(customer.getCode());
                td.setSapSynced(customer.getSapSynced() == 1);
                td.setCreateDate(customer.getCreateDate());

                tds.add(td);
            }
            dt.setData(tds);
            dt.setiTotalRecords(totalSize);

            // logger.debug("completed table Handling");
        }
    }

    private class TableData {

        private String pid = "b1";
        private String displayName = "b2";
        private String code;
        private String createDate;
        private boolean sapSynced = false;


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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public boolean isSapSynced() {
            return sapSynced;
        }

        public void setSapSynced(boolean sapSynced) {
            this.sapSynced = sapSynced;
        }

		public String getCreateDate() {
			return createDate;
		}

		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}
        
    }
}
