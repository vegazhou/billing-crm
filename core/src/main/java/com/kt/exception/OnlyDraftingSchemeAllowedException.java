package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class OnlyDraftingSchemeAllowedException extends WafException {
    @Override
    public String getKey() {
        return "only_drafting_scheme_allowed";
    }
}
