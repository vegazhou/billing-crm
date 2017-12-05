package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/5/4.
 */
public class InvalidAmountException extends WafException {
    @Override
    public String getKey() {
        return "invalid_amount";
    }
}
