package com.kt.biz.bean;

import java.math.BigDecimal;

/**
 * Created by Vega on 2017/11/27.
 */
public class ContractRefunds {
    private BigDecimal webexRefund;

    private BigDecimal ccRefund;

    public ContractRefunds(BigDecimal webexRefund, BigDecimal ccRefund) {
        this.webexRefund = webexRefund;
        this.ccRefund = ccRefund;
    }

    public BigDecimal getWebexRefund() {
        return webexRefund;
    }

    public void setWebexRefund(BigDecimal webexRefund) {
        this.webexRefund = webexRefund;
    }

    public BigDecimal getCcRefund() {
        return ccRefund;
    }

    public void setCcRefund(BigDecimal ccRefund) {
        this.ccRefund = ccRefund;
    }
}
