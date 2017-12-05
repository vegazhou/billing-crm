package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/25.
 */
public class UpdatingOriginalOrderNotAllowedException extends WafException {
    @Override
    public String getKey() {
        return "updating_original_order_not_allowed";
    }
}
