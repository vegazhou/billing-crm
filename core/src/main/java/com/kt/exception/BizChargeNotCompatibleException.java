package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/22.
 */
public class BizChargeNotCompatibleException extends WafException {
    @Override
    public String getKey() {
        return "biz_charge_not_compatible";
    }
}
