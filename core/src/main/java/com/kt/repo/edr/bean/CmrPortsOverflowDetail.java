package com.kt.repo.edr.bean;


import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/31.
 */
public class CmrPortsOverflowDetail {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getBillPeriod() {
        return billPeriod;
    }

    public void setBillPeriod(int billPeriod) {
        this.billPeriod = billPeriod;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getPeakNumber() {
        return peakNumber;
    }

    public void setPeakNumber(int peakNumber) {
        this.peakNumber = peakNumber;
    }

    public Date getPeakTime() {
        return peakTime;
    }

    public void setPeakTime(Date peakTime) {
        this.peakTime = peakTime;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public long getConfId() {
        return confId;
    }

    public void setConfId(long confId) {
        this.confId = confId;
    }

    public int getConfKey() {
        return confKey;
    }

    public void setConfKey(int confKey) {
        this.confKey = confKey;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public String getConfType() {
        return confType;
    }

    public void setConfType(String confType) {
        this.confType = confType;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private String id;
    private String customerId;
    private int billPeriod;
    private String customerName;
    private int peakNumber;
    private Date peakTime;
    private int siteId;
    private String siteName;
    private long confId;
    private int confKey;
    private String confName;
    private String confType;
    private String meetingType;
    private String userName;
    private String userEmail;
    private int duration;
    private Date startTime;
    private Date endTime;
    private Date createTime;

    public int getMeetingPeakNumber() {
        return meetingPeakNumber;
    }

    public void setMeetingPeakNumber(int meetingPeakNumber) {
        this.meetingPeakNumber = meetingPeakNumber;
    }

    private int meetingPeakNumber;


    public long getObjId() {
        return objId;
    }

    public void setObjId(long objId) {
        this.objId = objId;
    }

    private long objId;

    public int getOrderedPorts() {
        return orderedPorts;
    }

    public void setOrderedPorts(int orderedPorts) {
        this.orderedPorts = orderedPorts;
    }

    private int orderedPorts;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }
    private String hostName;
    private String hostEmail;

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    private BigDecimal unitPrice;

    public BigDecimal getOverflowUnitPrice() {
        return overflowUnitPrice;
    }

    public void setOverflowUnitPrice(BigDecimal overflowUnitPrice) {
        this.overflowUnitPrice = overflowUnitPrice;
    }

    private BigDecimal overflowUnitPrice;
}
