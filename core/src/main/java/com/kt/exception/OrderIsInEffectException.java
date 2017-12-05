package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/10.
 */
public class OrderIsInEffectException extends WafException {
    @Override
    public String getKey() {
        return "order_is_in_effect";
    }
}
