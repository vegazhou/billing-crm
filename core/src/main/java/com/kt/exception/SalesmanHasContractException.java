package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/6/2.
 */
public class SalesmanHasContractException extends WafException {
    @Override
    public String getKey() {
        return "salesman_has_contract";
    }
}
