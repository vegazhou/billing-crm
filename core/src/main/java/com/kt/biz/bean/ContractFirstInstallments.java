package com.kt.biz.bean;

import com.kt.biz.types.FeeType;
import com.kt.entity.mysql.crm.Order;

import java.math.BigDecimal;

/**
 * Created by Vega on 2017/11/27.
 */
public class ContractFirstInstallments {
    private BigDecimal webexFirstInstallment = new BigDecimal(0);

    private BigDecimal ccFirstInstallment = new BigDecimal(0);

    public ContractFirstInstallments() {
    }


    public void countIn(Order order) {
        FeeType fiType = FeeType.valueOf(order.getFiType());
        if (FeeType.isWeBexFeeType(fiType)) {
            webexFirstInstallment = webexFirstInstallment.add(BigDecimal.valueOf(order.getFirstInstallment()));
        } else if (FeeType.isCcFeeType(fiType)) {
            ccFirstInstallment = ccFirstInstallment.add(BigDecimal.valueOf(order.getFirstInstallment()));
        }
    }


    public BigDecimal getWebexFirstInstallment() {
        return webexFirstInstallment;
    }

    public void setWebexFirstInstallment(BigDecimal webexFirstInstallment) {
        this.webexFirstInstallment = webexFirstInstallment;
    }

    public BigDecimal getCcFirstInstallment() {
        return ccFirstInstallment;
    }

    public void setCcFirstInstallment(BigDecimal ccFirstInstallment) {
        this.ccFirstInstallment = ccFirstInstallment;
    }
}
