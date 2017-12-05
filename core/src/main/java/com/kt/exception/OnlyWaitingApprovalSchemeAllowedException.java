package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/14.
 */
public class OnlyWaitingApprovalSchemeAllowedException extends WafException {
    @Override
    public String getKey() {
        return "only_waiting_approval_scheme_allowed";
    }
}
