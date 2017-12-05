package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class NotABizSchemeTemplateException extends WafException {
    @Override
    public String getKey() {
        return "not_a_biz_scheme_template";
    }
}
