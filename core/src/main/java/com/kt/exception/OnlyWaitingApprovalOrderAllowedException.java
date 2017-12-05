package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
public class OnlyWaitingApprovalOrderAllowedException extends WafException {
    @Override
    public String getKey() {
        return "only_waiting_approval_order_allowed";
    }
}
