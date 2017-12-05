package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/10.
 */
public class ContractIsInEffectException extends WafException {
    @Override
    public String getKey() {
        return "contract_is_in_effect";
    }
}
