package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/27.
 */
public class FormalBillNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "formal_bill_not_found";
    }
}
