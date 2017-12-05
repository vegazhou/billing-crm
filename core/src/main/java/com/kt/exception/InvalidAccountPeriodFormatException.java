package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/5/4.
 */
public class InvalidAccountPeriodFormatException extends WafException {
    public InvalidAccountPeriodFormatException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getKey() {
        return "invalid_account_period_format";
    }
}
