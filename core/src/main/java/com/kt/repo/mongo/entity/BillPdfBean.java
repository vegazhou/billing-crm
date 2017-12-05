package com.kt.repo.mongo.entity;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by jianf on 2016/7/18.
 */
@Document(collection = "bill_pdf")
public class BillPdfBean {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Binary getPdf() {
        return pdf;
    }

    public void setPdf(Binary pdf) {
        this.pdf = pdf;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Field(value = "id")
    private String id;

    @Field(value = "image")
    private Binary pdf;

    @Field(value = "year")
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Field(value = "month")
    private int month;

    @Field(value = "code")
    private String code;

    @Field(value = "customerId")
    private String customerId;

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    @Field(value = "startDate")
    private int startDate;

    @Field(value = "endDate")
    private int endDate;

    @Field(value = "createdTime")
    private Date createdTime;
}
