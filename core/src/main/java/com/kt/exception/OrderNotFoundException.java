package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/10.
 */
public class OrderNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "order_not_found";
    }
}
