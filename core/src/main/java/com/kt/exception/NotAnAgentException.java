package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/11/18.
 */
public class NotAnAgentException extends WafException {
    @Override
    public String getKey() {
        return "not_an_agent";
    }
}
