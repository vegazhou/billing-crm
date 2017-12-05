package com.kt.biz.agent;

import com.kt.biz.bean.AgentRebateBean;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.billing.FeeCalculatorManager;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.biz.impl.BizWebExConf;
import com.kt.biz.types.FeeType;
import com.kt.exception.WafException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Vega on 2017/8/9.
 */
public class RebateAlgorithmWebExPeriodicImpl2 extends AbstractRebateAlgorithm {

    public RebateAlgorithmWebExPeriodicImpl2(AgentRebateBean bean) throws WafException {
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
        return agentPrice.multiply(new BigDecimal(0.9)).multiply(new BigDecimal(1.05));
    }

    @Override
    public BigDecimal computeNonRegisterSettleAmount() throws WafException, ParseException {
        computeIfNecessary();
        if (isMc8OrMc25()) {
            return agentPrice.multiply(new BigDecimal(0.9)).multiply(new BigDecimal(1.4));
        } else {
            return agentPrice.multiply(new BigDecimal(0.9)).multiply(new BigDecimal(1.3));
        }
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


    private boolean isMc8OrMc25() {
        AbstractBizScheme biz = orderBean.getBiz();
        if (biz instanceof BizWebExConf) {
            int ports = ((BizWebExConf) biz).getPorts();
            return ports == 8 || ports == 25;
        }
        return false;
    }
}
