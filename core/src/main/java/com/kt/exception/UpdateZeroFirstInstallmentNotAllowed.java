package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/9/28.
 */
public class UpdateZeroFirstInstallmentNotAllowed extends WafException {
    @Override
    public String getKey() {
        return "update_zero_first_installment_not_allowed";
    }
}
