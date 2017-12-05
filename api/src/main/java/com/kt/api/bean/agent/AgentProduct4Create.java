package com.kt.api.bean.agent;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/11/21.
 */
public class AgentProduct4Create {

    private List<String> productIds;

    @NotBlank(message = "agent.product.agentId.NotBlank")
    private String agentId;



    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    
}
