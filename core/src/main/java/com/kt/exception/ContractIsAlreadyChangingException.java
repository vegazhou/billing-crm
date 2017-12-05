package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/20.
 */
public class ContractIsAlreadyChangingException extends WafException {
    @Override
    public String getKey() {
        return "contract_is_already_changing";
    }
}
