package com.kt.entity.mysql.billing;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/4/18.
 */
@Table(name = "BB_PSTN_MONTHLY_PACK_RT" )
@IdClass(PstnMonthlyPackageRealTimeStatPrimaryKey.class)
@Entity
public class PstnMonthlyPackageRealTimeStat {
    @Id
    @Column(name = "PACKAGE_ID")
    private Long id;

    @Column(name = "ACCOUNT_PERIOD")
    private String accountPeriod;

    @Column(name = "MINUTES_LEFT")
    private Long minutesLeft;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public Long getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(Long minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
}
