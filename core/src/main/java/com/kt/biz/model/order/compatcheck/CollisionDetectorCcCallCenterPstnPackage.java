package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.CcCallCenterPstnCharge;
import com.kt.biz.model.charge.impl.CcCallCenterPstnPackageCharge;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.CollisionDetector;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.util.TimeSpanMerger;
import com.kt.biz.types.ChargeType;

import java.util.Date;
import java.util.List;

/**
 * Created by Vega on 2017/10/11.
 */
public class CollisionDetectorCcCallCenterPstnPackage implements CollisionDetector {

    OrderBean me;

    TimeSpanMerger pstnMerger = new TimeSpanMerger();

    @Override
    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
        CcCallCenterPstnPackageCharge charge = getChargeScheme();
        Date endDate = charge.getEffectiveBefore();
        if (endDate.before(me.getStartDate())) {
            return CollisionDetectResult.error("天客云PSTN语音包产品截止日期早于订购起始时间");
        }

        for (OrderBean order : orders) {
            if (order.equals(me)) {
                continue;
            }
            AbstractChargeScheme chargeScheme = order.getChargeScheme();
            ChargeType chargeType = chargeScheme.getType();
            switch (chargeType) {
                case CC_CALLCENTER_PSTN:
                    pstnMerger.addTimeSpan(order.getStartDate(), order.getEndDate());
                    break;
            }
        }
        if (!pstnMerger.covers(me.getStartDate(), me.getEndDate())) {
            return CollisionDetectResult.error("语音包类产品有部分时间未被PSTN标准费率覆盖");
        }

        return CollisionDetectResult.ok();
    }

    @Override
    public void setMe(OrderBean me) {
        this.me = me;
    }


    CcCallCenterPstnPackageCharge getChargeScheme() {
        return (CcCallCenterPstnPackageCharge)me.getChargeScheme();
    }
}
