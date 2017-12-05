package com.kt.biz.bean;

/**
 * Created by Vega Zhou on 2017/5/15.
 */
public class PersonalWebExBill {
    private long id;

    private String productName;

    private String orderStartDate;

    private String orderEndDate;

    private float totalAmount;

    private float unpaidAmount;

    private String writtenOffDate;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderStartDate() {
        return orderStartDate;
    }

    public void setOrderStartDate(String orderStartDate) {
        this.orderStartDate = orderStartDate;
    }

    public String getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(String orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(float unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public String getWrittenOffDate() {
        return writtenOffDate;
    }

    public void setWrittenOffDate(String writtenOffDate) {
        this.writtenOffDate = writtenOffDate;
    }
}
