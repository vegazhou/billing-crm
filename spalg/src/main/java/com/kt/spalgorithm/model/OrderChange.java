package com.kt.spalgorithm.model;

import com.kt.biz.model.order.OrderBean;
import com.kt.spalgorithm.types.ChangeType;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class OrderChange {
    private ChangeType changeType;

    private OrderBean order;

    public OrderChange() {
        changeType = ChangeType.START;
    }

    public OrderChange(ChangeType changeType, OrderBean order) {
        this.changeType = changeType;
        this.order = order;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }
}
