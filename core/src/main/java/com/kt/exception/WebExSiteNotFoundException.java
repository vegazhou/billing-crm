package com.kt.exception;

/**
 * Created by Garfield Chen on 2016/5/19.
 */
public class WebExSiteNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "webex_site_not_found";
    }
}
