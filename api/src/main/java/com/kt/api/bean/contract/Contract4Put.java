package com.kt.api.bean.contract;

import javax.validation.constraints.Size;

/**
 * Created by Vega Zhou on 2016/3/19.
 */
public class Contract4Put {

    @Size(min = 0, max = 200, message = "contract.displayName.Size")
    private String displayName;
    
    private String salesMan;
    
    private String agentId;

	private boolean isRegistered;

	private String comments;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

	public String getSalesMan() {
		return salesMan;
	}

	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
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

	public void setIsRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
