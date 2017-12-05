package com.kt.repo.mysql.batch;

import com.kt.biz.bean.PstnRateBean;
import com.kt.common.dbhelp.DbHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Vega Zhou on 2016/9/6.
 */
@Repository
public class RateRepositoryImpl implements RateRepositoryCustom {

    @Autowired
    private DbHelper dbHelper;

    @Override
    public List<String> listAllPids() {

        try {
            List<Map<String, String>> results = dbHelper.getMapList("SELECT DISTINCT PID FROM B_RATE");
            List<String> pids = new ArrayList<>(results.size());
            for (Map<String, String> result : results) {
                pids.add(result.get("PID"));
            }
            return pids;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


//    private static final String LIST_DETAILED_RATES =
//            "SELECT T1.pid, T1.DISPLAY_NAME, T1.CODE, T1.RATE, T1.SERVICE_RATE, T1.COUNTRY, T2.RATE AS LIST_PRICE_RATE, T2.SERVICE_RATE AS LIST_PRICE_SERVICE_RATE FROM " +
//            "(select * from b_rate where pid = ?) t1 " +
//            "inner join " +
//            "(select * from b_rate where pid = " +
//            "(select ATTRIBUTE_VALUE from b_charge_scheme_attributes WHERE SCHEME_ID =" +
//            "(SELECT DISTINCT SID FROM " +
//            "((select DISTINCT CHARGE_SCHEME_ID AS SID from b_product where pid = " +
//            "(select DISTINCT product_id from b_order where charge_id = " +
//            "(select DISTINCT scheme_id from b_charge_scheme_attributes " +
//            "where ATTRIBUTE_VALUE = ? and ATTRIBUTE_NAME = 'PSTN_RATES_ID'))) " +
//            "union " +
//            "(SELECT ID AS SID FROM B_CHARGE_SCHEME WHERE ID  = " +
//            "(select SCHEME_ID from b_charge_scheme_attributes " +
//            "where ATTRIBUTE_VALUE = ? and ATTRIBUTE_NAME = 'PSTN_RATES_ID') and is_template = 1))) " +
//            "AND ATTRIBUTE_NAME = 'PSTN_RATES_ID')) t2 " +
//            "on t1.CODE = T2.CODE " +
//            "order by country asc";

    private static final String LIST_DETAILED_RATES = "SELECT T1.pid, T1.DISPLAY_NAME, T1.CODE, T1.RATE, T1.SERVICE_RATE, T1.COUNTRY, T2.RATE AS LIST_PRICE_RATE, T2.SERVICE_RATE AS LIST_PRICE_SERVICE_RATE FROM " +
            "(select * from b_rate where pid = ?) t1 " +
            "inner join " +
            "(select * from b_rate where pid = NVL(" +
            "(select ATTRIBUTE_VALUE from b_charge_scheme_attributes WHERE SCHEME_ID =" +
            "(select DISTINCT CHARGE_SCHEME_ID AS SID from b_product where pid = " +
            "(select DISTINCT product_id from b_order where charge_id = " +
            "(select DISTINCT scheme_id from b_charge_scheme_attributes " +
            "where ATTRIBUTE_VALUE = ? and ATTRIBUTE_NAME = 'PSTN_RATES_ID'))) " +
            "AND ATTRIBUTE_NAME = 'PSTN_RATES_ID'), '0')) t2 " +
            "on t1.CODE = T2.CODE " +
            "order by country asc";

    @Override
    public List<PstnRateBean> listDetailedRates(String pid) {
        try {
            return dbHelper.getBeanList(LIST_DETAILED_RATES, PstnRateBean.class, pid, pid);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
