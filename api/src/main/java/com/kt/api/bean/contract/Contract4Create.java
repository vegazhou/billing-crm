package com.kt.api.bean.contract;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by Vega Zhou on 2016/3/19.
 */
public class Contract4Create {
    @NotBlank(message = "contract.customerId.NotBlank")
    private String customerId;

    @Size(min = 0, max = 200, message = "contract.displayName.Size")
    @NotBlank(message = "contract.displayName.NotBlank")
    private String displayName;

    @NotBlank(message = "contract.salesmanId.NotBlank")
    private String salesmanId;

    private String agentId;

    private boolean isRegistered;

    private String comments;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
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
