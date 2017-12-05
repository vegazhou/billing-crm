package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/29.
 */
public class InvalidLanguageValueException extends WafException {
    @Override
    public String getKey() {
        return "invalid_language_value";
    }
}
