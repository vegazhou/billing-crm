package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/22.
 */
public class OnlyDraftingProductAllowedException extends WafException {
    @Override
    public String getKey() {
        return "only_drafting_product_allowed";
    }
}
