package com.kt.entity.mysql.crm;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
@Table(name = "B_WEBEX_SITE_DRAFT")
@Entity
public class WebExSiteDraft {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "SITENAME")
    private String siteName;

    @Column(name = "CONTRACT_ID")
    private String contractId;

    //PRIMARY FIELDS
    @Column(name = "PRIMARY_LANGUAGE")
    private String primaryLanguage;
    @Column(name = "ADDITIONAL_LANGUAGES")
    private String additionalLanguage;
    @Column(name = "TIMEZONE")
    private String timeZone;
    @Column(name = "COUNTRY_CODE")
    private String countryCode;
    @Column(name = "LOCATION")
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

    public String getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(String primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getAdditionalLanguage() {
        return additionalLanguage;
    }

    public void setAdditionalLanguage(String additionalLanguage) {
        this.additionalLanguage = additionalLanguage;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
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