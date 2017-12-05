package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/27.
 */
public class TempBillNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "temp_bill_not_found";
    }
}
