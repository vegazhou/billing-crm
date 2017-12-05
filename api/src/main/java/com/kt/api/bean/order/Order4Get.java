package com.kt.api.bean.order;

/**
 * Created by Vega Zhou on 2016/3/21.
 */
public class Order4Get {

    private String productName;

    private String productId;

    private String bizId;

    private String chargeSchemeId;
    
    private String payInterval;
    
    private String effectiveStartDate;

    private String bizChance;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getChargeSchemeId() {
        return chargeSchemeId;
    }

    public void setChargeSchemeId(String chargeSchemeId) {
        this.chargeSchemeId = chargeSchemeId;
    }

	public String getPayInterval() {
		return payInterval;
	}

	public void setPayInterval(String payInterval) {
		this.payInterval = payInterval;
	}

	public String getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

    public String getBizChance() {
        return bizChance;
    }

    public void setBizChance(String bizChance) {
        this.bizChance = bizChance;
    }
}
