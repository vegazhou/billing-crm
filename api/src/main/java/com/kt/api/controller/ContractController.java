package com.kt.api.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kt.api.bean.contract.Contract4Create;
import com.kt.api.bean.contract.Contract4Get;
import com.kt.api.bean.contract.Contract4Put;
import com.kt.api.bean.contract.FinApproveForm;
import com.kt.api.common.APIConstants;

import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.bean.ContractBean;
import com.kt.biz.model.common.DefaultPstnRates;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.Contract;
import com.kt.exception.OrderCollisionsDetectedException;
import com.kt.exception.OrderIncompleteException;
import com.kt.exception.WafException;
import com.kt.service.ContractService;
import com.kt.service.OrderBeanCache;
import com.kt.service.SearchFilter;

import net.sf.json.JSONObject;

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
@RequestMapping("/contract")
public class ContractController extends BaseController {
    private static final Logger LOGGER = Logger.getLogger(ContractController.class);

    @Autowired
    private ContractService contractService;


    @RequestMapping(value = "", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Contract4Get add(@Valid @RequestBody Contract4Create bean, HttpServletRequest request) {
        try {
			ContractBean contractBean = convert(bean);
            Contract draft = contractService.draftContract(contractBean);
           
            Contract4Get response = new Contract4Get();
            response.setId(draft.getPid());
            response.setCustomerId(draft.getCustomerId());
            response.setDisplayName(draft.getDisplayName());
            return response;           
        } catch (WafException e) {
            LOGGER.error("error occurred while creating Contract", e);
            throw new ApiException(e.getKey());
        }
    }

	private ContractBean convert(Contract4Create input) {
		ContractBean bean = new ContractBean();
		bean.setCustomerId(input.getCustomerId());
		bean.setDisplayName(input.getDisplayName());
		bean.setSalesmanId(input.getSalesmanId());
		bean.setAgentId(input.getAgentId());
		bean.setIsRegistered(input.isRegistered());
		bean.setComments(input.getComments());
		return bean;
	}


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Contract4Get get(@PathVariable String id) {
        try {
            Contract c = contractService.findByContractId(id);
            Contract4Get response = new Contract4Get();
            response.setId(c.getPid());
            response.setCustomerId(c.getCustomerId());
            response.setDisplayName(c.getDisplayName());
            response.setSalesManId(c.getSalesman());
            response.setAgentId(c.getAgentId());
            response.setComments(c.getComments());
            response.setRegistered(c.getIsRegistered()==1?true:false);
            return response;
        } catch (WafException e) {
            LOGGER.error("error occurred while retrieving Contract " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id, HttpServletRequest request) {
        try {
            contractService.deleteByContractId(id);
        } catch (WafException e) {
            LOGGER.error("error occurred while deleting Contract " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@Valid @RequestBody Contract4Put bean, @PathVariable String id) {
        try {
			contractService.rename(id, bean.getDisplayName(), bean.getSalesMan(), bean.getAgentId(), bean.isRegistered(), bean.getComments());
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred while updating Contract " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/send/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
    public ResponseEntity<String> submit4approval(@PathVariable String id, HttpServletRequest request) {
        try {
            contractService.sendContract4Approval(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (WafException e) {
            LOGGER.error("error occurred while creating Contract", e);
            throw new ApiException(e.getKey());
        } catch (OrderIncompleteException e) {
            LOGGER.error("contract has incomplete orders", e);
            return new ResponseEntity<String>(generateJsonResponse("order_incomplete", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (OrderCollisionsDetectedException e) {
            LOGGER.error("error occurred while creating Contract", e);
            return new ResponseEntity<String>(generateJsonResponse("order_collision_detected", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/withdraw/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
    public ResponseEntity<String> withdraw(@PathVariable String id, HttpServletRequest request) {
        try {
            contractService.withdraw(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (WafException e) {
            LOGGER.error("error occurred while creating Contract", e);
            throw new ApiException(e.getKey());
        }
    }


	@RequestMapping(value = "/approve/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
	public ResponseEntity<String> approve(@PathVariable String id, HttpServletRequest request) {
		try {
			contractService.approve(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (WafException e) {
			LOGGER.error("error occurred while creating Contract", e);
			throw new ApiException(e.getKey());
		} catch (OrderIncompleteException e) {
			throw new ApiException("api.contract.order.incomplete");
		} catch (OrderCollisionsDetectedException e) {
			throw new ApiException("api.contract.order.collision.detected");
		}
	}


	@RequestMapping(value = "/finapprove/{id}", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
	public ResponseEntity<String> finApprove(@Valid @RequestBody FinApproveForm bean, @PathVariable String id, HttpServletRequest request) {
		try {
			contractService.finApprove(id, bean.getReceivedAmount(), bean.getCcReceivedAmount());
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (WafException e) {
			LOGGER.error("error occurred while creating Contract", e);
			throw new ApiException(e.getKey());
		} catch (OrderIncompleteException e) {
			throw new ApiException("api.contract.order.incomplete");
		} catch (OrderCollisionsDetectedException e) {
			throw new ApiException("api.contract.order.collision.detected");
		} catch (Exception e) {
			throw new ApiException("api.contract.order.collision.detected");
		}
	}


	@RequestMapping(value = "/decline/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
	public ResponseEntity<String> decline(@PathVariable String id, HttpServletRequest request) {
		try {
			contractService.decline(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (WafException e) {
			LOGGER.error("error occurred while creating Contract", e);
			throw new ApiException(e.getKey());
		}
	}


	@RequestMapping(value = "/alter/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
	public ResponseEntity<String> alter(@PathVariable String id, HttpServletRequest request) {
		try {
			contractService.draftContractAlteration(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (WafException e) {
			LOGGER.error("error occurred while creating Contract", e);
			throw new ApiException(e.getKey());
		}
	}


	@RequestMapping(value = "/flush/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
	public ResponseEntity<String> flushCache(@PathVariable String id, HttpServletRequest request) {
		OrderBeanCache.flush();
		DefaultPstnRates.flush();
		return new ResponseEntity<String>(HttpStatus.OK);
	}


    private String generateJsonResponse(String key, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", key);
        jsonObject.put("message", message);
        JSONObject error = new JSONObject();
        error.put("error", jsonObject);
        return error.toString();
    }
    
    
    @RequestMapping(value = "/query")
   	@ResponseBody
   	public String list(HttpServletRequest request) throws SQLException {
   		DataTableVo dt = this.parseData4DT(request);
   		String sserviceid = request.getParameter("s_sserviceid1");
   		String sname = request.getParameter("s_sname");
   		String sagent = request.getParameter("s_sagent");
   		String ssalesman = request.getParameter("s_salesman");
   		String sortedColumnId = request.getParameter("iSortCol_0");
   		String sortedOrder = request.getParameter("sSortDir_0");
   		String state = request.getParameter("s_sstate");
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
   			searchorg.setCustomerId(sserviceid);
   		}
   		
   		if (StringUtils.isNotBlank(sagent)) {
   			searchorg.setCustomerName(sagent);
   		}
   	
   		if (StringUtils.isNotBlank(sname)) {
   			searchorg.setDisplayName(sname);
   		}
   		
   		if (StringUtils.isNotBlank(state)) {
			searchorg.setState(state);
		}
   		
   		if (StringUtils.isNotBlank(ssalesman)) {
			searchorg.setSalesman(ssalesman);
		}

   		String[] supportedOrderFields = new String[] {"b.DISPLAY_NAME", "a.DISPLAY_NAME" , null, "salesmanname", "a.DRAFTDATE"};

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
   		Page<Contract> page;
   		List<Contract> contracts;
		int totalSize;
		try {
   			page = contractService.listAllByPage(curpage, searchorg);
   			contracts = page.getData();
   			totalSize = page.getRecordCount();
   		}  catch (Exception e) {
   			// logger.error("list Exception:"+e);
   			throw new Exception("Error in list orgs!", e);
   		}

   		if (contracts != null) {
   			List<TableData> tds = new ArrayList<TableData>();
   			for (int i = 0, n = contracts.size(); i < dt.getiDisplayLength()
   					&& i < n; i++) {
   				Contract contract = contracts.get(i);

   				TableData td = new TableData();

   				td.setPid(contract.getPid());
   				td.setDisplayName(contract.getDisplayName());
   				td.setStatus(contract.getState());
				td.setIsChanging(contract.getIsChanging() > 0);
				td.setDraftDate(truncateDate(contract.getDraftDate()));
				td.setCompany(contract.getCompany());
				td.setSalesManName(contract.getSalesmanname());
				td.setAgentName(contract.getAgentName());
				td.setIsAlter(contract.getIsAlteration() > 0);
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

   		private String pid = "b1";
   		private String displayName = "b2";
   		private String status="b3";
		private boolean isChanging = false;
		private boolean isAlter = false;
		private String draftDate = "";
		private String company = "";
		private String salesManName = "";
		private String agentName;
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

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public boolean isChanging() {
			return isChanging;
		}

		public void setIsChanging(boolean isChanging) {
			this.isChanging = isChanging;
		}

		public boolean isAlter() {
			return isAlter;
		}

		public void setIsAlter(boolean isAlter) {
			this.isAlter = isAlter;
		}

		public String getDraftDate() {
			return draftDate;
		}

		public void setDraftDate(String draftDate) {
			this.draftDate = draftDate;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getSalesManName() {
			return salesManName;
		}

		public void setSalesManName(String salesManName) {
			this.salesManName = salesManName;
		}

		public String getAgentName() {
			return agentName;
		}

		public void setAgentName(String agentName) {
			this.agentName = agentName;
		}
	}
}
