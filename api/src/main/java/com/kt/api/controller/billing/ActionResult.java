package com.kt.api.controller.billing;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 10/10/2016.
 */
public class ActionResult {
    public static final ActionResult SUCCESS;
    public static final ActionResult FAIL;

    static {
        SUCCESS = new ActionResult();
        SUCCESS.setError(false);

        FAIL = new ActionResult();
        FAIL.setError(true);
    }

    private boolean error;
    private String message;
    private Map<String, Object> payload = new HashMap<String, Object>();

    public ActionResult() {
        this.error = false;
    }

    public ActionResult(boolean error, String message) {
        this.error = error;
        this.message = message;
    }


    public ActionResult error(boolean error) {
        this.error = error;
        return this;
    }

    public ActionResult message(String message) {
        this.message = message;
        return this;
    }

    public ActionResult addPayload(String key, Object obj) {
        payload.put(key, obj);
        return this;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
}
