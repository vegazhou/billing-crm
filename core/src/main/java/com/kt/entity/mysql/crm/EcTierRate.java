package com.kt.entity.mysql.crm;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/3/9.
 */
@Table(name = "B_EC_TIER_RATE")
@IdClass(EcTierRatePrimaryKey.class)
@Entity
public class EcTierRate {

    @Column(name = "SUITE_ID")
    @Id
    private String suiteId;

    @Column(name = "TIER")
    private int tier;

    @Column(name = "RATE")
    private float rate;

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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
