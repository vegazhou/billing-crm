package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class BizSchemeIsInUsageException extends WafException {
    @Override
    public String getKey() {
        return "biz_scheme_is_in_usage";
    }
}
