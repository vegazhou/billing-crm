package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/5/9.
 */
public class ContractAmountLessThanOriginalException extends WafException {
    @Override
    public String getKey() {
        return "contract_amount_less_than_original";
    }
}
