package com.kt.api.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kt.api.bean.wbxsite.WbxSiteDraft4Create;
import com.kt.api.bean.wbxsite.WbxSiteDraft4Get;
import com.kt.api.bean.wbxsite.WbxSiteDraft4Put;
import com.kt.api.common.APIConstants;
import com.kt.api.common.APIUtil;

import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.site.LanguageMatrix;
import com.kt.biz.site.WebExSitePrimaryFields;
import com.kt.biz.types.Language;
import com.kt.biz.types.Location;
import com.kt.biz.types.TimeZone;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.WebExSite;
import com.kt.entity.mysql.crm.WebExSiteDraft;
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

/**
 * Created by Vega Zhou on 2016/3/19.
 */
@Controller
@RequestMapping("/wbxsite")
public class WebExSiteController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(WebExSiteController.class);

    @Autowired
    private WebExSiteService webExSiteService;

    @RequestMapping(value = "/draft/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public WbxSiteDraft4Get get(@PathVariable String id) {
        try {
            WebExSiteDraft draft = webExSiteService.findByDraftId(id);
            return convert2bean(draft);
        } catch (WafException e) {
            LOGGER.error("error occurred while retrieving BizScheme " + id, e);
            throw new ApiException(e.getKey());
        }
    }

    private WbxSiteDraft4Get convert2bean(WebExSiteDraft draft) {
        WbxSiteDraft4Get result = new WbxSiteDraft4Get();
        result.setId(draft.getId());
        result.setContractId(draft.getContractId());
        result.setLocation(draft.getLocation());
        result.setSiteName(draft.getSiteName());
        result.setPrimaryLanguage(draft.getPrimaryLanguage());
        result.setTimeZone(TimeZone.valueOf(draft.getTimeZone()).getValue());
        if (StringUtils.isNotBlank(draft.getAdditionalLanguage())) {
            result.setAdditionalLanguage(Arrays.asList(StringUtils.split(draft.getAdditionalLanguage(), ";")));
        }
        return result;
    }
    
    
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public WebExSite getSite(@PathVariable String id) {
        try {
            WebExSite draft = webExSiteService.findWebExSiteBySiteId(id);
            return draft;
        } catch (WafException e) {
            LOGGER.error("error occurred while retrieving BizScheme " + id, e);
            throw new ApiException(e.getKey());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
    public ResponseEntity<String> draft(@Valid @RequestBody WbxSiteDraft4Create bean, HttpServletRequest request) {
        try {
            WebExSitePrimaryFields sitePrimaryFields = new WebExSitePrimaryFields();
            sitePrimaryFields.setSiteName(bean.getSiteName());
            try {
                sitePrimaryFields.setLocation(Location.valueOf(bean.getLocation()));
            } catch (IllegalArgumentException e) {
                throw new InvalidLocationException();
            }
            if (bean.getTimeZone() != null) {
                sitePrimaryFields.setTimeZone(TimeZone.valueOf(bean.getTimeZone()));
            }
            LanguageMatrix languageMatrix = new LanguageMatrix();
            try {
                languageMatrix.setPrimaryLanguage(Language.valueOf(bean.getPrimaryLanguage()));
            } catch (IllegalArgumentException e) {
                throw new InvalidLanguageValueException();
            }
            if (bean.getAdditionalLanguages() != null) {
                for (String additionalLanguage : bean.getAdditionalLanguages()) {
                    try {
                        languageMatrix.enable(Language.valueOf(additionalLanguage));
                    } catch (IllegalArgumentException e) {
                        throw new InvalidLanguageValueException();
                    }
                }
            }
            sitePrimaryFields.setLanguages(languageMatrix);
            WebExSiteDraft site = webExSiteService.draftSite4Contract(bean.getContractId(), sitePrimaryFields);
            return APIUtil.createdResonse(request, site.getId());
        } catch (WafException e) {
            LOGGER.error("error occurred while creating WebEx Site ", e);
            throw new ApiException(e.getKey());
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

            webExSiteService.updateDraft(id, sitePrimaryFields);
        } catch (WafException e) {
            LOGGER.error("error occurred while updating Product " + id, e);
            throw new ApiException(e.getKey());
        }
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id, HttpServletRequest request) {
        try {
            webExSiteService.deleteUnusedSiteDraft(id);
        } catch (WafException e) {
            LOGGER.error("error occurred while deleting BizScheme " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/validate/{contractId}/{siteName}", method = RequestMethod.GET)
    public ResponseEntity<String> validateSiteName(@PathVariable String contractId, @PathVariable String siteName, HttpServletRequest request) {
        boolean available = webExSiteService.isSiteNameAvailable4Contract(siteName, contractId);
        if (available) {
            return ResponseEntity.accepted().body("");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("");
        }
    }


    @RequestMapping(value = "/candidates/{contractId}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> listSiteNamesAvailable4Contract(@PathVariable String contractId, HttpServletRequest request) {
        try {
            return webExSiteService.listSiteNamesAvailable4Contract(contractId);
        } catch (WafException e) {
            return new ArrayList<String>();
        }
    }


    @RequestMapping(value = "/query/{id}")
    @ResponseBody
    public String list(@PathVariable String id, HttpServletRequest request) throws SQLException {
        DataTableVo dt = this.parseData4DT(request);
        String contractId = id;

        String sortedColumnId = (String) request.getParameter("iSortCol_0");
        String sortedOrder = (String) request.getParameter("sSortDir_0");

        int curpage = 0;
        int totalSize = 0;
        if (dt.getiDisplayStart() > 0) {
            curpage = dt.getiDisplayStart() / dt.getiDisplayLength();
        }
        // logger.debug("list systemuser =" + saccount + "; sphone=" + sphone +
        // "; sname=" + sname); // "aphone=" + aphone + "; aacount=" + aacount
        // );

        SearchFilter searchFilter = new SearchFilter();
        curpage = curpage + 1;
        if (StringUtils.isNotBlank(contractId)) {
            searchFilter.setCustomerId(contractId);
        }


        String[] supportedOrderFields = new String[]{""};

        if (sortedColumnId != null && StringUtils.isNumeric(sortedColumnId)) {
            int orderIndex = Integer.parseInt(sortedColumnId);

            if (orderIndex < supportedOrderFields.length) {
                String orderFieldName = supportedOrderFields[orderIndex];
                if (orderFieldName != null) {
                    searchFilter.setOrderByField(orderFieldName);
                    if (sortedOrder != null
                            && sortedOrder.toLowerCase().equals(ORDER_DESC)) {
                        searchFilter.setOrderType(ORDER_DESC);
                    }
                }
            }
        }

        searchFilter.setPageSize(dt.getiDisplayLength());
        try {
            handleDataTable(dt, curpage, totalSize, searchFilter);
        } catch (Exception e) {
            LOGGER.error("list orgs failed", e);
            return FAIL;
        }
        // dt.setsEcho(String.valueOf(curpage+1));
        String json = this.formateData2DT(dt);
        return json;

    }

    public void handleDataTable(DataTableVo dt, int curpage, int totalSize,
                                SearchFilter searchorg) throws Exception {
        List<WebExSiteDraft> page = null;
        List<WebExSiteDraft> webExSiteDrafts = null;
        try {
            page = webExSiteService.listAllByPage(curpage, searchorg);
            webExSiteDrafts = page;
            totalSize = page.size();
        } catch (Exception e) {
            // logger.error("list Exception:"+e);
            throw new Exception("Error in list orgs!", e);
        }

        List<TableData> tds = new ArrayList<TableData>();
        for (WebExSiteDraft webExSiteDraft : webExSiteDrafts) {
            TableData td = new TableData();

            td.setPid(webExSiteDraft.getId());
            td.setSiteName(webExSiteDraft.getSiteName());
            td.setPrimaryLanguage(webExSiteDraft.getPrimaryLanguage());
            td.setAdditionalLanguage(webExSiteDraft.getAdditionalLanguage());
            td.setTimeZone(webExSiteDraft.getTimeZone());
            td.setCountryCode(webExSiteDraft.getCountryCode());
            td.setLocation(webExSiteDraft.getLocation());

            tds.add(td);
        }
        dt.setData(tds);
        dt.setiTotalRecords(totalSize);

        // logger.debug("completed table Handling");
    }

    private class TableData {

        private String pid = "b1";
        private String siteName = "b2";
        private String primaryLanguage = "b3";
        private String additionalLanguage = "b4";
        private String timeZone = "b5";
        private String countryCode = "b6";
        private String location = "b7";

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

        public String getPrimaryLanguage() {
            return primaryLanguage;
        }

        public void setPrimaryLanguage(String primaryLanguage) {
            this.primaryLanguage = primaryLanguage;
        }

        public String getAdditionalLanguage() {
            return additionalLanguage;
        }

        public void setAdditionalLanguage(String additionalLanguage) {
            this.additionalLanguage = additionalLanguage;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
