package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/29.
 */
public class WebExSiteDraftNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "webex_site_draft_not_found";
    }
}
