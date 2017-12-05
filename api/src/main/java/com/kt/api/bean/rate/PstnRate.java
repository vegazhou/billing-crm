package com.kt.api.bean.rate;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vega Zhou on 2016/4/28.
 */
public class PstnRate {
    @NotBlank(message = "pstnrate.code.NotBlank")
    private String code;

    private String displayName;

    private Float rate;

    private Float serviceRate;

    private Float listPriceRate;

    private Float listPriceServiceRate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Float getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Float serviceRate) {
        this.serviceRate = serviceRate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Float getListPriceRate() {
        return listPriceRate;
    }

    public void setListPriceRate(Float listPriceRate) {
        this.listPriceRate = listPriceRate;
    }

    public Float getListPriceServiceRate() {
        return listPriceServiceRate;
    }

    public void setListPriceServiceRate(Float listPriceServiceRate) {
        this.listPriceServiceRate = listPriceServiceRate;
    }

    public boolean isChanged() {
        return !listPriceServiceRate.equals(serviceRate) || !listPriceRate.equals(rate);
    }

    public void setChanged(boolean changed) {

    }
}
