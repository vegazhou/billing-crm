package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/26.
 */
public class DeletingOriginalOrderIsNotAllowedException extends WafException {
    @Override
    public String getKey() {
        return "deleting_original_order_is_not_allowed";
    }
}
