package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/13.
 */
public class BillingTaskIsRunningException extends WafException {
    @Override
    public String getKey() {
        return "billing_task_is_running";
    }
}
