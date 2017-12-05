package com.kt.api.bean.bill;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vega Zhou on 2016/10/18.
 */
public class CustomerNameAndAccountPeriod {
    @NotBlank(message = "bill.customerName.NotBlank")
    private String customerName;

    @NotBlank(message = "bill.accountPeriod.NotBlank")
    private String accountPeriod;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }
}
