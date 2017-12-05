package com.kt.repo.mysql.billing;

import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.DbHelper;
import com.kt.entity.mysql.billing.PstnMonthlyPackage;
import com.kt.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/13.
 */
@Repository
public class PstnMonthlyPackageRepositoryImpl implements PstnMonthlyPackageRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(PstnMonthlyPackageRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;

    private static final String SQL_FIND_WORKING_PACKAGES_IN_ACCOUNT_PERIOD =
            "SELECT p.*, cu.DISPLAY_NAME, cu.PID FROM bb_pstn_monthly_pack_meta p " +
            "INNER JOIN b_order o ON p.order_id = o.PID " +
            "INNER JOIN b_contract c ON o.CONTRACT_ID = c.PID " +
            "INNER JOIN b_customer cu ON cu.PID = c.CUSTOMER_ID " +
            "WHERE p.order_id IS NOT NULL AND (o.state = 'IN_EFFECT' OR o.state = 'END_OF_LIFE') AND " +
            "(p.START_DATE < ? OR p.end_date > ?) AND cu.pid = (SELECT cu.PID FROM b_order o " +
            "INNER JOIN b_contract c ON o.CONTRACT_ID = c.PID " +
            "INNER JOIN b_customer cu ON c.CUSTOMER_ID = cu.PID " +
            "WHERE o.PID = ?)";

    @Override
    public List<PstnMonthlyPackage> findWorkingPackagesInAccountPeriod(String pstnStandardOrderId, AccountPeriod accountPeriod) {
        try {
            return dbHelper.getBeanList(SQL_FIND_WORKING_PACKAGES_IN_ACCOUNT_PERIOD, PstnMonthlyPackage.class,
                    DateUtil.formatDate(accountPeriod.endOfThisPeriod()), DateUtil.formatDate(accountPeriod.beginOfThisPeriod()), pstnStandardOrderId);
        } catch (SQLException e) {
            LOGGER.error("error occured in PstnMonthlyPackageRepositoryImpl.findWorkingPackagesInAccountPeriod", e);
            return null;
        }
    }
}
