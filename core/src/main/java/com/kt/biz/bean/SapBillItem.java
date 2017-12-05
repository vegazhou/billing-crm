package com.kt.biz.bean;

/**
 * Created by Vega Zhou on 2016/10/20.
 */
public class SapBillItem {

    private String accountPeriod;

    private String billNumber;

    private String customerCode;

    private String customerName;

    private String feeType;

    private float totalAmount;

    private float paidAmount;

    private float unpaidAmount;


    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
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

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(float paidAmount) {
        this.paidAmount = paidAmount;
    }

    public float getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(float unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }
}
