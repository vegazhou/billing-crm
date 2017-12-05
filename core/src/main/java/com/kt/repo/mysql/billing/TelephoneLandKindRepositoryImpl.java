package com.kt.repo.mysql.billing;

import com.kt.common.dbhelp.DbHelper;
import com.kt.entity.mysql.billing.TelephoneLandKindEntity;
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
public class TelephoneLandKindRepositoryImpl implements TelephoneLandKindRepositoryCustom {

    private static final Log LOGGER = LogFactory.getLog(TelephoneLandKindRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;
    public List<TelephoneLandKindEntity> getTelephoneLandKind(Date startDate, Date endDate){
        String querySQL = "select * from TELEPHONE_LAND_KIND_T where valid=1 and end_Date >= to_date(?, 'yyyy-mm-dd HH24:mi:ss') and begin_Date <= to_date(?, 'yyyy-mm-dd HH24:mi:ss') ";
        List<Object> params = new ArrayList<Object>();
        params.add(DateUtils.format2DefaultFullTime(startDate));
        params.add(DateUtils.format2DefaultFullTime(endDate));
        try {
            List<TelephoneLandKindEntity> entities = dbHelper.getBeanList(querySQL, TelephoneLandKindEntity.class, params.toArray());
            return entities;
        } catch (SQLException e) {
            LOGGER.error("dbHelper.execute() error:", e);
        }
        return null;
    }
}
