package com.kt.api.bean.product;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class Product4Get {
	
	private String pid;

    private String displayName;

    private String bizId;

    private String chargeSchemeId;
    
    private boolean isTrial;
    
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isTrial() {
		return isTrial;
	}

	public void setTrial(boolean isTrial) {
		this.isTrial = isTrial;
	}

	public boolean getAgent() {
		return agent;
	}

	public void setAgent(boolean agent) {
		this.agent = agent;
	}
    
    
}
