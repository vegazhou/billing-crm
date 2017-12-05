package com.kt.entity.mysql.billing;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
@Table(name = "BB_ACCOUNT_SNAPSHOT")
@IdClass(AccountSnapshotPrimaryKey.class)
@Entity
public class AccountSnapshot implements Serializable {
    @Id
    @Column(name = "ACCOUNTID")
    private String accountId;

    @Column(name = "ACCOUNTPERIOD")
    private String accountPeriod;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "BALANCE")
    private float balance;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
