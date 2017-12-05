package com.kt.api.bean.bill;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vega on 2017/10/27.
 */
public class AccountPeriodForm {
    @NotBlank(message = "bill.charge.accountPeriod.NotBlank")
    private String accountPeriod;

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }
}
