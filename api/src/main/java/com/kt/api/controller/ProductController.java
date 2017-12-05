package com.kt.api.controller;

import java.sql.SQLException;
import java.util.*;

import com.kt.api.bean.product.Product4Create;
import com.kt.api.bean.product.Product4Get;
import com.kt.api.bean.product.Product4Put;
import com.kt.api.common.APIConstants;
import com.kt.api.common.APIUtil;
import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.types.ChargeType;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.Product;
import com.kt.exception.WafException;
import com.kt.service.ProductService;
import com.kt.service.SearchCriteria;

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
 * Created by Vega Zhou on 2016/3/12.
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(ProductController.class);

    @Autowired
    ProductService productService;



    @RequestMapping(value = "", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
    public ResponseEntity<String> add(@Valid @RequestBody Product4Create bean, HttpServletRequest request) {
        try {
            Product product = productService.createProduct(bean.getDisplayName(), bean.getBizId(), bean.getChargeSchemeId(), bean.getTrial(), bean.getAgent());
            return APIUtil.createdResonse(request, product.getPid());
        } catch (WafException e) {
            LOGGER.error("error occurred while creating Product ", e);
            throw new ApiException(e.getKey());
        }

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Product4Get get(@PathVariable String id) {
        try {
            Product product = productService.findById(id);
            return convert2bean(product);
        } catch (WafException e) {
            LOGGER.error("error occurred while retrieving Product " + id, e);
            throw new ApiException(e.getKey());
        }
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Product4Put bean,  @PathVariable String id,
                       HttpServletRequest request) {
        try {
            productService.updateProduct(id, bean.getDisplayName(), bean.getBizId(), bean.getChargeSchemeId(), bean.getTrial(), bean.getAgent());
        } catch (WafException e) {
            LOGGER.error("error occurred while updating Product " + id, e);
            throw new ApiException(e.getKey());
        }
    }


	@RequestMapping(value = "/send/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> send(@PathVariable String id,
									   HttpServletRequest request) {
		try {
			productService.sendProduct4Approval(id);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} catch (WafException e) {
			LOGGER.error("error occurred when updating Product", e);
			throw new ApiException(e.getKey());
		}
	}


	@RequestMapping(value = "/withdraw/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> withdraw(@PathVariable String id,
										   HttpServletRequest request) {
		try {
			productService.withdrawProduct(id);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} catch (WafException e) {
			LOGGER.error("error occurred when updating Product", e);
			throw new ApiException(e.getKey());
		}
	}


	@RequestMapping(value = "/approve/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> approve(@PathVariable String id, HttpServletRequest request) {
		try {
			productService.approve(id);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} catch (WafException e) {
			LOGGER.error("error occurred when approving Product", e);
			throw new ApiException(e.getKey());
		}
	}

	@RequestMapping(value = "/decline/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> decline(@PathVariable String id, HttpServletRequest request) {
		try {
			productService.decline(id);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} catch (WafException e) {
			LOGGER.error("error occurred when approving Product", e);
			throw new ApiException(e.getKey());
		}
	}


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id, HttpServletRequest request) {
        try {
            productService.deleteProduct(id);
        } catch (WafException e) {
            LOGGER.error("error occurred while deleting Product " + id, e);
            throw new ApiException(e.getKey());
        }
    }


	@RequestMapping(value = "/list/{contractid}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String,List<Product4Get>> listAvailableProducts4Contract(@PathVariable String contractid, HttpServletRequest request) {
		try {
			List<Product> products = productService.findAllEffectiveProductsOfContract(contractid);
			return groupByCategory(products);
		} catch (WafException e) {
			throw new ApiException(e.getKey());
		}
	}
	
	@RequestMapping(value = "/listpublicagent", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String,List<Product4Get>> listInEffectPublicAgentProducts() {

		List<Product> products = productService.findInEffectPublicAgentProducts();
		return groupByCategory(products);
	}

	private Map<String, List<Product4Get>> groupByCategory(List<Product> products) {
		Map<String, List<Product4Get>> resultsMap = new HashMap<String, List<Product4Get>>();
		List<Product4Get> byHosts = new LinkedList<Product4Get>();
		List<Product4Get> byPorts = new LinkedList<Product4Get>();
		List<Product4Get> ec = new LinkedList<Product4Get>();
		List<Product4Get> storage = new LinkedList<Product4Get>();
		List<Product4Get> pstn = new LinkedList<Product4Get>();
		List<Product4Get> audioPackage = new LinkedList<Product4Get>();
		List<Product4Get> misc = new LinkedList<Product4Get>();
		List<Product4Get> ah = new LinkedList<Product4Get>();
		List<Product4Get> cmr = new LinkedList<Product4Get>();
		List<Product4Get> cc = new LinkedList<Product4Get>();

		for (Product product : products) {
			ChargeType chargeType = ChargeType.valueOf(product.getChargeType());
			switch (chargeType) {
				case CMR_MONTHLY_PAY:
					cmr.add(convert2bean(product));
					break;
				case MONTHLY_PAY_BY_ACTIVEHOSTS:
					ah.add(convert2bean(product));
					break;
				case MONTHLY_PAY_BY_HOSTS:
					byHosts.add(convert2bean(product));
					break;
				case MONTHLY_PAY_BY_PORTS:
				case MONTHLY_PAY_BY_TOTAL_ATTENDEES:
					byPorts.add(convert2bean(product));
					break;
				case EC_PAY_PER_USE:
				case EC_PREPAID:
					ec.add(convert2bean(product));
					break;
				case MONTHLY_PAY_BY_STORAGE:
				case MONTHLY_PAY_BY_STORAGE_O:
					storage.add(convert2bean(product));
					break;
				case PSTN_STANDARD_CHARGE:
				case PSTN_PACKAGE_CHARGE:
				case PSTN_PERSONAL_CHARGE:
					pstn.add(convert2bean(product));
					break;
				case PSTN_MONTHLY_PACKET:
				case PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES:
				case PSTN_SINGLE_PACKET_FOR_ALL_SITES:
				case PSTN_PERSONAL_PACKET:
					audioPackage.add(convert2bean(product));
					break;
				case TELECOM_CHARGE:
					byHosts.add(convert2bean(product));
					break;
				case MISC_CHARGE:
				case MONTHLY_PAY_PERSONAL_WEBEX:
					misc.add(convert2bean(product));
					break;
				case CC_CALLCENTER_MONTHLY_PAY:
				case CC_CALLCENTER_NUMBER_MONTHLY_PAY:
				case CC_CALLCENTER_PSTN:
				case CC_CALLCENTER_PSTN_PACKAGE:
				case CC_CALLCENTER_PSTN_MONTHLY_PACKAGE:
				case CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE:
				case CC_CALLCENTER_OLS_MONTHLY_PAY:
					cc.add(convert2bean(product));
					break;
			}
		}
		if (byHosts.size() > 0) {
			resultsMap.put("WEBEX_BY_HOSTS", byHosts);
		}
		if (byPorts.size() > 0) {
			resultsMap.put("WEBEX_BY_PORTS", byPorts);
		}
		if (ec.size() > 0) {
			resultsMap.put("WEBEX_EC", ec);
		}
		if (storage.size() > 0) {
			resultsMap.put("WEBEX_STORAGE", storage);
		}
		if (pstn.size() > 0) {
			resultsMap.put("WEBEX_PSTN", pstn);
		}
		if (audioPackage.size() > 0) {
			resultsMap.put("WEBEX_AUDIO_PACKAGE", audioPackage);
		}
		if (misc.size() > 0) {
			resultsMap.put("MISC", misc);
		}
		if (ah.size() > 0) {
			resultsMap.put("WEBEX_BY_AH", ah);
		}
		if (cmr.size() > 0) {
			resultsMap.put("WEBEX_CMR", cmr);
		}
		if (cc.size() > 0) {
			resultsMap.put("CC", cc);
		}

		return resultsMap;
	}


	private Product4Get convert2bean(Product product) {
		Product4Get result = new Product4Get();
		result.setBizId(product.getBizId());
		result.setChargeSchemeId(product.getChargeSchemeId());
		result.setDisplayName(product.getDisplayName());
		result.setPid(product.getPid());
		result.setTrial(product.getIsTrial() == 1);
		result.setAgent(product.getIsAgent() == 1);
		return result;
	}
    
    @RequestMapping(value = "/query")
	@ResponseBody
	public String list(HttpServletRequest request) throws SQLException {
		DataTableVo dt = this.parseData4DT(request);
		
		String sname = request.getParameter("s_sname");
		if (sname != null) {
			sname = sname.toLowerCase();
		}
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

		SearchCriteria searchorg = new SearchCriteria();
		curpage = curpage + 1;

	
		if (StringUtils.isNotBlank(sname)) {
			searchorg.setOrgName(sname);
		}
		
		if (StringUtils.isNotBlank(state)) {
			searchorg.setStatus(state);
		}

		String[] supportedOrderFields = new String[] { "DISPLAY_NAME" };

		if (sortedColumnId != null && StringUtils.isNumeric(sortedColumnId)) {
			int orderIndex = Integer.parseInt(sortedColumnId);

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
								SearchCriteria searchorg) throws Exception {
		Page<Product> page;
		List<Product> products;
		int totalSize;
		try {
			page = productService.paginateAllProducts(curpage, searchorg);
			products = page.getData();
			totalSize = page.getRecordCount();
		} catch (SQLException e) {
			// logger.error("list SQLException:"+e);
			throw new Exception("Error in list orgs!", e);
		} catch (Exception e) {
			// logger.error("list Exception:"+e);
			throw new Exception("Error in list orgs!", e);
		}

		if (products != null) {
			List<TableData> tds = new ArrayList<TableData>();
			for (int i = 0, n = products.size(); i < dt.getiDisplayLength()
					&& i < n; i++) {
				Product product = products.get(i);

				TableData td = new TableData();

				td.setPid(product.getPid());
				td.setDisplayName(product.getDisplayName());
				td.setBizId(product.getBizId());
				td.setChargeSchemeId(product.getChargeSchemeId());
				td.setStatus(product.getState());
				td.setIsAgent(product.getIsAgent() != 0);
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
		private String chargeSchemeId = "b3";
		private String bizId = "b4";
		private String status = "b5";
		private boolean isAgent = false;

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

		public String getChargeSchemeId() {
			return chargeSchemeId;
		}

		public void setChargeSchemeId(String chargeSchemeId) {
			this.chargeSchemeId = chargeSchemeId;
		}

		public String getBizId() {
			return bizId;
		}

		public void setBizId(String bizId) {
			this.bizId = bizId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public boolean isAgent() {
			return isAgent;
		}

		public void setIsAgent(boolean isAgent) {
			this.isAgent = isAgent;
		}
	}
}
