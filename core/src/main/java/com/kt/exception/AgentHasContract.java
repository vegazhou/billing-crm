package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/11/23.
 */
public class AgentHasContract extends WafException {
    @Override
    public String getKey() {
        return "agent_has_contract";
    }
}
