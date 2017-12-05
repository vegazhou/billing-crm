package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.*;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.ChargeType;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/21.
 */
public class CollisionDetectorPSTNMonthlyPacket extends AbstractPSTNCollisionDetector {


    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
        for (OrderBean order : orders) {
            if (order.equals(getMe())) {
                continue;
            }
            AbstractChargeScheme chargeScheme = order.getChargeScheme();
            ChargeType chargeType = chargeScheme.getType();
            switch (chargeType) {
                case MONTHLY_PAY_BY_HOSTS:
                    putIntoConferenceMergers(((WebExConfMonthlyPayByHosts) chargeScheme).getSiteName(), order);
                    break;
                case MONTHLY_PAY_BY_ACTIVEHOSTS:
                    putIntoConferenceMergers(((WebExConfMonthlyPayByAH) chargeScheme).getSiteName(), order);
                    break;
                case MONTHLY_PAY_BY_PORTS:
                    putIntoConferenceMergers(((WebExConfMonthlyPayByPorts) chargeScheme).getSiteName(), order);
                    break;
                case MONTHLY_PAY_BY_TOTAL_ATTENDEES:
                    putIntoConferenceMergers(((WebExConfMonthlyPayByTotalAttendees) chargeScheme).getSiteName(), order);
                    break;
                case EC_PAY_PER_USE:
                    putIntoConferenceMergers(((WebExECPayPerUse) chargeScheme).getSiteName(), order);
                    break;
                case EC_PREPAID:
                    putIntoConferenceMergers(((WebExECPrepaid) chargeScheme).getSiteName(), order);
                    break;
                case PSTN_STANDARD_CHARGE:
                    putIntoPSTNMergers(((PSTNStandardCharge) chargeScheme).getSiteNames(), order);
                    break;
                case PSTN_PACKAGE_CHARGE:
                    putIntoPSTNMergers(((PSTNStandardCharge) chargeScheme).getSiteNames(), order);
                    break;
            }
        }
        if (!isMyTimeSpanAlignWithConferenceTimeSpan()) {
            return CollisionDetectResult.error("PSTN语音包月产品有效时间超出同站点数据会议产品");
        }
        if (!isMyTimeSpanAlignWithPSTNTimeSpan()) {
            return CollisionDetectResult.error("PSTN语音包月产品有效时间超出同站点PSTN标准语音产品");
        }

        return CollisionDetectResult.ok();
    }


    @Override
    List<String> getMySiteNames() {
        return ((PSTNMonthlyPacketCharge)getMe().getChargeScheme()).getSiteNames();
    }

}
