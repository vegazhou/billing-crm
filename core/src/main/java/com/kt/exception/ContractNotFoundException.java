package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/10.
 */
public class ContractNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "contract_not_found";
    }
}
