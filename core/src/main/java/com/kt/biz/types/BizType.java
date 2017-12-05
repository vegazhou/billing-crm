package com.kt.biz.types;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public enum BizType implements SchemeType {
    WEBEX_MC("MC"),
    WEBEX_EC("EC"),
    WEBEX_TC("TC"),
    WEBEX_SC("SC"),
    WEBEX_EE("EE"),
    WEBEX_STORAGE("STORAGE"),
    WEBEX_PSTN("WEBEX_PSTN"),
    WEBEX_CMR("WEBEX_CMR"),
    CC("CC"),
    MISC("MISC");

    private String serviceName;

    BizType(String serviceName) {
        this.serviceName = serviceName;
    }


    public String getServiceName() {
        return serviceName;
    }
}
