package com.kt.api.bean.bill;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vega Zhou on 2016/6/17.
 */
public class ChargeForm {

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
