package com.kt.entity.mysql.billing;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
public class AccountSnapshotPrimaryKey implements Serializable {
    private String accountId;

    private String accountPeriod;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AccountSnapshotPrimaryKey) {
            AccountSnapshotPrimaryKey o = (AccountSnapshotPrimaryKey) obj;
            return StringUtils.equalsIgnoreCase(accountId, o.getAccountId()) &&
                    StringUtils.equalsIgnoreCase(accountPeriod, o.getAccountPeriod());
        } else {
            return false;
        }
    }
}
