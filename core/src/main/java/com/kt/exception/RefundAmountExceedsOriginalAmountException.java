package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/27.
 */
public class RefundAmountExceedsOriginalAmountException extends WafException {
    @Override
    public String getKey() {
        return "refund_amount_exceeds_original_amount";
    }
}
