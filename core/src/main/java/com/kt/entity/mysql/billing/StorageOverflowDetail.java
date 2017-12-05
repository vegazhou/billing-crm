package com.kt.entity.mysql.billing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kt.serializer.DateYMDJsonDeserializer;
import com.kt.serializer.DateYMDJsonSerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * Created by Administrator on 2016/8/31.
 */
@Table(name = "BSS_STORAGE_OVERFLOW_DETAILS")
@Entity
public class StorageOverflowDetail {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Id
//    @GeneratedValue(generator = "paymentableGenerator")
//    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;
    private String customerId;
    private int billPeriod;
    private String customerName;
    @JsonDeserialize(using = DateYMDJsonDeserializer.class)
    @JsonSerialize(using = DateYMDJsonSerializer.class)
    private Date peakTime;
    private int siteId;
    private String siteName;

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

    public Date getConsumptionTime() {
        return consumptionTime;
    }

    public void setConsumptionTime(Date consumptionTime) {
        this.consumptionTime = consumptionTime;
    }

    public long getStorageConsumption() {
        return storageConsumption;
    }

    public void setStorageConsumption(long storageConsumption) {
        this.storageConsumption = storageConsumption;
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }

    public int getSessionType() {
        return sessionType;
    }

    public void setSessionType(int sessionType) {
        this.sessionType = sessionType;
    }

    public long getOrderedStorage() {
        return orderedStorage;
    }

    public void setOrderedStorage(long orderedStorage) {
        this.orderedStorage = orderedStorage;
    }

    public long getPeakStorage() {
        return peakStorage;
    }

    public void setPeakStorage(long peakStorage) {
        this.peakStorage = peakStorage;
    }

    @JsonDeserialize(using = DateYMDJsonDeserializer.class)
    @JsonSerialize(using = DateYMDJsonSerializer.class)
    private Date consumptionTime;
    private long storageConsumption;
    private long hostId;
    private int sessionType;
    private long orderedStorage;
    @JsonDeserialize(using = DateYMDJsonDeserializer.class)
    @JsonSerialize(using = DateYMDJsonSerializer.class)
    private Date createTime;
    private long peakStorage;
}