package com.kt.biz.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kt.biz.types.BizType;
import com.kt.serializer.BigDecimalJsonDeserializer;
import com.kt.serializer.BigDecimalJsonSerializer;
import com.kt.serializer.DateYMDJsonDeserializer;
import com.kt.serializer.DateYMDJsonSerializer;
//import com.skytech.csm.bean.CsmCmrPortsOverflowDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class CmrPortsUsageBean {

    public BigDecimal getPortsFee() {
        return portsFee;
    }

    public void setPortsFee(BigDecimal portsFee) {
        this.portsFee = portsFee;
    }

    public int getBillPeriod() {
        return billPeriod;
    }

    public void setBillPeriod(int billPeriod) {
        this.billPeriod = billPeriod;
    }

//    public List<CsmCmrPortsOverflowDetail> getPortsOverflowDetail() {
//        return portsOverflowDetail;
//    }
//
//    public void setPortsOverflowDetail(List<CsmCmrPortsOverflowDetail> portsOverflowDetail) {
//        this.portsOverflowDetail = portsOverflowDetail;
//    }

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

    public int getOrderPortsAmount() {
        return orderPortsAmount;
    }

    public void setOrderPortsAmount(int orderPortsAmount) {
        this.orderPortsAmount = orderPortsAmount;
    }

    private int siteId;
//    List<CsmCmrPortsOverflowDetail> portsOverflowDetail;
    private String customerId;
    private String siteName;
    @JsonDeserialize(using = BigDecimalJsonDeserializer.class)
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal portsFee;

    public BigDecimal getPortsOverFlowFee() {
        return portsOverFlowFee;
    }

    public void setPortsOverFlowFee(BigDecimal portsOverFlowFee) {
        this.portsOverFlowFee = portsOverFlowFee;
    }

    @JsonDeserialize(using = BigDecimalJsonDeserializer.class)
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal portsOverFlowFee;

    private int billPeriod;

    public BizType getServiceType() {
        return serviceType;
    }

    public void setServiceType(BizType serviceType) {
        this.serviceType = serviceType;
    }

    private BizType serviceType;
    private int usedPortsAmount;
    private int orderPortsAmount;

    private String orderId;
    @JsonDeserialize(using = DateYMDJsonDeserializer.class)
    @JsonSerialize(using = DateYMDJsonSerializer.class)
    private Date peakTime;

    public int getUsedPortsAmount() {
        return usedPortsAmount;
    }

    public void setUsedPortsAmount(int usedPortsAmount) {
        this.usedPortsAmount = usedPortsAmount;
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
    public int getNumberOfOverflows() {
        return numberOfOverflows;
    }

    public void setNumberOfOverflows(int numberOfOverflows) {
        this.numberOfOverflows = numberOfOverflows;
    }

    private int numberOfOverflows;

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
