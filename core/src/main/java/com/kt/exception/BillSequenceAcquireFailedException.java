package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/6/2.
 */
public class BillSequenceAcquireFailedException extends WafException {
    @Override
    public String getKey() {
        return "bill_sequence_acquire_failed";
    }
}
