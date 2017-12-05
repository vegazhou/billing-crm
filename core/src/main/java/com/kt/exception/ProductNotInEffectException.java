package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/11/18.
 */
public class ProductNotInEffectException extends WafException {
    @Override
    public String getKey() {
        return "product_not_in_effect";
    }
}
