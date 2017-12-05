package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/12/15.
 */
public class NotAnAgentProductException extends WafException {
    @Override
    public String getKey() {
        return "not_an_agent_product";
    }
}
