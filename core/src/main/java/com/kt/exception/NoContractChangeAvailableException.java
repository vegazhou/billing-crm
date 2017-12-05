package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/20.
 */
public class NoContractChangeAvailableException extends WafException {
    @Override
    public String getKey() {
        return "no_contract_change_available";
    }
}
