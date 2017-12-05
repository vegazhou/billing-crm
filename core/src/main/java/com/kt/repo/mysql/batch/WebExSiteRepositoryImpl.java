package com.kt.repo.mysql.batch;

import com.kt.biz.bean.WebExRequestRecord;
import com.kt.common.dbhelp.DbHelper;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.WebExSiteDraft;
import com.kt.entity.mysql.crm.WebExSiteDraftReport;
import com.kt.service.SearchFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
@Repository
public class WebExSiteRepositoryImpl implements WebExSiteRepositoryCustom {

    @Autowired
    private DbHelper dbHelper;

    private static final Logger LOGGER = Logger.getLogger(WebExSiteRepositoryImpl.class);

    private static final String SQL_LIST_SITE_CANDIDATES =
            "select b_webex_sites.SITENAME from b_customer, b_contract, b_webex_sites " +
                    "where b_webex_sites.CUSTOMER_ID = b_customer.PID and b_customer.pid = b_contract.CUSTOMER_ID and b_contract.pid = ? " +
                    "union " +
                    "select b_webex_site_draft.SITENAME from b_webex_site_draft where CONTRACT_ID = ?";


    @Override
    public List<String> findAvailableSiteNames4Contract(String contractId) {
        try {
            List<Bean> d = dbHelper.getBeanList(SQL_LIST_SITE_CANDIDATES, Bean.class, contractId, contractId);
            return covert2string(d);
        } catch (SQLException e) {
            LOGGER.error("WebExSiteRepositoryImpl.findAvailableSiteNames4Contract error", e);
            return null;
        }
    }


    private List<String> covert2string(List<Bean> beans) {
        List<String> result = new ArrayList<>();
        if (beans != null && beans.size() > 0) {
            for (Bean bean : beans) {
                result.add(bean.getSiteName());
            }
        }
        return result;
    }


    public static class Bean {
        private String siteName;

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }
    }

    @Override
    public List<WebExSiteDraft> listAllByPage(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM B_WEBEX_SITE_DRAFT  WHERE id !=' ' ");
        if (StringUtils.isNotBlank(search.getCustomerId())) {
            sql.append(" and CONTRACT_ID = '").append(search.getCustomerId()).append("'");
        }


        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            List<WebExSiteDraft> results = dbHelper.getBeanList(sql.toString(), WebExSiteDraft.class);
//            Page page = dbHelper.getPage(sql.toString(), WebExSiteDraft.class,
//                    curPage, search.getPageSize());
            return results;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }


    private static final String SQL_PAGINATE_ALL_SITES = "select s.SITENAME, c.DISPLAY_NAME as CUSTOMER_NAME " +
            "from b_webex_sites s " +
            "inner join b_customer c on s.CUSTOMER_ID = c.PID";

    @Override
    public Page<WebExSiteDraftReport> paginateAllSites(int curPage, SearchFilter search) {
        ArrayList<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder(SQL_PAGINATE_ALL_SITES);
        try {
            if (StringUtils.isNotBlank(search.getDisplayName())) {
                sql.append(" AND lower(s.SITENAME) like ?");
                params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
            }

            if (StringUtils.isNotBlank(search.getCustomerId())) {
                sql.append(" AND lower(c.DISPLAY_NAME) like ?");
                params.add("%" + StringUtils.lowerCase(search.getCustomerId()) + "%");
            }

            Page page = dbHelper.getPage(SQL_PAGINATE_ALL_SITES, WebExSiteDraftReport.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred WebExSiteRepositoryImpl", e);
            return null;
        }
    }

    @Override
    public Page<WebExSiteDraftReport> listAllByPageForReport(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("select t1.*, t2.display_name as RESELLER from "+
        "(select  R.SITE_ID,s.SITENAME, o.EFFECTIVEENDDATE, o.EFFECTIVESTARTDATE, "+
		"p.DISPLAY_NAME as PRODUCT_NAME, c.DISPLAY_NAME as CUSTOMER_NAME, o.CONTRACT_ID as CONTRACT_ID,q.agent_id,q.display_name as CONTRACT_NAME "+
		"from b_site_order_relations r "+
		"inner join b_order o on r.ORDER_ID = o.PID "+
		"inner join b_product p on o.PRODUCT_ID = p.PID "+
		"inner join b_webex_sites s on r.SITE_ID = s.PID "+
		"inner join B_CONTRACT q on o.CONTRACT_ID  = q.PID "+
		"inner join b_customer c on o.CUSTOMER_ID = c.PID ");


        if (StringUtils.isNotBlank(search.getDisplayName())) {
            sql.append(" AND lower(c.DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }
        
        if (StringUtils.isNotBlank(search.getCustomerId())) {
        	sql.append(" and s.pid ='").append(search.getCustomerId()).append("'");
        }
        sql.append(") t1 left join b_customer t2 on t1.agent_id = t2.pid ");
        
        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            
            Page page = dbHelper.getPage(sql.toString(), WebExSiteDraftReport.class,
                    curPage, search.getPageSize(), params.toArray());
//            Page page = dbHelper.getPage(sql.toString(), WebExSiteDraft.class,
//                    curPage, search.getPageSize());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }
    
    
    @Override
    public Page<WebExSiteDraftReport> listAllSiteByPageForReport(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT s.PID as site_id, s.sitename,c.pid as CUSTOMER_ID, c.DISPLAY_NAME as CUSTOMER_NAME, s.STATE  " +
                "FROM B_WEBEX_SITES s INNER JOIN B_CUSTOMER c ON s.CUSTOMER_ID = c.PID WHERE s.pid !=' ' ");
		


        if (StringUtils.isNotBlank(search.getDisplayName())) {
            sql.append(" AND lower(s.SITENAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }

        if (StringUtils.isNotBlank(search.getCustomerName())) {
            sql.append(" AND lower(c.DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getCustomerName()) + "%");
        }
        
      

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            
            Page page = dbHelper.getPage(sql.toString(), WebExSiteDraftReport.class,
                    curPage, search.getPageSize(), params.toArray());
//            Page page = dbHelper.getPage(sql.toString(), WebExSiteDraft.class,
//                    curPage, search.getPageSize());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }



    private static final String SQL_WEBEX_REQUESTS = "SELECT * FROM B_WBX_PROVISION_TASK";

    @Override
    public Page<WebExRequestRecord> paginateWebExRequests(int curPage, SearchFilter search) {
        try {
            Page page = dbHelper.getPage(SQL_WEBEX_REQUESTS.toString(), WebExRequestRecord.class,
                    curPage, search.getPageSize());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }
}
