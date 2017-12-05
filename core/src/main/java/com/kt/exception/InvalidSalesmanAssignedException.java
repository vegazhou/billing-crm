package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/6/2.
 */
public class InvalidSalesmanAssignedException extends WafException {
    @Override
    public String getKey() {
        return "invalid_salesman_assigned";
    }
}
