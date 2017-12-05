package com.kt.repo.mysql.billing;

import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.DbHelper;
import com.kt.entity.mysql.billing.AccountOperation;
import com.kt.entity.mysql.billing.BillFormalDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/10/21.
 */
@Repository
public class AccountOperationRepositoryImpl implements AccountOperationRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(AccountOperationRepositoryImpl.class);

    @Autowired
    private DbHelper dbHelper;

    private static final String SQL_1 = "select o.*, c.DISPLAY_NAME as customer_name, c.code as customer_code, a.ACCOUNT_TYPE " +
            "from bb_account_operation o inner join bb_account a on o.account_id = a.ID inner join b_customer c on a.CUSTOMER_ID = c.PID " +
            "where operation_type = 2 and CURRENT_AMOUNT > 0 and OPERATE_TIME like ? order by OPERATE_TIME asc";

    @Override
    public List<AccountOperation> findCustomerPaymentsByAccountPeriod(AccountPeriod accountPeriod) {
        try {
            String ac = accountPeriod.toString();

            return dbHelper.getBeanList(SQL_1, AccountOperation.class, ac.substring(0, 4) + "-" + ac.substring(4, 6) + "%");
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();

        }
    }


    private static final String SQL_2 = "select o.*, c.DISPLAY_NAME as customer_name, c.code as customer_code, a.ACCOUNT_TYPE " +
            "from bb_account_operation o inner join bb_account a on o.account_id = a.ID inner join b_customer c on a.CUSTOMER_ID = c.PID " +
            "where operation_type = 2 and CURRENT_AMOUNT > 0 and o.SAPSYNCED = 0 " +
            "order by OPERATE_TIME asc";
    @Override
    public List<AccountOperation> findUnsyncedPayments() {
        try {
            return dbHelper.getBeanList(SQL_2, AccountOperation.class);
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
            return new ArrayList<>();

        }
    }
}
