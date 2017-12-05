package com.kt.biz.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kt.biz.types.BizType;
import com.kt.entity.mysql.billing.PortsOverflowDetail;
import com.kt.serializer.BigDecimalJsonDeserializer;
import com.kt.serializer.BigDecimalJsonSerializer;
import com.kt.serializer.DateYMDJsonDeserializer;
import com.kt.serializer.DateYMDJsonSerializer;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class PortsUsageBean {

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
//
//    public BizType getBizType() {
//        if(bizType == null && StringUtils.isNotBlank(serviceType)) {
//            switch (serviceType) {
//                case "WEBEX_MC":
//                    bizType = BizType.WEBEX_MC;
//                    break;
//                case "WEBEX_EC":
//                    bizType = BizType.WEBEX_EC;
//                    break;
//                case "WEBEX_TC":
//                    bizType = BizType.WEBEX_TC;
//                    break;
//                case "WEBEX_SC":
//                    bizType = BizType.WEBEX_SC;
//                    break;
//                case "WEBEX_EE":
//                    bizType = BizType.WEBEX_EE;
//                    break;
//                case "WEBEX_STORAGE":
//                    bizType = BizType.WEBEX_STORAGE;
//                    break;
//                case "WEBEX_PSTN":
//                    bizType = BizType.WEBEX_PSTN;
//                    break;
//                case "MISC":
//                    bizType = BizType.MISC;
//            }
//        }
//        return bizType;
//    }
//
//    public void setBizType(BizType bizType) {
//
//        this.bizType = bizType;
//        serviceType = this.getServiceType();
//    }

    public List<PortsOverflowDetail> getPortsOverflowDetail() {
        return portsOverflowDetail;
    }

    public void setPortsOverflowDetail(List<PortsOverflowDetail> portsOverflowDetail) {
        this.portsOverflowDetail = portsOverflowDetail;
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

    public int getOrderPortsAmount() {
        return orderPortsAmount;
    }

    public void setOrderPortsAmount(int orderPortsAmount) {
        this.orderPortsAmount = orderPortsAmount;
    }

    private int siteId;
    List<PortsOverflowDetail> portsOverflowDetail;
    private String customerId;
    private String siteName;
    @JsonDeserialize(using = BigDecimalJsonDeserializer.class)
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal portsFee;
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

//    public String getServiceType() {
//        if(StringUtils.isBlank(serviceType) && bizType != null){
//            switch (bizType.getServiceName()) {
//                case "MC":
//                    serviceType = "WEBEX_MC";
//                    break;
//                case "EC":
//                    serviceType = "WEBEX_EC";
//                    break;
//                case "TC":
//                    serviceType = "WEBEX_TC";
//                    break;
//                case "SC":
//                    serviceType = "WEBEX_SC";
//                    break;
//                case "EE":
//                    serviceType = "WEBEX_EE";
//                    break;
//                case "STORAGE":
//                    serviceType =  "WEBEX_STORAGE";
//                    break;
//                case "WEBEX_PSTN":
//                    serviceType =  "WEBEX_PSTN";
//                    break;
//                case "MISC":
//                    serviceType =  "MISC";
//            }
//        }
//        return serviceType;
//    }
//
//    public void setServiceType(String serviceType) {
//        this.serviceType = serviceType;
//        bizType = this.getBizType();
//    }

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
}
