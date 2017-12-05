package com.kt.api.controller;

import com.kt.api.bean.charge.ChargeScheme4Create;
import com.kt.api.common.APIConstants;
import com.kt.api.common.APIUtil;
import com.kt.api.controller.datatable.DataTableVo;
import com.kt.biz.bean.GenericChargeSchemeBean;
import com.kt.biz.model.IScheme;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.types.BizType;
import com.kt.biz.types.ChargeType;
import com.kt.common.dbhelp.page.Page;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.ChargeScheme;
import com.kt.exception.WafException;
import com.kt.service.BizSchemeService;
import com.kt.service.ChargeSchemeService;
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
 * Created by Vega Zhou on 2016/3/12.
 */
@Controller
@RequestMapping("/charges")
public class ChargeSchemeController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(ChargeSchemeController.class);

    @Autowired
    private ChargeSchemeService chargeSchemeService;

    @Autowired
    private BizSchemeService bizSchemeService;


    @RequestMapping(value = "", method = RequestMethod.POST, consumes = APIConstants.MEDIATYPE)
    public ResponseEntity<String> addChargeScheme(@Valid @RequestBody ChargeScheme4Create bean, HttpServletRequest request) {
        try {
            IScheme scheme = chargeSchemeService.createChargeScheme(bean.getDisplayName(), ChargeType.valueOf(bean.getChargeType()));
            return APIUtil.createdResonse(request, scheme.getId());
        } catch (WafException e) {
            LOGGER.error("error occurred while creating ChargeScheme ", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String get(@PathVariable String id) {
        try {
            IScheme scheme = chargeSchemeService.findChargeSchemeById(id);
            return scheme.saveAsJson().toString();
        } catch (WafException e) {
            LOGGER.error("error occurred while retrieving ChargeScheme " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id, HttpServletRequest request) {
        try {
            chargeSchemeService.deleteChargeScheme(id);
        } catch (WafException e) {
            LOGGER.error("error occurred while deleting ChargeScheme " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@RequestBody GenericChargeSchemeBean input,
                                         @PathVariable String id,
                                         HttpServletRequest request) {
        try {
            chargeSchemeService.updateChargeScheme(id, input);
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred when updating ChargeScheme", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/send/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> send(@PathVariable String id,
                                         HttpServletRequest request) {
        try {
            chargeSchemeService.sendChargeScheme4Approval(id);
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred when send ChargeScheme for approval", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/withdraw/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> withdraw(@PathVariable String id,
                                       HttpServletRequest request) {
        try {
            chargeSchemeService.withdraw(id);
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred when withdrawing ChargeScheme", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/approve/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> approve(@PathVariable String id,
                                           HttpServletRequest request) {
        try {
            chargeSchemeService.approve(id);
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred when approving ChargeScheme", e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/decline/{id}", method = RequestMethod.GET, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> decline(@PathVariable String id,
                                          HttpServletRequest request) {
        try {
            chargeSchemeService.decline(id);
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        } catch (WafException e) {
            LOGGER.error("error occurred when declining ChargeScheme", e);
            throw new ApiException(e.getKey());
        }
    }


    /**
     * this API is used by page to list all unused charge scheme templates of the specified biz for creating products
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/listallchargeforbiz/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ChargeScheme> listAllChargeForBiz(@PathVariable String id) {
        try {
            AbstractBizScheme scheme = bizSchemeService.findBizSchemeById(id);
            BizType bizType = scheme.getType();
            return chargeSchemeService.listAvailableTemplates4Biz(scheme.getEntity(), bizType);
        } catch (WafException e) {
            LOGGER.error("error occurred while retrieving ChargeScheme " + id, e);
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
        final String[] SUPPORTED_SORTING_FIELDS = new String[]{"DISPLAY_NAME", "CHARGE_TYPE", "STATE", "CREATED_TIME"};

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
            listChargeSchemeTemplates(dt, searchFilter);
        } catch (Exception e) {
            return FAIL;
        }
        String json = this.formateData2DT(dt);
        return json;
    }


    private void listChargeSchemeTemplates(DataTableVo dt, SearchFilter searchFilter) throws Exception {
        Page<ChargeScheme> page;
        List<ChargeScheme> chargeSchemes;
        int totalSize;
        try {
            page = chargeSchemeService.listAllTemplates(dt.getCurrentPageNumber(), searchFilter);
            chargeSchemes = page.getData();
            totalSize = page.getRecordCount();
        } catch (Exception e) {
            //logger.error("list Exception:"+e);
            throw new Exception("Error in list systemuser!", e);
        }


        if (chargeSchemes != null) {
            List<TableRow> tds = new ArrayList<TableRow>();
            for (int i = 0, n = chargeSchemes.size(); i < dt.getiDisplayLength() && i < n; i++) {
                ChargeScheme chargeScheme = chargeSchemes.get(i);

                TableRow td = new TableRow();


                td.setPid(chargeScheme.getId());
                td.setType(chargeScheme.getType());
                td.setDisplayName(chargeScheme.getDisplayName());
                td.setIstemplate(chargeScheme.getIsTemplate());
                td.setStatus(chargeScheme.getState());
                td.setCreatedTime(chargeScheme.getCreatedTime());

                tds.add(td);
            }
            dt.setData(tds);
            dt.setiTotalRecords(totalSize);
        }
    }


    private class TableRow {
        private String pid = "b1";
        private String displayName = "b2";
        private String type = "b3";
        private int istemplate = 1;
        private String status = "b5";
        private String createdTime;

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
