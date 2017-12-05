package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/19.
 */
public class CustomerHasSignedContractException extends WafException {
    @Override
    public String getKey() {
        return "customer_has_signed_contract";
    }
}
