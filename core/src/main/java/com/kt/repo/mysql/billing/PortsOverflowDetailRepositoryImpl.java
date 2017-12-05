package com.kt.repo.mysql.billing;

import com.kt.biz.bean.KeyValueBean;
import com.kt.biz.bean.PortsUsageBean;
import com.kt.common.dbhelp.DbHelper;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.FormalBillTuneLog;
import com.kt.entity.mysql.billing.PortsOverflowDetail;
import com.kt.entity.mysql.billing.TelephoneCallinNumberEntity;
import com.kt.util.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
public class PortsOverflowDetailRepositoryImpl implements PortsOverflowDetailRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(PortsOverflowDetailRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;

    public List<PortsUsageBean> getSitePortsSetting(Date startDate, Date endDate){
        String sql = "select to_date(o.EFFECTIVESTARTDATE, 'yyyy-mm-dd hh24:mi:ss') as EFFECTIVESTARTDATE, to_date(o.effectiveenddate, 'yyyy-mm-dd hh24:mi:ss') as effectiveenddate, b.display_name as bizName, p.display_name as productName, c.code as customerCode, o.customer_id as customerId, o.pid as orderId, c.display_name as customerName, b.type as serviceType, csa.attribute_value as orderPortsAmount, ws.sitename as siteName \n" +
                "from b_order o \n" +
                "inner join b_customer c on c.pid = o.customer_id \n" +
                "inner join b_charge_scheme cs on cs.id = o.charge_id and cs.type='MONTHLY_PAY_BY_PORTS' and cs.STATE = 'IN_EFFECT'\n" +
                "inner join b_CHARGE_scheme_attributes csa on csa.scheme_id=cs.id and csa.attribute_name = 'PORTS_AMOUNT' and csa.attribute_value > 0 \n" +
                "inner join b_site_order_relations sor on sor.order_id = o.pid \n" +
                "inner join b_webex_sites ws on ws.pid = sor.SITE_ID \n" +
                "inner join b_biz b on b.id = o.biz_id and b.state = 'IN_EFFECT' \n" +
                "inner join b_product p on p.pid = o.product_id and p.state = 'IN_EFFECT' \n" +

                "where o.state='IN_EFFECT' and to_date(o.EFFECTIVESTARTDATE, 'yyyy-mm-dd hh24:mi:ss') < to_date(?, 'yyyy-mm-dd hh24:mi:ss') and to_date(o.EFFECTIVEENDDATE, 'yyyy-mm-dd hh24:mi:ss') > to_date(?, 'yyyy-mm-dd hh24:mi:ss') \n";

        List<Object> params = new ArrayList<Object>();
        params.add(DateUtils.format2DefaultFullTime(endDate));
        params.add(DateUtils.format2DefaultFullTime(startDate));
        try {
            List<PortsUsageBean> entities = dbHelper.getBeanList(sql, PortsUsageBean.class, params.toArray());
            return entities;
        } catch (SQLException e) {
            LOGGER.error("dbHelper.execute() error:", e);
        }
        return null;
    }
}
