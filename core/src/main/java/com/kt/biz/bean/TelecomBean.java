package com.kt.biz.bean;

import com.kt.biz.types.PayInterval;

/**
 * Created by Administrator on 2016/6/12.
 */
public class TelecomBean {

    private String siteName;
    private String webexId;
    private String enterpriseCode;
    private String enterpriseName;
    private String initialPassword;
    private String userName;
    private PayInterval payInterval;
    private int buyMonths;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PayInterval getPayInterval() {
        return payInterval;
    }

    public void setPayInterval(PayInterval payInterval) {
        this.payInterval = payInterval;
    }

    public int getBuyMonths() {
        return buyMonths;
    }

    public void setBuyMonths(int buyMonths) {
        this.buyMonths = buyMonths;
    }
}
