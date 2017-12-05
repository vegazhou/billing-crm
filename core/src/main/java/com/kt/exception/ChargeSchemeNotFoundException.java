package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/10.
 */
public class ChargeSchemeNotFoundException extends WafException {

    @Override
    public String getKey() {
        return "charge_scheme_not_found";
    }
}
