package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/14.
 */
public class EditingNonDraftProductException extends WafException {
    @Override
    public String getKey() {
        return "editing_non_draft_product";
    }
}
