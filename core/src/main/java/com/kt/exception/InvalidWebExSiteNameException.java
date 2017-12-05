package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public class InvalidWebExSiteNameException extends WafException {
    @Override
    public String getKey() {
        return "invalid_webex_sitename";
    }
}
