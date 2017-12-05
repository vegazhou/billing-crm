package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class ProductNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "product_not_found";
    }
}
