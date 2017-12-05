package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.CcCallCenterPstnCharge;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.CollisionDetector;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.util.OrderUtils;
import com.kt.biz.model.order.util.TimeSpanMerger;
import com.kt.biz.types.ChargeType;

import java.util.Date;
import java.util.List;

/**
 * Created by Vega on 2017/10/10.
 */
public class CollisionDetectorCcCallCenterPstn implements CollisionDetector {
    OrderBean me;

    TimeSpanMerger merger = new TimeSpanMerger();

    @Override
    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
        CcCallCenterPstnCharge charge = getChargeScheme();
        Date endDate = charge.getEffectiveBefore();
        if (endDate.before(me.getStartDate())) {
            return CollisionDetectResult.error("天客云PSTN标准语音产品截止日期早于订购起始时间");
        }

        for (OrderBean order : orders) {
            if (order.equals(me)) {
                continue;
            }
            AbstractChargeScheme chargeScheme = order.getChargeScheme();
            ChargeType chargeType = chargeScheme.getType();
            switch (chargeType) {
                case CC_CALLCENTER_MONTHLY_PAY:
                    merger.addTimeSpan(order.getStartDate(), order.getEndDate());
                    break;
                case CC_CALLCENTER_PSTN:
                    if (OrderUtils.isTimeOverlapping(me, order)) {
                        return CollisionDetectResult.error("同时存在两个或以上的天客云PSTN标准语音计费产品");
                    }
                    break;
                default:
            }
        }

        if (!merger.covers(me.getStartDate(), me.getEndDate())) {
            return CollisionDetectResult.error("天客云PSTN标准语音产品有效时间超出主产品");
        }

        return CollisionDetectResult.ok();
    }

    @Override
    public void setMe(OrderBean me) {
        this.me = me;
    }

    CcCallCenterPstnCharge getChargeScheme() {
        return (CcCallCenterPstnCharge)me.getChargeScheme();
    }

}
