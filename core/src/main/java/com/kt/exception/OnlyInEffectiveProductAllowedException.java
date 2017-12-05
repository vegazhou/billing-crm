package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
public class OnlyInEffectiveProductAllowedException extends WafException {
    @Override
    public String getKey() {
        return "only_in_effective_product_allowed";
    }
}
