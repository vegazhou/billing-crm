package com.kt.api.controller;

import com.kt.api.bean.salesman.Salesman4Create;
import com.kt.api.bean.salesman.Salesman4Get;
import com.kt.api.bean.salesman.Salesman4Put;
import com.kt.api.common.APIConstants;
import com.kt.api.common.APIUtil;

import com.kt.api.controller.datatable.DataTableVo;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.Salesman;
import com.kt.exception.WafException;
import com.kt.service.SalesmanService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/6/2.
 */
@Controller
@RequestMapping("/salesman")
public class SalesmanController extends BaseController{

    private static final Logger LOGGER = Logger.getLogger(SalesmanController.class);

    @Autowired
    SalesmanService salesmanService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
    public ResponseEntity<String> add(@Valid @RequestBody Salesman4Create bean, HttpServletRequest request) {
        try {
            Salesman salesman = salesmanService.createSalesman(bean.getName(), bean.getEmail());
            return APIUtil.createdResonse(request, salesman.getId());
        } catch (WafException e) {
            LOGGER.error("error occurred while creating Salesman ", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Salesman4Get get(@PathVariable String id) {
        try {
            Salesman salesman = salesmanService.findById(id);
            Salesman4Get response = new Salesman4Get();
            response.setName(salesman.getName());
            response.setEmail(salesman.getEmail());
            response.setEnabled(salesman.getEnabled());
            return response;
        } catch (WafException e) {
            LOGGER.error("error occurred while retrieving Salesman " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id, HttpServletRequest request) {
        try {
            salesmanService.deleteSalesman(id);
        } catch (WafException e) {
            LOGGER.error("error occurred while deleting Salesman " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@Valid @RequestBody Salesman4Put bean, @PathVariable String id) {
        try {
            salesmanService.updateSalesman(id, bean.getName(), bean.getEmail(),bean.getEnabled());
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred while updating Customer " + id, e);
            throw new ApiException(e.getKey());
        }
    }
    
    @RequestMapping(value = "/changestatus/{id}", method = RequestMethod.PUT, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> updateStatus(@Valid @RequestBody Salesman4Put bean, @PathVariable String id) {
        try {
            salesmanService.updateSalesman(id, "", "",bean.getEnabled());
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred while updating Customer " + id, e);
            throw new ApiException(e.getKey());
        }
    }

    @RequestMapping(value = "/enabledsales", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Salesman> listEnabledSales(HttpServletRequest request) {
        return salesmanService.listAllEnabledSalesmen();
    }
    
    @RequestMapping(value = "/query")
    @ResponseBody
    public String listAllTemplates(HttpServletRequest request) {
        DataTableVo dt = this.parseData4DT(request);
        String displayNameFilter = request.getParameter("s_sname");
      
        String stateFilter = request.getParameter("s_state");
        String sortedColumnId = request.getParameter("iSortCol_0");
        String sortedOrder = request.getParameter("sSortDir_0");

        SearchFilter searchFilter = new SearchFilter();
        if (StringUtils.isNotBlank(displayNameFilter)) {
            searchFilter.setDisplayName(displayNameFilter);
        }
      
        if (StringUtils.isNotBlank(stateFilter)) {
            searchFilter.setState(stateFilter);
        }
        final String[] SUPPORTED_SORTING_FIELDS = new String[]{"NAME", "EMAIL"};

        if (sortedColumnId != null && StringUtils.isNumeric(sortedColumnId)) {
            int orderIndex = Integer.parseInt(sortedColumnId);

            if (orderIndex < SUPPORTED_SORTING_FIELDS.length) {
                String orderFieldName = SUPPORTED_SORTING_FIELDS[orderIndex];
                if (orderFieldName != null) {
                    searchFilter.setOrderByField(orderFieldName);
                    if (sortedOrder != null && sortedOrder.toLowerCase().equals(ORDER_DESC)) {
                        searchFilter.setOrderType(ORDER_DESC);
                    }else {
                    	searchFilter.setOrderType(ORDER_ASC);
                    }
                }
            }
        }
        searchFilter.setPageSize(dt.getiDisplayLength());


        try {
            listChargeSchemeTemplates(dt, searchFilter);
        } catch (Exception e) {
            return FAIL;
        }
        String json = this.formateData2DT(dt);
        return json;
    }


    private void listChargeSchemeTemplates(DataTableVo dt, SearchFilter searchFilter) throws Exception {
        Page<Salesman> page;
        List<Salesman> salesmans;
        int totalSize;
        try {
            page = salesmanService.listAll(dt.getCurrentPageNumber(), searchFilter);
            salesmans = page.getData();
            totalSize = page.getRecordCount();
        } catch (Exception e) {
            //logger.error("list Exception:"+e);
            throw new Exception("Error in list systemuser!", e);
        }


        if (salesmans != null) {
            List<TableRow> tds = new ArrayList<TableRow>();
            for (int i = 0, n = salesmans.size(); i < dt.getiDisplayLength() && i < n; i++) {
            	Salesman salesman = salesmans.get(i);

                TableRow td = new TableRow();


                td.setPid(salesman.getId());
                td.setEmail(salesman.getEmail());
                td.setDisplayName(salesman.getName());
                td.setEnabled(salesman.getEnabled());               

                tds.add(td);
            }
            dt.setData(tds);
            dt.setiTotalRecords(totalSize);
        }
    }


    private class TableRow {
        private String pid = "b1";
        private String displayName = "b2";
        private String email = "b3";
        private int enabled = 1;
   

        public TableRow() {

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

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public int getEnabled() {
			return enabled;
		}

		public void setEnabled(int enabled) {
			this.enabled = enabled;
		}




    }
    
    
    
}
