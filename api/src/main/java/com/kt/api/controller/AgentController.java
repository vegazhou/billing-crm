package com.kt.api.controller;

import com.kt.api.bean.agent.AgentProduct4Create;
import com.kt.api.bean.customer.Customer4Create;
import com.kt.api.common.APIConstants;
import com.kt.api.common.APIUtil;
import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.bean.OrderTableRow;
import com.kt.biz.customer.CustomerLevel;
import com.kt.biz.customer.CustomerOptionalFields;
import com.kt.biz.customer.CustomerPrimaryFields;
import com.kt.biz.types.AgentType;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.Customer;
import com.kt.entity.mysql.crm.Product;
import com.kt.exception.WafException;
import com.kt.service.CustomerService;
import com.kt.service.ProductService;
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

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/11/17.
 */
@Controller
@RequestMapping("/agent")
public class AgentController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(AgentController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/listallagents", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Customer> listAllAgents(HttpServletRequest request) {
        return customerService.listAllAgentsAndReseller2();
    }


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
        primary.setAgentType(AgentType.AGENT);
        return primary;
    }

    private CustomerOptionalFields bean2optional(Customer4Create bean) {
        CustomerOptionalFields optional = new CustomerOptionalFields();
        optional.setAddress(bean.getAddress());
        optional.setBankAccount(bean.getBankAccount());
        optional.setBank(bean.getBank());
        optional.setPhone(bean.getPhone());
        optional.setVatNo(bean.getVatNo());
        return optional;
    }



    @RequestMapping(value = "/draftproduct", method = RequestMethod.POST, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void draftAgentProduct(@Valid @RequestBody AgentProduct4Create bean, HttpServletRequest request) {
        try {
            productService.draftAgentProducts(bean.getAgentId(), bean.getProductIds());
        } catch (WafException e) {
            LOGGER.error("error occurred while creating agent Product ", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/onshelf/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void onShelfAgentProduct(@PathVariable String id) {
        try {
            productService.onShelfAgentProduct(id);
        } catch (WafException e) {
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/offshelf/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void offShelfAgentProduct(@PathVariable String id) {
        try {
            productService.offShelfAgentProduct(id);
        } catch (WafException e) {
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
        if (dt.getiDisplayStart() > 0) {
            curpage = dt.getiDisplayStart() / dt.getiDisplayLength();
        }
        SearchFilter searchorg = new SearchFilter();
        curpage = curpage + 1;


        if (StringUtils.isNotBlank(sname)) {
            searchorg.setDisplayName(sname);
        }

        String[] supportedOrderFields = new String[]{"DISPLAY_NAME", "CREATEDATE"};

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

    private void handleDataTable(DataTableVo dt, int curpage, SearchFilter searchorg) throws Exception {
        Page<Customer> page;
        List<Customer> customers;
        int totalSize;
        try {
            page = customerService.listAllAgentsByPage(curpage, searchorg);
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
        
        
        /**
    	 * 该接口用于查询某个代理下的所有产品，
    	 * @param id
    	 * @param request
    	 * @return
    	 * @throws SQLException
    	 */
        @RequestMapping(value = "/queryproductforagent/{agentId}")
       	@ResponseBody
       	public String listProductsOfAgent(@PathVariable String agentId,HttpServletRequest request) throws SQLException {
       		DataTableVo dt = this.parseData4DT(request);

       		String sortedColumnId = request.getParameter("iSortCol_0");
       		String sortedOrder = request.getParameter("sSortDir_0");
       		String sname = request.getParameter("s_sname");
    		if (sname != null) {
    			sname = sname.toLowerCase();
    		}
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
       		if (StringUtils.isNotBlank(agentId)) {
       			searchorg.setCustomerId(agentId);
       		}
       		if (StringUtils.isNotBlank(sname)) {
       			searchorg.setDisplayName(sname);
       		}


       		String[] supportedOrderFields = new String[] { "DISPLAY_NAME"};

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
       			handleDataTable2(dt, curpage, totalSize, searchorg);
       		} catch (Exception e) {
       			LOGGER.error("list orgs failed", e);
    			e.printStackTrace();
//       			return FAIL;
       		}
       		// dt.setsEcho(String.valueOf(curpage+1));
       		String json = this.formateData2DT(dt);
       		return json;

       	}

     

		public void handleDataTable2(DataTableVo dt, int curpage, int totalSize,
       			SearchFilter searchorg) throws Exception {
       		Page<Product> page = null;
       		List<Product> orders = null;
       		try {
       			page = productService.findProductsForAgent(curpage, searchorg);
       			orders = page.getData();
       			totalSize = page.getRecordCount();
       		}  catch (Exception e) {
       			// logger.error("list Exception:"+e);
       			throw new Exception("Error in list orgs!", e);
       		}


    	
    		dt.setData(orders);
    		dt.setiTotalRecords(totalSize);

    }
}
