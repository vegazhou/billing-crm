package com.kt.repo.mysql.batch;

import com.kt.common.dbhelp.DbHelper;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Biz;
import com.kt.service.SearchFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/14.
 */
@Repository
public class BizRepositoryImpl implements BizRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(BizRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;

    @Override
    public Page<Biz> listAllTemplates(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT * FROM B_BIZ  WHERE IS_TEMPLATE = 1");
        if (StringUtils.isNotBlank(search.getDisplayName())) {
            sql.append(" AND lower(DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }
        if (StringUtils.isNotBlank(search.getType())) {
            sql.append(" AND TYPE = ?");
            params.add(StringUtils.upperCase(search.getType()));
        }
        if (StringUtils.isNotBlank(search.getState())) {
            sql.append(" AND STATE = ?");
            params.add(StringUtils.upperCase(search.getState()));
        }

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType());
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), Biz.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }


    private static final String SQL_LIST_UNUSED_INEFFECT_TEMPLATES =
            "SELECT * FROM b_biz b WHERE b.state = 'IN_EFFECT' AND is_template = 1 AND id NOT IN (SELECT biz_id FROM b_product);";


    @Override
    public List<Biz> listAllUnusedInEffectTemplates() {
        try {
            return dbHelper.getBeanList(SQL_LIST_UNUSED_INEFFECT_TEMPLATES, Biz.class);
        } catch (SQLException e) {
            LOGGER.error("BizRepositoryImpl.listAllUnusedInEffectTemplates error", e);
            return null;
        }
    }
}
