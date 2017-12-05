package com.kt.entity.mysql.billing;

/**
 * Created by Vega Zhou on 2016/4/11.
 */

import javax.persistence.*;


@Table(name = "BB_PSTN_SINGLE_PACK_META" )
@Entity
@SequenceGenerator(name="SEQ_PACKAGE_ID", sequenceName="SEQ_PACKAGE_ID", allocationSize = 1)
public class PstnSinglePackage {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQ_PACKAGE_ID")
    @Column(name = "ID")
    private Long id;

    @Column(name = "TOTAL_MINUTES")
    private int totalMinutes;

    @Column(name = "START_DATE")
    private String startDate;

    @Column(name = "END_DATE")
    private String endDate;

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

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
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
