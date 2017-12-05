package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class ChargeSchemeIsNotInEffectException extends WafException {
    @Override
    public String getKey() {
        return "charge_scheme_is_not_in_effect";
    }
}
