package com.kt.exception;

/**
 * Created by Vega Zhou on 2015/10/21.
 */
public abstract class WafException extends Exception {

    public WafException() {
        super();
    }

    public WafException(String message) {
        super(message);
    }

    public WafException(String message, Throwable cause) {
        super(message, cause);
    }

    public WafException(Throwable cause) {
        super(cause);
    }

    abstract public String getKey();
}
