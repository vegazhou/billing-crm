package com.kt.api.bean.wbxsite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/31.
 */
public class WbxSiteDraft4Get {
    private String id;

    private String siteName;

    private String contractId;

    //PRIMARY FIELDS
    private String primaryLanguage;

    private List<String> additionalLanguage;

    private int timeZone;

    private String countryCode;

    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(String primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public List<String> getAdditionalLanguage() {
        return additionalLanguage;
    }

    public void setAdditionalLanguage(List<String> additionalLanguage) {
        this.additionalLanguage = additionalLanguage;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
