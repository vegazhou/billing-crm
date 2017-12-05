package com.kt.repo.mysql.billing;

import com.kt.common.dbhelp.DbHelper;
import com.kt.entity.mysql.billing.TelephoneTollMappingEntity;
import com.kt.repo.edr.bean.WbxSite;
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
 * Created by Administrator on 2016/9/7.
 */
@Repository
public class TelephoneTollMappingRepositoryImpl implements TelephoneTollMappingRepositoryCustom {
    private static final Log LOGGER = LogFactory.getLog(TelephoneTollMappingRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;
    public List<TelephoneTollMappingEntity> getTelephoneTollMapping(Date startDate, Date endDate){
        String querySQL = "select e.begin_date, e.end_date, e.real_number, e.access_number, case when c.code is null then '1' else c.code end as code \n" +
                "from TELEPHONE_400_EXCHANGE_T e left join TELEPHONE_CALLIN_NUMBER_T c on c.access_number = e.access_number and c.valid=1 and c.end_Date >= to_date(?, 'yyyy-mm-dd HH24:mi:ss') and c.begin_Date <= to_date(?, 'yyyy-mm-dd HH24:mi:ss') \n" +
                "where e.valid=1 and e.end_Date >= to_date(?, 'yyyy-mm-dd HH24:mi:ss') and e.begin_Date <= to_date(?, 'yyyy-mm-dd HH24:mi:ss') ";
        List<Object> params = new ArrayList<Object>();
        params.add(DateUtils.format2DefaultFullTime(startDate));
        params.add(DateUtils.format2DefaultFullTime(endDate));
        params.add(DateUtils.format2DefaultFullTime(startDate));
        params.add(DateUtils.format2DefaultFullTime(endDate));
        try {
            List<TelephoneTollMappingEntity> entities = dbHelper.getBeanList(querySQL, TelephoneTollMappingEntity.class, params.toArray());
            return entities;
        } catch (SQLException e) {
            LOGGER.error("dbHelper.execute() error:", e);
        }
        return null;
    }

    /*

    select a.*
from B_CHARGE_SCHEME_ATTRIBUTES a
inner join B_CHARGE_SCHEME c on a.SCHEME_ID = c.ID and c.state='IN_EFFECT' and c.type='PSTN_STANDARD_CHARGE'
inner join b_order o on o.charge_id = c.id and o.customer_id = '4621' and o.state='IN_EFFECT' and to_date(o.effectivestartdate, 'yyyy-mm-dd hh24:mi:ss') < trunc(sysdate, 'MONTH')
inner join b_product p on p.pid = o.product_id inner join b_biz b on p.biz_id = b.id and b.type = 'WEBEX_PSTN' and b.STATE='IN_EFFECT';

where c.type='PSTN_RATES_ID';

     */
}
