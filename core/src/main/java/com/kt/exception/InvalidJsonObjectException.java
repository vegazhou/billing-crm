package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class InvalidJsonObjectException extends WafException {
    @Override
    public String getKey() {
        return "invalid_json_object";
    }
}
