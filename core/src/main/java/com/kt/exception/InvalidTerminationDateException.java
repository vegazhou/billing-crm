package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/22.
 */
public class InvalidTerminationDateException extends WafException {
    @Override
    public String getKey() {
        return "invalid_termination_date";
    }
}
