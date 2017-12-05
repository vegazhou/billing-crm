package com.kt.api.bean.bill;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vega Zhou on 2016/5/9.
 */
public class TempBillConfirmBean {
    @NotBlank(message = "bill.temp.confirm.customerId.NotBlank")
    private String customerId;

    @NotBlank(message = "bill.temp.confirm.accountPeriod.NotBlank")
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
