package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class ChargeSchemeIsInUsageException extends WafException {
    @Override
    public String getKey() {
        return "charge_scheme_is_in_usage";
    }
}
