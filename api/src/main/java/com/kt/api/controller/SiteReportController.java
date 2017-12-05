package com.kt.api.controller;

import com.kt.api.bean.wbxsite.WbxSiteDraft4Put;
import com.kt.api.common.APIConstants;
import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.site.LanguageMatrix;
import com.kt.biz.site.WebExSitePrimaryFields;
import com.kt.biz.types.Language;
import com.kt.biz.types.Location;
import com.kt.biz.types.TimeZone;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.WebExSiteDraftReport;
import com.kt.exception.InvalidLanguageValueException;
import com.kt.exception.InvalidLocationException;
import com.kt.exception.WafException;
import com.kt.service.SearchFilter;
import com.kt.service.WebExSiteService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garfield Chen on 2016/5/13.
 */
@Controller
@RequestMapping("/sitereport")
public class SiteReportController extends BaseController {
    private static final Logger LOGGER = Logger.getLogger(SiteReportController.class);

    @Autowired
    private WebExSiteService webExSiteService;

  
    @RequestMapping(value = "/query")
   	@ResponseBody
   	public String list(HttpServletRequest request) throws SQLException {
   		DataTableVo dt = this.parseData4DT(request);
   		String sserviceid = request.getParameter("s_sserviceid1");
   		String sname = request.getParameter("s_sname");
   		String sortedColumnId = request.getParameter("iSortCol_0");
   		String sortedOrder = request.getParameter("sSortDir_0");
   		String state = request.getParameter("s_sstate");
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
   		if (StringUtils.isNotBlank(sname)) {
   			searchorg.setCustomerId(sname);
   		}
   	
   		if (StringUtils.isNotBlank(sserviceid)) {
   			searchorg.setDisplayName(sserviceid);
   		}
   		
   		if (StringUtils.isNotBlank(state)) {
			searchorg.setState(state);
		}

   		String[] supportedOrderFields = new String[] {"s.sitename", "c.DISPLAY_NAME" , null, "s.sitename"};

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
    
    
    
    @RequestMapping(value = "/queryonesite")
   	@ResponseBody
   	public String listOneSite(HttpServletRequest request) throws SQLException {
   		DataTableVo dt = this.parseData4DT(request);
   		String sserviceid = request.getParameter("s_siteid");
   		String sname = request.getParameter("s_sname");
   		String sortedColumnId = request.getParameter("iSortCol_0");
   		String sortedOrder = request.getParameter("sSortDir_0");
   		String state = request.getParameter("s_sstate");
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
   		if (StringUtils.isNotBlank(sname)) {
   			searchorg.setDisplayName(sname);
   		}
   	
   		if (StringUtils.isNotBlank(sserviceid)) {
   			searchorg.setCustomerId(sserviceid);
   		}
   		
   		if (StringUtils.isNotBlank(state)) {
			searchorg.setState(state);
		}

   		String[] supportedOrderFields = new String[] {"sitename", "a.DISPLAY_NAME" , null, "s.sitename"};

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
   			handleDataTableOneSite(dt, curpage, totalSize, searchorg);
   		} catch (Exception e) {
   			LOGGER.error("list orgs failed", e);
   			return FAIL;
   		}
   		// dt.setsEcho(String.valueOf(curpage+1));
   		String json = this.formateData2DT(dt);
   		return json;

   	}

   	public void handleDataTableOneSite(DataTableVo dt, int curpage, int totalSize,
   			SearchFilter searchorg) throws Exception {
   		Page<WebExSiteDraftReport> page = null;
   		List<WebExSiteDraftReport> contracts = null;
   		try {
   			page = webExSiteService.listAllByPageForReport(curpage, searchorg);
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
   				WebExSiteDraftReport site = contracts.get(i);

   				TableData td = new TableData();

   			
   				td.setSiteName(site.getSITENAME());
   				td.setPRODUCT_NAME(site.getPRODUCT_NAME());
				td.setCUSTOMER_NAME(site.getCUSTOMER_NAME());
				td.setEFFECTIVESTARTDATE(StringUtils.left(site.getEFFECTIVESTARTDATE(), 10));
				td.setEFFECTIVEENDDATE(StringUtils.left(site.getEFFECTIVEENDDATE(), 10));
				td.setContractId(site.getCONTRACT_ID());
				td.setPid(site.getSite_id());
				td.setCONTRACT_NAME(site.getCONTRACT_NAME());
				td.setRESELLER(site.getRESELLER());
   				tds.add(td);
   			}
   			dt.setData(tds);
   			dt.setiTotalRecords(totalSize);

   			// logger.debug("completed table Handling");
   		}
   	}
   	public void handleDataTable(DataTableVo dt, int curpage, int totalSize,
   			SearchFilter searchorg) throws Exception {
   		Page<WebExSiteDraftReport> page = null;
   		List<WebExSiteDraftReport> contracts = null;
   		try {
   			page = webExSiteService.listAllSiteByPageForReport(curpage, searchorg);
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
   				WebExSiteDraftReport site = contracts.get(i);

   				TableData td = new TableData();

   			
   				td.setSiteName(site.getSITENAME());
   				td.setPRODUCT_NAME(site.getPRODUCT_NAME());
				td.setCUSTOMER_NAME(site.getCUSTOMER_NAME());
				td.setEFFECTIVESTARTDATE(StringUtils.left(site.getEFFECTIVESTARTDATE(), 10));
				td.setEFFECTIVEENDDATE(StringUtils.left(site.getEFFECTIVEENDDATE(), 10));
				td.setPid(site.getSite_id());
				td.setState(site.getState());
				td.setCUSTOMER_ID(site.getCUSTOMER_ID());
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
   		private String siteName = "b2";
   		private String PRODUCT_NAME="b3";
		private String CUSTOMER_NAME = "";
		private String EFFECTIVESTARTDATE = "";
		private String EFFECTIVEENDDATE = "";
		private String contractId;
		private String state;
		private String CONTRACT_NAME;
		private String RESELLER;
		private String CUSTOMER_ID;
   		public TableData() {

   		}

   		public String getPid() {
   			return pid;
   		}

   		public void setPid(String pid) {
   			this.pid = pid;
   		}

		public String getSiteName() {
			return siteName;
		}

		public void setSiteName(String siteName) {
			this.siteName = siteName;
		}

		public String getPRODUCT_NAME() {
			return PRODUCT_NAME;
		}

		public void setPRODUCT_NAME(String pRODUCT_NAME) {
			PRODUCT_NAME = pRODUCT_NAME;
		}

		public String getCUSTOMER_NAME() {
			return CUSTOMER_NAME;
		}

		public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
			CUSTOMER_NAME = cUSTOMER_NAME;
		}

		public String getEFFECTIVESTARTDATE() {
			return EFFECTIVESTARTDATE;
		}

		public void setEFFECTIVESTARTDATE(String eFFECTIVESTARTDATE) {
			EFFECTIVESTARTDATE = eFFECTIVESTARTDATE;
		}

		public String getEFFECTIVEENDDATE() {
			return EFFECTIVEENDDATE;
		}

		public void setEFFECTIVEENDDATE(String eFFECTIVEENDDATE) {
			EFFECTIVEENDDATE = eFFECTIVEENDDATE;
		}

		public String getContractId() {
			return contractId;
		}

		public void setContractId(String contractId) {
			this.contractId = contractId;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCONTRACT_NAME() {
			return CONTRACT_NAME;
		}

		public void setCONTRACT_NAME(String cONTRACT_NAME) {
			CONTRACT_NAME = cONTRACT_NAME;
		}

		public String getRESELLER() {
			return RESELLER;
		}

		public void setRESELLER(String rESELLER) {
			RESELLER = rESELLER;
		}

		public String getCUSTOMER_ID() {
			return CUSTOMER_ID;
		}

		public void setCUSTOMER_ID(String cUSTOMER_ID) {
			CUSTOMER_ID = cUSTOMER_ID;
		}
		
		
	}
   	
   	
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@Valid @RequestBody WbxSiteDraft4Put bean, @PathVariable String id,
                                         HttpServletRequest request) {
        try {
            WebExSitePrimaryFields sitePrimaryFields = new WebExSitePrimaryFields();
            LanguageMatrix languageMatrix = new LanguageMatrix();
            if (StringUtils.isNotBlank(bean.getPrimaryLanguage())) {
                try {
                    languageMatrix.setPrimaryLanguage(Language.valueOf(bean.getPrimaryLanguage()));
                } catch (IllegalArgumentException e) {
                    throw new InvalidLanguageValueException();
                }
                sitePrimaryFields.setLanguages(languageMatrix);
            }
            if (bean.getAdditionalLanguages() != null) {
                for (String language : bean.getAdditionalLanguages()) {
                    try {
                        languageMatrix.enable(Language.valueOf(language));
                    } catch (IllegalArgumentException e) {
                        throw new InvalidLanguageValueException();
                    }
                }
                sitePrimaryFields.setLanguages(languageMatrix);
            }
            if (bean.getTimeZone() != null) {
                sitePrimaryFields.setTimeZone(TimeZone.valueOf(bean.getTimeZone()));
            }
            if (StringUtils.isNotBlank(bean.getLocation())) {
                try {
                    sitePrimaryFields.setLocation(Location.valueOf(bean.getLocation()));
                } catch (IllegalArgumentException e) {
                    throw new InvalidLocationException();
                }
            }
            if (StringUtils.isNotBlank(bean.getSiteName())) {
                sitePrimaryFields.setSiteName(bean.getSiteName());
            }

            webExSiteService.updateSite(id, sitePrimaryFields);
        } catch (WafException e) {
            LOGGER.error("error occurred while updating Product " + id, e);
            throw new ApiException(e.getKey());
        }
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }
}
