package com.kt.biz.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kt.biz.types.BizType;
import com.kt.entity.mysql.billing.PortsOverflowDetail;
import com.kt.entity.mysql.billing.StorageOverflowDetail;
import com.kt.serializer.BigDecimalJsonDeserializer;
import com.kt.serializer.BigDecimalJsonSerializer;
import com.kt.serializer.DateYMDJsonDeserializer;
import com.kt.serializer.DateYMDJsonSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class StorageUsageBean {

    public BigDecimal getStorageFee() {
        return storageFee;
    }

    public void setStorageFee(BigDecimal storageFee) {
        this.storageFee = storageFee;
    }

    public int getBillPeriod() {
        return billPeriod;
    }

    public void setBillPeriod(int billPeriod) {
        this.billPeriod = billPeriod;
    }

    public List<StorageOverflowDetail> getStorageOverflowDetail() {
        return storageOverflowDetail;
    }

    public void setStorageOverflowDetail(List<StorageOverflowDetail> storageOverflowDetail) {
        this.storageOverflowDetail = storageOverflowDetail;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getOrderStorageAmount() {
        return orderStorageAmount;
    }

    public void setOrderStorageAmount(int orderStorageAmount) {
        this.orderStorageAmount = orderStorageAmount;
    }

    private int siteId;
    List<StorageOverflowDetail> storageOverflowDetail;
    private String customerId;
    private String siteName;
    @JsonDeserialize(using = BigDecimalJsonDeserializer.class)
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal storageFee;

    public BigDecimal getStorageOverFlowFee() {
        return storageOverFlowFee;
    }

    public void setStorageOverFlowFee(BigDecimal portsOverFlowFee) {
        this.storageOverFlowFee = storageOverFlowFee;
    }

    @JsonDeserialize(using = BigDecimalJsonDeserializer.class)
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal storageOverFlowFee;

    private int billPeriod;

    public BizType getServiceType() {
        return serviceType;
    }

    public void setServiceType(BizType serviceType) {
        this.serviceType = serviceType;
    }

    private BizType serviceType;
    private int usedStorageAmount;
    private int orderStorageAmount;

    private String orderId;
    @JsonDeserialize(using = DateYMDJsonDeserializer.class)
    @JsonSerialize(using = DateYMDJsonSerializer.class)
    private Date peakTime;

    public int getUsedStorageAmount() {
        return usedStorageAmount;
    }

    public void setUsedStorageAmount(int usedStorageAmount) {
        this.usedStorageAmount = usedStorageAmount;
    }


    public Date getPeakTime() {
        return peakTime;
    }

    public void setPeakTime(Date peakTime) {
        this.peakTime = peakTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    private String customerName;


    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private Date effectiveStartDate;
    private Date effectiveEndDate;
    private String bizName;
    private String productName;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    private String customerCode;

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @JsonDeserialize(using = BigDecimalJsonDeserializer.class)
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal unitPrice;

    public BigDecimal getOverflowUnitPrice() {
        return overflowUnitPrice;
    }

    public void setOverflowUnitPrice(BigDecimal overflowUnitPrice) {
        this.overflowUnitPrice = overflowUnitPrice;
    }

    @JsonDeserialize(using = BigDecimalJsonDeserializer.class)
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal overflowUnitPrice;

}