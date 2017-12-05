package com.kt.repo.mysql.batch;

import com.kt.biz.model.util.BizChargeCompatibility;
import com.kt.biz.types.BizType;
import com.kt.common.dbhelp.DbHelper;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Biz;
import com.kt.entity.mysql.crm.ChargeScheme;
import com.kt.service.SearchFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/14.
 */
@Repository
public class ChargeSchemeRepositoryImpl implements ChargeSchemeRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(BizRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;

    @Override
    public Page<ChargeScheme> listAllTemplates(int curPage, SearchFilter search) {
        StringBuilder sql = new StringBuilder();
        ArrayList<Object> params = new ArrayList<>();
        sql.append("SELECT * FROM B_CHARGE_SCHEME  WHERE IS_TEMPLATE = 1");
        if (StringUtils.isNotBlank(search.getDisplayName())) {
            sql.append(" AND lower(DISPLAY_NAME) like ?");
            params.add("%" + StringUtils.lowerCase(search.getDisplayName()) + "%");
        }
        if (StringUtils.isNotBlank(search.getType())) {
            sql.append(" AND CHARGE_TYPE = ?");
            params.add(StringUtils.upperCase(search.getType()));
        }
        if (StringUtils.isNotBlank(search.getState())) {
            sql.append(" AND STATE = ?");
            params.add(StringUtils.upperCase(search.getState()));
        }

        if (search.getOrderByField() != null && search.getOrderByField().length() > 0
                && search.getOrderType() != null && search.getOrderType().length() > 0) {
            sql.append(" ORDER BY ").append(search.getOrderByField())
                    .append(" ").append(search.getOrderType()).append( ",DISPLAY_NAME desc");
        }
        try {
            Page page = dbHelper.getPage(sql.toString(), ChargeScheme.class,
                    curPage, search.getPageSize(), params.toArray());
            return page;
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return null;
        }
    }

    private static final String SQL_LIST_UNUSED_TEMPLATES =
            "SELECT * FROM b_charge_scheme c WHERE state = 'IN_EFFECT' AND id in (SELECT charge_scheme_id FROM b_product where biz_id = ?)" +
                    " union " +
                    "SELECT * FROM b_charge_scheme c WHERE state = 'IN_EFFECT' AND is_template = 1 AND id NOT IN (SELECT charge_scheme_id FROM b_product)";

    @Override
    public List<ChargeScheme> listCandidateTemplates4Biz(Biz biz) {
        List<String> types = BizChargeCompatibility.getAvailableChargeTypesAsStringList(BizType.valueOf(biz.getType()));
        List<Object> params = new LinkedList<>();
        params.add(biz.getId());
        params.addAll(types);
        try {
            return dbHelper.getBeanList(SQL_LIST_UNUSED_TEMPLATES + buildInClause(types), ChargeScheme.class, params.toArray());
        } catch (SQLException e) {
            LOGGER.error("ChargeSchemeRepositoryImpl.listUnusedTemplates error", e);
            return null;
        }
    }

    private String buildInClause(List<String> types) {
        StringBuilder builder = new StringBuilder();
        builder.append(" AND type IN (''");
        for (String type : types) {
            builder.append(", ?");
        }
        builder.append(")");
        return builder.toString();
    }
}
