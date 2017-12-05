package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
public class DuplicatedWebExSiteDraftException extends WafException {
    @Override
    public String getKey() {
        return "duplicated_webex_site_draft";
    }
}
