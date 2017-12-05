package com.kt.biz.agent;

import com.kt.exception.WafException;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Created by Vega on 2017/8/9.
 */
public interface RebateAlgorithm {
    BigDecimal computeUnitPrice() throws ParseException;

    BigDecimal computeAgentUnitPrice() throws ParseException;

    BigDecimal computeAgentAmount() throws WafException, ParseException;

    BigDecimal computeRegisterSettleAmount() throws WafException, ParseException;

    BigDecimal computeNonRegisterSettleAmount() throws WafException, ParseException;
}
