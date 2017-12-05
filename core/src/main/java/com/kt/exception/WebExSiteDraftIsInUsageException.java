package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/30.
 */
public class WebExSiteDraftIsInUsageException extends WafException {
    @Override
    public String getKey() {
        return "site_draft_is_in_usage";
    }
}
