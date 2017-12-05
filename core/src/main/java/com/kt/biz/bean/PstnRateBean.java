package com.kt.biz.bean;

/**
 * Created by Vega Zhou on 2016/10/8.
 */
public class PstnRateBean {
    private String pid;

    private String displayName;

    private String code;

    private float rate;

    private float serviceRate;

    private String country;

    private float listPriceRate;

    private float listPriceServiceRate;



    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(float serviceRate) {
        this.serviceRate = serviceRate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getListPriceRate() {
        return listPriceRate;
    }

    public void setListPriceRate(float listPriceRate) {
        this.listPriceRate = listPriceRate;
    }

    public float getListPriceServiceRate() {
        return listPriceServiceRate;
    }

    public void setListPriceServiceRate(float listPriceServiceRate) {
        this.listPriceServiceRate = listPriceServiceRate;
    }

    public boolean isChanged() {
        return listPriceRate != rate || listPriceServiceRate != serviceRate;
    }
}
