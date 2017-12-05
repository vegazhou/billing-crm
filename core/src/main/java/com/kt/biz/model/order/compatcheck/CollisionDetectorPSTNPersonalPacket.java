package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.*;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.util.TimeSpanMerger;
import com.kt.biz.types.ChargeType;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2017/5/9.
 */
public class CollisionDetectorPSTNPersonalPacket extends AbstractPSTNCollisionDetector {

    private TimeSpanMerger merger = new TimeSpanMerger();

    @Override
    List<String> getMySiteNames() {
        return Collections.singletonList(((PSTNPersonalPacket) getMe().getChargeScheme()).getSiteName());
    }

    @Override
    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
        Date endDate = getChargeScheme().getEffectiveBefore();
        if (endDate.before(getMe().getStartDate())) {
            return CollisionDetectResult.error("订单截止日期早于订购起始时间");
        }

        for (OrderBean order : orders) {
            if (order.equals(getMe())) {
                continue;
            }
            AbstractChargeScheme chargeScheme = order.getChargeScheme();
            ChargeType chargeType = chargeScheme.getType();
            switch (chargeType) {
                case PSTN_PERSONAL_CHARGE:
                    tryToMerge((PSTNPersonalCharge) chargeScheme, order);
                    break;
            }
        }

        if (!isPacketTimeSpanAlignWithPstnTimeSpan()) {
            return CollisionDetectResult.error("PSTN个人语音包产品有效时间超出该账户的PSTN个人标准语音产品");
        }


        return CollisionDetectResult.ok();
    }


    private void tryToMerge(PSTNPersonalCharge charge, OrderBean orderBean) {
        if (StringUtils.equals(charge.getSiteName(), getChargeScheme().getSiteName()) &&
                StringUtils.equals(charge.getUserName(), getChargeScheme().getUserName())) {
            merger.addTimeSpan(orderBean.getStartDate(), orderBean.getEndDate());
        }
    }

    private boolean isPacketTimeSpanAlignWithPstnTimeSpan() {
        return merger.covers(getMe().getStartDate(), getMe().getEndDate());
    }


    private PSTNPersonalPacket getChargeScheme() {
        return (PSTNPersonalPacket) getMe().getChargeScheme();
    }
}
