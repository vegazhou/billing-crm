package com.kt.api.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kt.api.bean.biz.BizScheme4Create;
import com.kt.api.common.APIConstants;
import com.kt.api.common.APIUtil;

import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.model.IScheme;
import com.kt.biz.types.BizType;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.Biz;
import com.kt.exception.InvalidJsonObjectException;
import com.kt.exception.WafException;
import com.kt.service.BizSchemeService;
import com.kt.service.SearchFilter;
import com.kt.session.PrincipalContext;
import com.kt.util.DateUtil;
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
 * Created by Vega Zhou on 2016/3/11.
 */
@Controller
@RequestMapping("/biz")
public class BizSchemeController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(BizSchemeController.class);


    @Autowired
    private BizSchemeService bizSchemeService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
    public ResponseEntity<String> add(@Valid @RequestBody BizScheme4Create bean, HttpServletRequest request) {
        try {
            IScheme scheme = bizSchemeService.createBizScheme(bean.getDisplayName(), BizType.valueOf(bean.getBizType()));
            scheme.setCreatedBy(PrincipalContext.getCurrentUserName());
            scheme.setCreatedTime(DateUtil.now());
            scheme.save();
            return APIUtil.createdResonse(request, scheme.getId());
        } catch (WafException e) {
            LOGGER.error("error occurred while create BizScheme ", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String get(@PathVariable String id) {
        try {
            IScheme scheme = bizSchemeService.findBizSchemeById(id);
            return scheme.saveAsJson().toString();
        } catch (WafException e) {
            LOGGER.error("error occurred while retrieving BizScheme " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/send/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> send(@PathVariable String id,
                                       HttpServletRequest request) {
        try {
            bizSchemeService.sendBizScheme4Approval(id);
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred when sending BizScheme for approval", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/withdraw/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> withdraw(@PathVariable String id,
                                           HttpServletRequest request) {
        try {
            bizSchemeService.withdraw(id);
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred when withdrawing BizScheme", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/approve/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> approve(@PathVariable String id,
                                           HttpServletRequest request) {
        try {
            bizSchemeService.approve(id);
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred when approving BizScheme", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/decline/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> decline(@PathVariable String id,
                                           HttpServletRequest request) {
        try {
            bizSchemeService.decline(id);
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred when declining BizScheme", e);
            throw new ApiException(e.getKey());
        }
    }


    /**
     * This API is used by page to list all biz templates available for creating products
     * @param request
     * @return
     */
    @RequestMapping(value = "/listtemplates", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Biz> listBizTemplates(HttpServletRequest request) {
        return bizSchemeService.findAllInEffectTemplates();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id, HttpServletRequest request) {
        try {
            bizSchemeService.deleteBizScheme(id);
        } catch (WafException e) {
            LOGGER.error("error occurred while deleting BizScheme " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@RequestBody String input,
                                         @PathVariable String id,
                                         HttpServletRequest request) {
        try {
            JsonObject json = parse(input);
            bizSchemeService.updateBizScheme(id, json);
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred while updating BizScheme " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/queryTemplates")
    @ResponseBody
    public String listAllTemplates(HttpServletRequest request) {
        DataTableVo dt = this.parseData4DT(request);
        String displayNameFilter = request.getParameter("s_sname");
        String typeFilter = request.getParameter("s_type");
        String stateFilter = request.getParameter("s_state");
        String sortedColumnId = request.getParameter("iSortCol_0");
        String sortedOrder = request.getParameter("sSortDir_0");

        SearchFilter searchFilter = new SearchFilter();
        if (StringUtils.isNotBlank(displayNameFilter)) {
            searchFilter.setDisplayName(displayNameFilter);
        }
        if (StringUtils.isNotBlank(typeFilter)) {
            searchFilter.setType(typeFilter);
        }
        if (StringUtils.isNotBlank(stateFilter)) {
            searchFilter.setState(stateFilter);
        }
        final String[] SUPPORTED_SORTING_FIELDS = new String[]{"DISPLAY_NAME", "TYPE", "STATE", "CREATED_TIME"};

        if (sortedColumnId != null && StringUtils.isNumeric(sortedColumnId)) {
            int orderIndex = Integer.parseInt(sortedColumnId);

            if (orderIndex < SUPPORTED_SORTING_FIELDS.length) {
                String orderFieldName = SUPPORTED_SORTING_FIELDS[orderIndex];
                if (orderFieldName != null) {
                    searchFilter.setOrderByField(orderFieldName);
                    if (sortedOrder != null && sortedOrder.toLowerCase().equals(ORDER_DESC)) {
                        searchFilter.setOrderType(ORDER_DESC);
                    } else {
                        searchFilter.setOrderType(ORDER_ASC);
                    }
                }
            }
        }
        searchFilter.setPageSize(dt.getiDisplayLength());


        try {
            listBizSchemeTemplates(dt, searchFilter);
        } catch (Exception e) {
            return FAIL;
        }
        String json = this.formateData2DT(dt);
        return json;
    }


    private void listBizSchemeTemplates(DataTableVo dt, SearchFilter searchFilter) throws Exception {
        Page<Biz> page;
        List<Biz> bizs;
        int totalSize;
        try {
            page = bizSchemeService.listAllTemplates(dt.getCurrentPageNumber(), searchFilter);
            bizs = page.getData();
            totalSize = page.getRecordCount();
        } catch (Exception e) {
            //logger.error("list Exception:"+e);
            throw new Exception("Error in list systemuser!", e);
        }


        if (bizs != null) {
            List<TableRow> tds = new ArrayList<TableRow>();
            for (int i = 0, n = bizs.size(); i < dt.getiDisplayLength() && i < n; i++) {
                Biz biz = bizs.get(i);
                TableRow td = new TableRow();


                td.setPid(biz.getId());
                td.setType(biz.getType());
                td.setDisplayName(biz.getDisplayName());
                td.setIstemplate(biz.getIsTemplate());
                td.setStatus(biz.getState());
                td.setCreatedTime(biz.getCreatedTime());

                tds.add(td);
            }
            dt.setData(tds);
            dt.setiTotalRecords(totalSize);
        }
    }


    private JsonObject parse(String input) throws InvalidJsonObjectException {
        try {
            JsonParser parser = new JsonParser();
            return (JsonObject) parser.parse(input);
        } catch (Exception e) {
            throw new InvalidJsonObjectException();
        }
    }


    private class TableRow {
        private String pid = "b1";
        private String displayName = "b2";
        private String type = "b3";
        private int istemplate = 1;
        private String status = "b5";
        private String createdTime = "";

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIstemplate() {
            return istemplate;
        }

        public void setIstemplate(int istemplate) {
            this.istemplate = istemplate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }
    }
}
