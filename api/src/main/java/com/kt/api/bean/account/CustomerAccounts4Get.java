package com.kt.api.bean.account;

import com.kt.entity.mysql.billing.CustomerAccount;

/**
 * Created by Vega on 2017/10/23.
 */
public class CustomerAccounts4Get {

    CustomerAccount prepaid;

    CustomerAccount deposit;

    CustomerAccount ccDeposit;

    public CustomerAccount getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(CustomerAccount prepaid) {
        this.prepaid = prepaid;
    }

    public CustomerAccount getDeposit() {
        return deposit;
    }

    public void setDeposit(CustomerAccount deposit) {
        this.deposit = deposit;
    }

    public CustomerAccount getCcDeposit() {
        return ccDeposit;
    }

    public void setCcDeposit(CustomerAccount ccDeposit) {
        this.ccDeposit = ccDeposit;
    }
}
