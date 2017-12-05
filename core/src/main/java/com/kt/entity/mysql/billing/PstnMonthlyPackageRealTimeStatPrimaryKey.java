package com.kt.entity.mysql.billing;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Vega Zhou on 2016/4/18.
 */
public class PstnMonthlyPackageRealTimeStatPrimaryKey implements Serializable {

    private Long id;

    private String accountPeriod;

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


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PstnMonthlyPackageRealTimeStatPrimaryKey) {
            PstnMonthlyPackageRealTimeStatPrimaryKey o = (PstnMonthlyPackageRealTimeStatPrimaryKey) obj;
            return Objects.equals(id, o.getId()) &&
                    StringUtils.equalsIgnoreCase(accountPeriod, o.getAccountPeriod());
        } else {
            return false;
        }
    }
}
