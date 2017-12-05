package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/6/1.
 */
public class PrimaryPropertyMissingException extends WafException {

    public PrimaryPropertyMissingException() {
        super();
    }

    public PrimaryPropertyMissingException(String message) {
        super(message);
    }

    @Override
    public String getKey() {
        return "primary_property_missing";
    }
}
