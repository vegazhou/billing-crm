package com.kt.biz.model.order;

/**
 * Created by Vega Zhou on 2016/3/21.
 */
public class CollisionDetectResult {
    private boolean ok;

    private String message;

    public static CollisionDetectResult ok() {
        CollisionDetectResult r = new CollisionDetectResult();
        r.setOk(true);
        return r;
    }

    public static CollisionDetectResult error(String errorMessage) {
        CollisionDetectResult r = new CollisionDetectResult();
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
