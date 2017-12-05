package com.kt.entity.mysql.crm;

import java.io.Serializable;

/**
 * Created by Vega Zhou on 2016/3/9.
 */
public class EcTierRatePrimaryKey implements Serializable {
    private String suiteId;

    private int tier;

    public String getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(String suiteId) {
        this.suiteId = suiteId;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EcTierRatePrimaryKey) {
            EcTierRatePrimaryKey o = (EcTierRatePrimaryKey) obj;
            return suiteId.equals(o.getSuiteId()) && tier == o.getTier();
        } else {
            return false;
        }
    }
}
