package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/9.
 */
public class IllegalChargeSchemeArgumentException extends WafException {
    @Override
    public String getKey() {
        return "illegal_charge_scheme_argument";
    }
}
