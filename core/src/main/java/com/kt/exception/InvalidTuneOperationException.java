package com.kt.exception;

/**
 * Created by Vega Zhou on 2017/1/23.
 */
public class InvalidTuneOperationException extends WafException {
    @Override
    public String getKey() {
        return "invalid_tune_operation";
    }
}
