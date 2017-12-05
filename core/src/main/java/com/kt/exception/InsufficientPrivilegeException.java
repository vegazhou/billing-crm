package com.kt.exception;

/**
 * Created by Vega Zhou on 2015/11/1.
 */
public class InsufficientPrivilegeException extends WafException {

    @Override
    public String getKey() {
        return "insufficient_privilege";
    }
}
