package com.kt.entity.mysql.billing;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/4/28.
 */
@Table(name = "BB_BILL_CONFIRMATION")
@IdClass(BillConfirmationPrimaryKey.class)
@Entity
public class BillConfirmation {
    @Id
    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "ACCOUNT_PERIOD")
    private String accountPeriod;

    @Column(name = "CONFIRMED_DATE")
    private String confirmedDate;

    @Column(name = "CONFIRMED_BY")
    private String confirmedBy;

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

    public String getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(String confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }
}
