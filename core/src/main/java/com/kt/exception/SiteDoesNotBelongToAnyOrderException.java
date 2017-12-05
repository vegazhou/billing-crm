package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/30.
 */
public class SiteDoesNotBelongToAnyOrderException extends WafException {
    @Override
    public String getKey() {
        return "site_does_not_belong_to_any_order";
    }
}
