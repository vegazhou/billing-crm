package com.kt.api.bean.contract;

/**
 * Created by Vega Zhou on 2016/9/9.
 */
public class ContractConfirmDetail {
    private String id;

    private float webexFirstInstallment;

    private float webexRefund;

    private float ccFirstInstallment;

    private float ccRefund;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getWebexFirstInstallment() {
        return webexFirstInstallment;
    }

    public void setWebexFirstInstallment(float webexFirstInstallment) {
        this.webexFirstInstallment = webexFirstInstallment;
    }

    public float getWebexRefund() {
        return webexRefund;
    }

    public void setWebexRefund(float webexRefund) {
        this.webexRefund = webexRefund;
    }

    public float getCcFirstInstallment() {
        return ccFirstInstallment;
    }

    public void setCcFirstInstallment(float ccFirstInstallment) {
        this.ccFirstInstallment = ccFirstInstallment;
    }

    public float getCcRefund() {
        return ccRefund;
    }

    public void setCcRefund(float ccRefund) {
        this.ccRefund = ccRefund;
    }
}
