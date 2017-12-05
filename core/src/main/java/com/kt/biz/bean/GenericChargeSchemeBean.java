package com.kt.biz.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.common.SchemeKeys;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/30.
 */
public class GenericChargeSchemeBean implements WebExConfMonthlyPayByHostsBean,
        PSTNStandardChargeBean {

    @JsonProperty("COMMON_SITE")
    private String commonSite;

    @JsonProperty("COMMON_SITES")
    private List<String> commonSites;

    @JsonProperty("COMMON_UNIT_PRICE")
    private Float unitPrice;

    @JsonProperty("COMMON_OVERFLOW_UNIT_PRICE")
    private Float overflowUnitPrice;

    @JsonProperty("EFFECTIVE_BEFORE")
    private String effectiveBefore;

    @JsonProperty("HOSTS_AMOUNT")
    private Integer hostAmount;

    @JsonProperty("MONTH_AMOUNT")
    private Integer monthAmount;

    @JsonProperty("PORTS_AMOUNT")
    private Integer portsAmount;

    @JsonProperty("PSTN_PACKAGE_MINUTES")
    private Integer packageMinutes;

    @JsonProperty("STORAGE_SIZE")
    private Integer storageSize;

    @JsonProperty("TIMES")
    private Integer times;

    @JsonProperty("TOTAL_PRICE")
    private Float totalPrice;

    @JsonProperty("MONTHLY_FEE")
    private Float monthlyFee;

    @JsonProperty("WEBEX_ID")
    private String webexId;

    @JsonProperty(value = SchemeKeys.ENTERPRISE_CODE)
    private String enterpriseCode;

    @JsonProperty(value = SchemeKeys.ENTERPRISE_NAME)
    private String enterpriseName;

    @JsonProperty(value = SchemeKeys.INITIAL_PASSWORD)
    private String initialPassword;

    @JsonProperty(value = SchemeKeys.DISPLAY_NAME)
    private String displayName;

    @JsonProperty(value = SchemeKeys.FULL_NAME)
    private String fullName;

    @JsonProperty(value = SchemeKeys.SUPPORT_BILL_SPLIT)
    private Integer supportBillSplit = 0;


    @Override
    public String getCommonSite() {
        return commonSite;
    }

    @Override
    public void setCommonSite(String commonSite) {
        this.commonSite = commonSite;
    }

    @Override
    public List<String> getCommonSites() {
        return commonSites;
    }

    @Override
    public void setCommonSites(List<String> commonSites) {
        this.commonSites = commonSites;
    }

    @Override
    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getOverflowUnitPrice() {
        return overflowUnitPrice;
    }

    public void setOverflowUnitPrice(Float overflowUnitPrice) {
        this.overflowUnitPrice = overflowUnitPrice;
    }

    @Override
    public String getEffectiveBefore() {
        return effectiveBefore;
    }

    @Override
    public void setEffectiveBefore(String effectiveBefore) {
        this.effectiveBefore = effectiveBefore;
    }

    @Override
    public Integer getHostAmount() {
        return hostAmount;
    }

    @Override
    public void setHostAmount(Integer hostAmount) {
        this.hostAmount = hostAmount;
    }

    @Override
    public Integer getMonthAmount() {
        return monthAmount;
    }

    @Override
    public void setMonthAmount(Integer monthAmount) {
        this.monthAmount = monthAmount;
    }

    public Integer getPortsAmount() {
        return portsAmount;
    }

    public void setPortsAmount(Integer portsAmount) {
        this.portsAmount = portsAmount;
    }

    public Integer getPackageMinutes() {
        return packageMinutes;
    }

    public void setPackageMinutes(Integer packageMinutes) {
        this.packageMinutes = packageMinutes;
    }

    public Integer getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(Integer storageSize) {
        this.storageSize = storageSize;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Float getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(Float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public String getWebexId() {
        return webexId;
    }

    public void setWebexId(String webexId) {
        this.webexId = webexId;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getInitialPassword() {
        return initialPassword;
    }

    public void setInitialPassword(String initialPassword) {
        this.initialPassword = initialPassword;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getSupportBillSplit() {
        return supportBillSplit;
    }

    public void setSupportBillSplit(Integer supportBillSplit) {
        this.supportBillSplit = supportBillSplit;
    }
}
