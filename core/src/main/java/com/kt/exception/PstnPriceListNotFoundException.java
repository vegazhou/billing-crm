package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/4/28.
 */
public class PstnPriceListNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "pstn_price_list_not_found";
    }
}
