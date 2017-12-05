package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
public class OnlyWaitingApprovalContractAllowedException extends WafException {
    @Override
    public String getKey() {
        return "only_waiting_approval_contract_allowed";
    }
}
