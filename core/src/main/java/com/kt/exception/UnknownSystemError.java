package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/27.
 */
public class UnknownSystemError extends WafException {

    public UnknownSystemError() {
        super();
    }

    public UnknownSystemError(String message) {
        super(message);
    }

    public UnknownSystemError(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownSystemError(Throwable cause) {
        super(cause);
    }

    @Override
    public String getKey() {
        return "unknown_system_error";
    }
}
