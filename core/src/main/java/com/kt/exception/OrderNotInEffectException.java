package com.kt.exception;

/**
 * Created by Rickey Zhu on 2016/6/21.
 */
public class OrderNotInEffectException extends WafException {
    @Override
    public String getKey() {
        return "order_not_in_effect";
    }
}
