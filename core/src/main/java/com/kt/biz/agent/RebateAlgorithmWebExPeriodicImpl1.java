package com.kt.biz.agent;

import com.kt.biz.bean.AgentRebateBean;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.billing.FeeCalculatorManager;
import com.kt.biz.types.FeeType;
import com.kt.exception.WafException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Vega on 2017/8/9.
 */
public class RebateAlgorithmWebExPeriodicImpl1 extends AbstractRebateAlgorithm {

    public RebateAlgorithmWebExPeriodicImpl1(AgentRebateBean bean) throws WafException {
        super(bean);
    }


    private transient BigDecimal agentPrice = null;

    @Override
    public BigDecimal computeUnitPrice() {
        return BigDecimal.valueOf(bean.getUnitPrice());
    }

    @Override
    public BigDecimal computeAgentUnitPrice() {
        return BigDecimal.valueOf(bean.getAgentUnitPrice());
    }

    @Override
    public BigDecimal computeAgentAmount() throws WafException, ParseException {
        computeIfNecessary();
        return agentPrice;
    }

    @Override
    public BigDecimal computeRegisterSettleAmount() throws WafException, ParseException {
        computeIfNecessary();
        return agentPrice.multiply(new BigDecimal(0.9));
    }

    @Override
    public BigDecimal computeNonRegisterSettleAmount() throws WafException, ParseException {
        computeIfNecessary();
        return agentPrice.multiply(new BigDecimal(0.93));
    }


    private void computeIfNecessary() throws WafException, ParseException {
        if (agentPrice != null) {
            return;
        }

        FeeCalculator calc = FeeCalculatorManager.getAgentRebateFeeCalculator(orderBean);
        List<BillItem> billItems = calc.calculatePrepaidDue(new AccountPeriod(bean.getAccountPeriod()));
        if (billItems == null || billItems.size() == 0) {
            return;
        }
        FeeType feeType = FeeType.valueOf(bean.getFeeType());
        for (BillItem billItem : billItems) {
            if (billItem.getFeeType() == feeType) {
                agentPrice = BigDecimal.valueOf(billItem.getAmount().floatValue());
            }
        }
    }
}
