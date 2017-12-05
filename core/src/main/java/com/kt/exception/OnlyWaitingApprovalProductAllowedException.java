package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/22.
 */
public class OnlyWaitingApprovalProductAllowedException extends WafException {
    @Override
    public String getKey() {
        return "only_waiting_approval_product_allowed";
    }
}
