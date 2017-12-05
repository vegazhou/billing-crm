package wang.huaichao.data.repo;

import com.kt.common.dbhelp.DbHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import wang.huaichao.data.entity.crm.MeetingRecord;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 8/30/2016.
 */
@Repository
public class MeetingRecordRepositoryImpl implements MeetingRecordRepositoryCustom {
    @Autowired
    @Qualifier("crmJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    DbHelper dbHelper;

    @Override
    public void deleteByBillingPeriodAndSiteNameIn(int billingPeriod, List<String> siteNames) {
        if (siteNames == null || siteNames.size() == 0) return;

        String sql = "delete from meeting_records where billing_period=:billingPeriod and site_name in (:siteNames)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("billingPeriod", billingPeriod, Types.INTEGER);
        params.addValue("siteNames", siteNames);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteByBillingPeriodAndCustomerIdAndSiteNameIn(int billingPeriod, String customerId, List<String> siteNames) {
        if (siteNames == null || siteNames.size() == 0) return;

        String sql = "delete from meeting_records where billing_period=:billingPeriod and customer_id=:customerId and site_name in (:siteNames)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("billingPeriod", billingPeriod, Types.INTEGER);
        params.addValue("siteNames", siteNames);
        params.addValue("customerId", customerId);

        jdbcTemplate.update(sql, params);
    }


    @Override
    public List<MeetingRecord> findByCustomerIdAndBillingPeriodJoinPstnCode(String customerId, int billingPeriod) {
        String sql = "select a.*, b.PSTN_CODE from meeting_records a inner join  PSTN_CODE b on a.access_type = b.PSTN_TYPE where a.billing_period=:billingPeriod and a.customer_id=:customerId order by a.id asc";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("billingPeriod", billingPeriod, Types.INTEGER);
        params.addValue("customerId", customerId);

        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper(MeetingRecord.class));
    }


    @Override
    public List<MeetingRecord> findByCustomerIdAndBillingPeriodJoinPstnCode(int billingPeriod, List<String> orderIds) {
        List<String> quotedOrderIds = new ArrayList<>(orderIds.size());
        for (String orderId : orderIds) {
            quotedOrderIds.add("'" + orderId + "'");
        }
        String orderIdsCondition = "(" + StringUtils.join(quotedOrderIds, ",") + ")";

        StringBuilder sql = new StringBuilder();
        sql.append("select a.*, b.PSTN_CODE from meeting_records a left join  PSTN_CODE b on a.access_type = b.PSTN_TYPE where a.billing_period = ? and a.order_id in ");
        sql.append(orderIdsCondition);
        sql.append(" order by a.id asc");

        try {
            return dbHelper.getBeanList(sql.toString(), MeetingRecord.class, billingPeriod);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MeetingRecord> findByCustomerIdsAndBillingPeriodJoinPstnCode(
        List<String> customerIds, int billingPeriod) {

        List<String> quotedOrderIds = new ArrayList<>();
        for (String orderId : customerIds) {
            quotedOrderIds.add(orderId);
        }
        String orderIdsCondition = "('" + StringUtils.join(quotedOrderIds, "','") + "')";

        StringBuilder sql = new StringBuilder();
        sql.append("select a.*, b.PSTN_CODE from meeting_records a " +
            "inner join  PSTN_CODE b on a.access_type = b.PSTN_TYPE " +
            "where a.billing_period = ? and a.customer_id in ");
        sql.append(orderIdsCondition);
        sql.append(" order by a.customer_id,a.id asc");

        try {
            return dbHelper.getBeanList(sql.toString(), MeetingRecord.class, billingPeriod);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MeetingRecord> findByBillingPeriodAndOrderIds(int billingPeriod, List<String> orderIds) {
        List<String> quotedOrderIds = new ArrayList<>(orderIds.size());
        for (String orderId : orderIds) {
            quotedOrderIds.add("'" + orderId + "'");
        }
        String orderIdsCondition = "(" + StringUtils.join(quotedOrderIds, ",") + ")";

        StringBuilder sql = new StringBuilder();
        sql.append("select a.* from meeting_records a where a.billing_period = ? and a.order_id in ");
        sql.append(orderIdsCondition);
        sql.append(" order by a.id asc");

        try {
            return dbHelper.getBeanList(sql.toString(), MeetingRecord.class, billingPeriod);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
