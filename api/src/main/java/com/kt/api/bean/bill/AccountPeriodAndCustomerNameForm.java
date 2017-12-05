package com.kt.api.bean.bill;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vega on 2017/10/30.
 */
public class AccountPeriodAndCustomerNameForm {
    @NotBlank(message = "bill.charge.customerName.NotBlank")
    private String customerName;

    @NotBlank(message = "bill.charge.accountPeriod.NotBlank")
    private String accountPeriod;


    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
