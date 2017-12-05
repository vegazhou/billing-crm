package com.kt.entity.mysql.crm;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/3/8.
 */
@Table(name = "B_RATE")
@IdClass(RatePrimaryKey.class)
@Entity
public class Rate {
    @Id
    @Column(name = "PID")
    private String pid;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "CODE")
    private String code;

    @Column(name = "RATE")
    private float rate;

    @Column(name = "SERVICE_RATE")
    private float serviceRate;

    @Column(name = "COUNTRY")
    private String country;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(float serviceRate) {
        this.serviceRate = serviceRate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
