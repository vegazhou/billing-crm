package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/21.
 */
public class OnlyDraftingContractAllowedException extends WafException {
    @Override
    public String getKey() {
        return "only_drafting_contract_allowed";
    }
}
