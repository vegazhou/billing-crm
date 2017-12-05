package com.kt.biz.agent;

import com.kt.biz.bean.AgentRebateBean;
import com.kt.exception.WafException;

import java.math.BigDecimal;

/**
 * Created by Vega on 2017/8/9.
 */
public class RebateAlgorithmWebExFirstInstallmentImpl1 extends AbstractRebateAlgorithm {

    public RebateAlgorithmWebExFirstInstallmentImpl1(AgentRebateBean bean) throws WafException {
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
    public BigDecimal computeAgentAmount() {
        computeIfNecessary();
        return agentPrice;
    }

    @Override
    public BigDecimal computeRegisterSettleAmount() throws WafException {
        computeIfNecessary();
        return agentPrice.multiply(new BigDecimal(0.9));
    }

    @Override
    public BigDecimal computeNonRegisterSettleAmount() throws WafException {
        computeIfNecessary();
        return agentPrice.multiply(new BigDecimal(0.93));
    }


    private void computeIfNecessary() {
        if (agentPrice == null) {
            agentPrice = BigDecimal.valueOf(agentChargeScheme.calculateFirstInstallment(orderBean.getStartDate(), orderBean.getPayInterval()));
        }
    }
}
