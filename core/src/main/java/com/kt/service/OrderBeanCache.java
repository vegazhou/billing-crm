package com.kt.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.CommonState;
import com.kt.biz.types.PayInterval;
import com.kt.entity.mysql.crm.Order;
import com.kt.exception.InvalidDateFormatException;
import com.kt.exception.WafException;
import com.kt.sys.SpringContextHolder;
import com.kt.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vega Zhou on 2016/4/14.
 */
public class OrderBeanCache {

    private static Cache<String, OrderBean> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(60, TimeUnit.DAYS).maximumSize(1000)
            .build();

    //TODO: 当order，order的计费方案发生变动时，需要将cache过期


    public static OrderBean get(final Order order) {
        try {
            return cache.get(order.getPid(), new Callable<OrderBean>() {
                @Override
                public OrderBean call() throws Exception {
                    return convert2bean(order);
                }
            });
        } catch (ExecutionException e) {
            return null;
        }
    }

    public static void expire(String orderId) {
        cache.invalidate(orderId);
    }


    public static void flush() {
        cache.invalidateAll();
    }



    private static OrderBean convert2bean(Order order) throws WafException {
        BizSchemeService bizSchemeService = SpringContextHolder.getBean(BizSchemeService.class);
        ChargeSchemeService chargeSchemeService = SpringContextHolder.getBean(ChargeSchemeService.class);
        try {
            OrderBean bean = new OrderBean(order.getPid());
            bean.setPayInterval(new PayInterval(order.getPayInterval()));
            bean.setStartDate(DateUtil.toDate(order.getEffectiveStartDate()));
            if (StringUtils.isNotBlank(order.getPlacedDate())) {
                bean.setPlacedDate(DateUtil.toDate(order.getPlacedDate()));
            }
            bean.setEndDate(DateUtil.toDate(order.getEffectiveEndDate()));
            bean.setState(CommonState.valueOf(order.getState()));
            bean.setContractId(order.getContractId());
            bean.setOriginalOrderId(order.getOriginalOrderId());
            bean.setBiz(bizSchemeService.findBizSchemeById(order.getBizId()));
            bean.setChargeScheme(chargeSchemeService.findChargeSchemeById(order.getChargeId()));
            bean.setCustomerId(order.getCustomerId());
            bean.setProductId(order.getProductId());
            return bean;
        } catch (ParseException e) {
            throw new InvalidDateFormatException();
        }
    }
}
