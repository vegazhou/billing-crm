package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/20.
 */
public class OnlyInEffectContractAllowedException extends WafException {
    @Override
    public String getKey() {
        return "only_in_effect_contract_allowed";
    }
}
