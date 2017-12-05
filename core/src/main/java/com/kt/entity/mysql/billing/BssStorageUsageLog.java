package com.kt.entity.mysql.billing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/14.
 */

@Table(name = "BB_STORAGE_OVERFLOW_LOG")
@Entity
public class BssStorageUsageLog {

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public int getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(int accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public long getStorageOrderSize() {
        return storageOrderSize;
    }

    public void setStorageOrderSize(long storageOrderSize) {
        this.storageOrderSize = storageOrderSize;
    }

    public long getStorageUsedSize() {
        return storageUsedSize;
    }

    public void setStorageUsedSize(long storageUsedSize) {
        this.storageUsedSize = storageUsedSize;
    }

    public long getStorageOverflowSize() {
        return storageOverflowSize;
    }

    public void setStorageOverflowSize(long storageOverflowSize) {
        this.storageOverflowSize = storageOverflowSize;
    }

    public double getStorageOverflowUnitPrice() {
        return storageOverflowUnitPrice;
    }

    public void setStorageOverflowUnitPrice(double storageOverflowUnitPrice) {
        this.storageOverflowUnitPrice = storageOverflowUnitPrice;
    }

    public double getStorageOverflowFee() {
        return storageOverflowFee;
    }

    public void setStorageOverflowFee(double storageOverflowFee) {
        this.storageOverflowFee = storageOverflowFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "SITE_ID")
    private int siteId;

    @Column(name = "SITE_NAME")
    private String siteName;

    @Column(name = "ACCOUNT_PERIOD")
    private int accountPeriod;

    @Column(name = "STORAGE_ORDER_SIZE")
    private long storageOrderSize;

    @Column(name = "STORAGE_USED_SIZE")
    private long storageUsedSize;

    @Column(name = "STORAGE_OVERFLOW_SIZE")
    private long storageOverflowSize;

    @Column(name = "STORAGE_OVERFLOW_UNIT_PRICE")
    private double storageOverflowUnitPrice;

    @Column(name = "STORAGE_OVERFLOW_FEE")
    private double storageOverflowFee;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    @Column(name = "CUSTOMER_CODE")
    private String customerCode;
}
