package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/11/18.
 */
public class MoreThanOneAgentProductInEffect extends WafException {
    @Override
    public String getKey() {
        return "more_than_one_agent_product_in_effect";
    }
}
