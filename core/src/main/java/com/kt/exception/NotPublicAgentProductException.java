package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/12/21.
 */
public class NotPublicAgentProductException extends WafException {
    @Override
    public String getKey() {
        return "not_public_agent_product";
    }
}
