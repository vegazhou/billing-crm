package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/10.
 */
public class BizSchemeNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "biz_scheme_not_found";
    }
}
