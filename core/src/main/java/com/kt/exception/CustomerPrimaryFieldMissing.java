package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/5/31.
 */
public class CustomerPrimaryFieldMissing extends WafException {

    public CustomerPrimaryFieldMissing() {
        super();
    }

    public CustomerPrimaryFieldMissing(String message) {
        super(message);
    }

    @Override
    public String getKey() {
        return "customer_primary_field_missing";
    }
}
