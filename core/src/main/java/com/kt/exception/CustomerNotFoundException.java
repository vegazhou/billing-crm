package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public class CustomerNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "customer_not_found";
    }
}
