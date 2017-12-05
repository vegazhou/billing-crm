package com.kt.sso.client.exception;

/**
 * Created by Vega Zhou on 2015/10/13.
 */
public class SsoClientException extends Exception {
    public SsoClientException() {
        super();
    }

    public SsoClientException(String message) {
        super(message);
    }

    public SsoClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public SsoClientException(Throwable cause) {
        super(cause);
    }
}
