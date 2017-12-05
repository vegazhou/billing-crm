package com.kt.entity.mysql.billing;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by Vega Zhou on 2016/4/28.
 */
public class BillConfirmationPrimaryKey implements Serializable {
    private String customerId;

    private String accountPeriod;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }
}
