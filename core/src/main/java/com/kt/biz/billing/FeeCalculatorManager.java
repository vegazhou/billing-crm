package com.kt.biz.billing;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.order.OrderBean;
import com.kt.entity.mysql.crm.Product;
import com.kt.exception.NotAnAgentProductException;
import com.kt.exception.WafException;
import com.kt.service.ChargeSchemeService;
import com.kt.service.ProductService;
import com.kt.sys.SpringContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
public class FeeCalculatorManager {

    private static final Logger LOGGER = Logger.getLogger(FeeCalculatorManager.class);

    public static FeeCalculator getFeeCalculator(OrderBean orderBean) {
        AbstractChargeScheme chargeScheme = orderBean.getChargeScheme();
        FeeCalculator calculator = null;

        Class<? extends FeeCalculator> clazz = chargeScheme.getType().getFeeCalc();
        if (clazz != null) {

            try {
                // 要求FeeCalc要和chargeScheme一一对应，否则构造函数不一致
                Constructor<? extends FeeCalculator> constructor = clazz.getConstructor(OrderBean.class, chargeScheme.getClass());
                calculator = constructor.newInstance(orderBean, orderBean.getChargeScheme());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                LOGGER.error("instantiate FeeCalculator failed", e);
            }
        }

        return calculator;
    }


    public static FeeCalculator getAgentRebateFeeCalculator(OrderBean orderBean) throws WafException {
        String productId = orderBean.getProductId();
        ProductService productService = SpringContextHolder.getBean(ProductService.class);
        Product product = productService.findById(productId);
        if (StringUtils.isNotBlank(product.getAgentId())) {
            return _getAgentRebateFeeCalculator(orderBean);
        }
        throw new NotAnAgentProductException();
    }

    public static AbstractChargeScheme getAgentRebateChargeScheme(OrderBean orderBean) throws WafException {
        ChargeSchemeService chargeSchemeService = SpringContextHolder.getBean(ChargeSchemeService.class);
        AbstractChargeScheme orderChargeScheme = chargeSchemeService.findChargeSchemeById(orderBean.getChargeScheme().getId());
        ProductService productService = SpringContextHolder.getBean(ProductService.class);
        Product product = productService.findById(orderBean.getProductId());
        AbstractChargeScheme agentChargeScheme = chargeSchemeService.findChargeSchemeById(product.getChargeSchemeId());
        orderChargeScheme.copyChargeElementFrom(agentChargeScheme);
        return orderChargeScheme;
    }

    private static FeeCalculator _getAgentRebateFeeCalculator(OrderBean orderBean) throws WafException {
        AbstractChargeScheme orderChargeScheme = getAgentRebateChargeScheme(orderBean);

        FeeCalculator calculator = null;
        Class<? extends FeeCalculator> clazz = orderChargeScheme.getType().getFeeCalc();
        if (clazz != null) {

            try {
                // 要求FeeCalc要和chargeScheme一一对应，否则构造函数不一致
                Constructor<? extends FeeCalculator> constructor = clazz.getConstructor(OrderBean.class, orderChargeScheme.getClass());
                calculator = constructor.newInstance(orderBean, orderChargeScheme);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                LOGGER.error("instantiate FeeCalculator failed", e);
            }
        }

        return calculator;
    }
}
