package com.kt.api.bean.bill;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/5/12.
 */
public class TempBillRecalcForm {
    private List<String> customerIds;

    private String accountPeriod;

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public List<String> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<String> customerIds) {
        this.customerIds = customerIds;
    }
}
