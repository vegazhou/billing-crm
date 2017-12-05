package com.kt.repo.mysql.billing;

import com.kt.common.dbhelp.DbHelper;
import com.kt.entity.mysql.crm.ChargeSchemeAttribute;
import com.kt.entity.mysql.crm.Customer;
import com.kt.entity.mysql.crm.Rate;
import com.kt.util.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
@Repository
public class TelephoneCommonRepository {

    private static final Log LOGGER = LogFactory.getLog(TelephoneCommonRepository.class);

    @Autowired
    private DbHelper dbHelper;

    public Customer getCustomerBySiteName(String siteName){
        String sql = "select c.* from B_CUSTOMER c inner join B_WEBEX_SITES w on w.SITENAME=? and c.pid = w.CUSTOMER_ID and w.state='IN_EFFECT'";

        List<Object> params = new ArrayList<Object>();
        params.add(siteName);

        try {
            Customer customer = dbHelper.getBean(sql, Customer.class, params.toArray());
            return customer;
        } catch (SQLException e) {
            LOGGER.error("dbHelper.execute() error:", e);
        }
        return null;
    }

    public List<String> getSiteNamesByCustomerId(String customerId){
        String sql = "select w.sitename as name, w.pid as value from B_CUSTOMER c inner join B_WEBEX_SITES w on c.pid = w.CUSTOMER_ID and w.state='IN_EFFECT' where c.pid = ?";

        List<Object> params = new ArrayList<Object>();
        params.add(customerId);

        try {
            List<ChargeSchemeAttribute> chargeSchemeAttributes = dbHelper.getBeanList(sql, ChargeSchemeAttribute.class, params.toArray());
            List<String> siteNames = new ArrayList<String>();
            for(ChargeSchemeAttribute s: chargeSchemeAttributes){
                siteNames.add(s.getName());
            };
            return siteNames;
        } catch (SQLException e) {
            LOGGER.error("dbHelper.execute() error:", e);
        }
        return null;
    }

    public List<ChargeSchemeAttribute> getStandardPSTNRateAttribute(String customerId, Date startDate, Date endDate){ //customerId , 4621,
        String sql = "select a.SCHEME_ID as entityId, a.ATTRIBUTE_NAME as name, a.ATTRIBUTE_VALUE as value \n" +
                "from B_CHARGE_SCHEME_ATTRIBUTES a \n" +
                "inner join B_CHARGE_SCHEME c on a.SCHEME_ID = c.ID and c.state='IN_EFFECT' and c.type='PSTN_STANDARD_CHARGE' \n" +
                "inner join b_order o on o.charge_id = c.id and o.customer_id = ? and o.state='IN_EFFECT'" +
                "and to_date(o.effectiveenddate, 'yyyy-mm-dd hh24:mi:ss') >= to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n" +
                "and to_date(o.effectivestartdate, 'yyyy-mm-dd hh24:mi:ss') <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')  \n" +
                "inner join b_product p on p.pid = o.product_id inner join b_biz b on p.biz_id = b.id and b.type = 'WEBEX_PSTN' and b.STATE='IN_EFFECT' \n";

        String strStartTime = DateUtils.format2DefaultFullTime(startDate);
        String strEndTime = DateUtils.format2DefaultFullTime(endDate);
        List<Object> params = new ArrayList<Object>();
        params.add(customerId);
        params.add(strStartTime);
        params.add(strEndTime);

        try {
            List<ChargeSchemeAttribute> entities = dbHelper.getBeanList(sql, ChargeSchemeAttribute.class, params.toArray());
            return entities;
        } catch (SQLException e) {
            LOGGER.error("dbHelper.execute() error:", e);
        }
        return null;
    }

    public List<Rate> getPSTNDetailRates(String rateId){ //customerId , 4621,
        String sql = " select * from b_rate where pid=? \n";
        List<Object> params = new ArrayList<Object>();
        params.add(rateId);

        try {
            List<Rate> entities = dbHelper.getBeanList(sql, Rate.class, params.toArray());
            return entities;
        } catch (SQLException e) {
            LOGGER.error("dbHelper.execute() error:", e);
        }
        return null;
    }
}
