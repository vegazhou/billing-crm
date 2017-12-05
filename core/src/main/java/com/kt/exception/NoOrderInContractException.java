package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
public class NoOrderInContractException extends WafException {
    @Override
    public String getKey() {
        return "no_order_in_contract";
    }
}
