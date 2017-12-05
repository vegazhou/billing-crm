package com.kt.biz.agent;

import com.kt.biz.bean.AgentRebateBean;
import com.kt.biz.billing.FeeCalculatorManager;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.order.OrderBean;
import com.kt.exception.WafException;
import com.kt.service.OrderService;
import com.kt.sys.SpringContextHolder;

/**
 * Created by Vega on 2017/8/9.
 */
public abstract class AbstractRebateAlgorithm implements RebateAlgorithm {
    protected AgentRebateBean bean;

    protected transient OrderBean orderBean;

    protected transient AbstractChargeScheme agentChargeScheme ;

    public AbstractRebateAlgorithm(AgentRebateBean bean) throws WafException {
        this.bean = bean;
        init();
    }

    private void init() throws WafException {
        orderBean = findOrderBean();
        agentChargeScheme = FeeCalculatorManager.getAgentRebateChargeScheme(orderBean);
    }

    protected OrderBean findOrderBean() throws WafException {
        OrderService orderService = SpringContextHolder.getBean(OrderService.class);
        return orderService.findOrderBeanById(bean.getOrderId());
    }
}
