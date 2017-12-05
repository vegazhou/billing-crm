package com.kt.api.bean.contract;

/**
 * Created by Vega Zhou on 2016/5/11.
 */
public class FinApproveForm {
    private Float receivedAmount;

    private Float ccReceivedAmount;

    public Float getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(Float receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public Float getCcReceivedAmount() {
        return ccReceivedAmount;
    }

    public void setCcReceivedAmount(Float ccReceivedAmount) {
        this.ccReceivedAmount = ccReceivedAmount;
    }
}
