package com.kt.entity.mysql.billing;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
@Table(name = "BB_BILL_TEMP_DETAIL")
@Entity
@SequenceGenerator(name="SEQ_BILL_DETAIL", sequenceName="SEQ_BILL_DETAIL", allocationSize = 1)
public class BillTempDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQ_BILL_DETAIL")
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

    @Column(name = "CONFIRMED")
    private int confirmed;

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

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }
}
