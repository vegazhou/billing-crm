package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/11.
 */
public class InvalidPortsException extends SchemeValidationException {
    @Override
    public String getKey() {
        return "invalid_ports";
    }
}
