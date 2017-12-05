package com.kt.entity.mysql.batch;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Table(name = "B_PROVISION_HOST_CONFIG")
@Entity
public class ProvHostConfig {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "SALES_MAN_ID")
    private String salesId;

    @Column(name = "SERVICE_NAME")
    private String serviceName;

    @Column(name = "ATTENDEE_CAPACITY")
    private String attendeeCapacity;

    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "PRICE")
    private String price;

    @Column(name = "SITE_NAME")
    private String siteName;

    @Column(name = "TEL_TYPE")
    private String telType;

    @Column(name = "PRODUCT_TYPE")
    private String productType;

    @Column(name = "PSTN_PROD_ID")
    private String pstnProdId;

    @Column(name = "PSTN_PACKET_PROD_ID")
    private String pstnPacketProdId;

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

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAttendeeCapacity() {
        return attendeeCapacity;
    }

    public void setAttendeeCapacity(String attendeeCapacity) {
        this.attendeeCapacity = attendeeCapacity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTelType() {
        return telType;
    }

    public void setTelType(String telType) {
        this.telType = telType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPstnProdId() {
        return pstnProdId;
    }

    public void setPstnProdId(String pstnProdId) {
        this.pstnProdId = pstnProdId;
    }

    public String getPstnPacketProdId() {
        return pstnPacketProdId;
    }

    public void setPstnPacketProdId(String pstnPacketProdId) {
        this.pstnPacketProdId = pstnPacketProdId;
    }
}
