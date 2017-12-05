package com.kt.entity.mysql.billing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kt.serializer.DateYMDJsonDeserializer;
import com.kt.serializer.DateYMDJsonSerializer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/31.
 */
@Table(name = "BB_PORTS_OVERFLOW_DETAILS")
@Entity
public class PortsOverflowDetail {
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

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID")
    private String id;
    @Column(name = "CUSTOMER_ID")
    private String customerId;
    @Column(name = "BILL_PERIOD")
    private int billPeriod;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
    @Column(name = "PEAK_NUMBER")
    private int peakNumber;
    @Column(name = "PEAK_TIME")
    @JsonDeserialize(using = DateYMDJsonDeserializer.class)
    @JsonSerialize(using = DateYMDJsonSerializer.class)
    private Date peakTime;
    @Column(name = "SITE_ID")
    private int siteId;
    @Column(name = "SITE_NAME")
    private String siteName;
    @Column(name = "CONF_ID")
    private long confId;
    @Column(name = "CONF_KEY")
    private int confKey;
    @Column(name = "CONF_NAME")
    private String confName;
    @Column(name = "CONF_TYPE")
    private String confType;
    @Column(name = "MEETING_TYPE")
    private String meetingType;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "USER_EMAIL")
    private String userEmail;
    @Column(name = "DURATION")
    private int duration;
    @Column(name = "START_TIME")
    @JsonDeserialize(using = DateYMDJsonDeserializer.class)
    @JsonSerialize(using = DateYMDJsonSerializer.class)
    private Date startTime;
    @Column(name = "END_TIME")
    @JsonDeserialize(using = DateYMDJsonDeserializer.class)
    @JsonSerialize(using = DateYMDJsonSerializer.class)
    private Date endTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME")
    @JsonDeserialize(using = DateYMDJsonDeserializer.class)
    @JsonSerialize(using = DateYMDJsonSerializer.class)
    private Date createTime;

    public int getMeetingPeakNumber() {
        return meetingPeakNumber;
    }

    public void setMeetingPeakNumber(int meetingPeakNumber) {
        this.meetingPeakNumber = meetingPeakNumber;
    }

    @Column(name = "MEETING_PEAK_NUMBER")
    private int meetingPeakNumber;


    public long getObjId() {
        return objId;
    }

    public void setObjId(long objId) {
        this.objId = objId;
    }

    @Column(name = "OBJID")
    private long objId;

    public int getOrderedPorts() {
        return orderedPorts;
    }

    public void setOrderedPorts(int orderedPorts) {
        this.orderedPorts = orderedPorts;
    }

    @Column(name = "ORDERED_PORTS")
    public int orderedPorts;

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
    @Column(name = "HOSTNAME")
    private String hostName;
    @Column(name = "HOSTEMAIL")
    private String hostEmail;
}
