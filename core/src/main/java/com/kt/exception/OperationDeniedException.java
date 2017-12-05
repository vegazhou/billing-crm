package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/22.
 */
public class OperationDeniedException extends WafException {
    @Override
    public String getKey() {
        return "operation_denied";
    }
}
