package com.kt.exception;

/**
 * Created by Vega Zhou on 2017/5/15.
 */
public class IllegalArgumentException extends WafException {
    @Override
    public String getKey() {
        return "illegal_argument";
    }
}
