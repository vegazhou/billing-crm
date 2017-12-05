package com.kt.api.bean.bill;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vega on 2017/10/27.
 */
public class AccountPeriodAndCustomerIdForm {
    @NotBlank(message = "bill.charge.customerId.NotBlank")
    private String customerId;

    @NotBlank(message = "bill.charge.accountPeriod.NotBlank")
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
