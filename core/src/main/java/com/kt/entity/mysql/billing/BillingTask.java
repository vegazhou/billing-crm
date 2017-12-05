package com.kt.entity.mysql.billing;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/4/13.
 */
@Table(name = "BB_BILLING_TASK")
@Entity
public class BillingTask {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "ACCOUNT_PERIOD")
    private String accountPeriod;

    @Column(name = "STATUS")
    private String status;

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

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
