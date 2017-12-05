package com.kt.entity.mysql.billing;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/4/8.
 */
@Table(name = "BB_PSTN_MONTHLY_PACK_META" )
@Entity
@SequenceGenerator(name="SEQ_PACKAGE_ID", sequenceName="SEQ_PACKAGE_ID", allocationSize = 1)
public class PstnMonthlyPackage {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQ_PACKAGE_ID")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MINUTES")
    private long minutes;

    @Column(name = "START_DATE")
    private String startDate;

    @Column(name = "END_DATE")
    private String endDate;

    @Column(name = "DURATION")
    private int duration;

    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
