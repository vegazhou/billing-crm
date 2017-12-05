package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public class SiteAlreadyExistedException extends WafException {
    @Override
    public String getKey() {
        return "site_already_existed";
    }
}
