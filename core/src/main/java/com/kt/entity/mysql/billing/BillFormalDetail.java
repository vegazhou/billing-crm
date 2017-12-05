package com.kt.entity.mysql.billing;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/6/16.
 */
@Table(name = "BB_BILL_FORMAL_DETAIL")
@Entity
@SequenceGenerator(name="SEQ_FBILL_DETAIL", sequenceName="SEQ_FBILL_DETAIL", allocationSize = 1)
public class BillFormalDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQ_FBILL_DETAIL")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "AMOUNT")
    private Float amount;

    @Column(name = "FEE_TYPE")
    private int feeType;

    @Column(name = "ACCOUNT_PERIOD")
    private String accountPeriod;

    @Column(name = "UNPAID_AMOUNT")
    private Float unpaidAmount;

    @Column(name = "SAPSYNCED")
    private int sapSynced;

    @Column(name = "CLEARED_DATE")
    private String clearedDate;

    @Transient
    private String customerCode;

    @Transient
    private String customerName;

    @Transient
    private String accountType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Float getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(Float unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public int getSapSynced() {
        return sapSynced;
    }

    public void setSapSynced(int sapSynced) {
        this.sapSynced = sapSynced;
    }

    public String getClearedDate() {
        return clearedDate;
    }

    public void setClearedDate(String clearedDate) {
        this.clearedDate = clearedDate;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
