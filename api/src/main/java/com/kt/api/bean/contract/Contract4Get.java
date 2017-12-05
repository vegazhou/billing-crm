package com.kt.api.bean.contract;

/**
 * Created by Vega Zhou on 2016/3/19.
 */
public class Contract4Get {

    private String id;

    private String displayName;

    private String customerId;
    
    private String salesManId;

    private String agentId;
    
    private boolean isRegistered;

	private String comments;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

	public String getSalesManId() {
		return salesManId;
	}

	public void setSalesManId(String salesManId) {
		this.salesManId = salesManId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
    
    
}
