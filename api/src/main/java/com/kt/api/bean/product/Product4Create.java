package com.kt.api.bean.product;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class Product4Create {
    @Size(min = 0, max = 100, message = "product.displayName.Size")
    @NotBlank(message = "product.displayName.NotBlank")
    private String displayName;

    @NotBlank(message = "product.bizId.NotBlank")
    private String bizId;

    @NotBlank(message = "product.chargeSchemeId.NotBlank")
    private String chargeSchemeId;

    private boolean trial;
    
    private boolean agent;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

	public boolean getTrial() {
		return trial;
	}

	public void setTrial(boolean trial) {
		this.trial = trial;
	}

	public boolean getAgent() {
		return agent;
	}

	public void setAgent(boolean agent) {
		this.agent = agent;
	}

	
   
}
