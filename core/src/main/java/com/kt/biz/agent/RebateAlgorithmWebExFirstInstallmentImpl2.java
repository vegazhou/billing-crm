package com.kt.biz.agent;

import com.kt.biz.bean.AgentRebateBean;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.biz.impl.BizWebExConf;
import com.kt.exception.WafException;

import java.math.BigDecimal;

/**
 * Created by Vega on 2017/8/9.
 */
public class RebateAlgorithmWebExFirstInstallmentImpl2 extends AbstractRebateAlgorithm {

    public RebateAlgorithmWebExFirstInstallmentImpl2(AgentRebateBean bean) throws WafException {
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
        return agentPrice.multiply(new BigDecimal(0.9)).multiply(new BigDecimal(1.05));
    }

    @Override
    public BigDecimal computeNonRegisterSettleAmount() throws WafException {
        computeIfNecessary();
        if (isMc8OrMc25()) {
            return agentPrice.multiply(new BigDecimal(0.9)).multiply(new BigDecimal(1.4));
        } else {
            return agentPrice.multiply(new BigDecimal(0.9)).multiply(new BigDecimal(1.3));
        }
    }


    private void computeIfNecessary() {
        if (agentPrice == null) {
            agentPrice = BigDecimal.valueOf(agentChargeScheme.calculateFirstInstallment(orderBean.getStartDate(), orderBean.getPayInterval()));
        }
    }


    private boolean isMc8OrMc25() {
        AbstractBizScheme biz = orderBean.getBiz();
        if (biz instanceof BizWebExConf) {
            int ports = ((BizWebExConf) biz).getPorts();
            return ports == 8 || ports == 25;
        }
        return false;
    }
}
