package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.charge.impl.CcCallCenterMonthlyPayCharge;
import com.kt.biz.model.charge.impl.PSTNStandardCharge;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.CollisionDetector;
import com.kt.biz.model.order.OrderBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Vega on 2017/10/9.
 */
public class CollisionDetectorCcCallCenter implements CollisionDetector {

    private OrderBean me;

    @Override
    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
        Date endDate = getChargeScheme().getEffectiveBefore();
        if (endDate.before(me.getStartDate())) {
            return CollisionDetectResult.error("截止日期早于订购起始时间");
        }
        return CollisionDetectResult.ok();
    }

    @Override
    public void setMe(OrderBean me) {
        this.me = me;
    }


    public CcCallCenterMonthlyPayCharge getChargeScheme() {
        return (CcCallCenterMonthlyPayCharge)me.getChargeScheme();
    }
}
