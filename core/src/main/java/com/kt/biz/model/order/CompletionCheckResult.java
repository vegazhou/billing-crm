package com.kt.biz.model.order;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
public class CompletionCheckResult {
    private boolean ok;

    private String message;

    public static CompletionCheckResult buildOkResult() {
        CompletionCheckResult r = new CompletionCheckResult();
        r.setOk(true);
        return r;
    }

    public static CompletionCheckResult buildErrorResult(String errorMessage) {
        CompletionCheckResult r = new CompletionCheckResult();
        r.setOk(false);
        r.setMessage(errorMessage);
        return r;
    }


    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
