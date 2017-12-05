package com.kt.biz.billing;

import com.kt.biz.types.FeeType;

import java.math.BigDecimal;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
public class BillItem {

    private AccountPeriod accountPeriod;

    private String orderId;

    private FeeType feeType;

    private BigDecimal amount;

    public AccountPeriod getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(AccountPeriod accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
