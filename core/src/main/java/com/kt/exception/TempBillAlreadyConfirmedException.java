package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/28.
 */
public class TempBillAlreadyConfirmedException extends WafException {
    @Override
    public String getKey() {
        return "temp_bill_already_confirmed";
    }
}
