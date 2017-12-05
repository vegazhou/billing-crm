package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/12.
 */
public class SchemeNotInWaitingListException extends WafException {
    @Override
    public String getKey() {
        return "scheme_not_in_waiting_list";
    }
}
