package com.kt.biz.model.order;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
public class OrderCompletionCheck {

    public static CompletionCheckResult check(OrderBean order) {
        return order.getChargeScheme().checkCompletion();
    }
}
