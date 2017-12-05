package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/19.
 */
public class InvalidOrderStartDateException extends WafException {
    @Override
    public String getKey() {
        return "invalid_order_start_date";
    }
}
