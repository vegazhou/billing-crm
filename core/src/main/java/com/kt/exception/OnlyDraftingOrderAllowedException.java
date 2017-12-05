package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/22.
 */
public class OnlyDraftingOrderAllowedException extends WafException {
    @Override
    public String getKey() {
        return "only_drafting_order_allowed";
    }
}
