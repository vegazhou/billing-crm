package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.*;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.util.OrderUtils;
import com.kt.biz.types.ChargeType;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/30.
 */
public class CollisionDetectorPSTNStandardCharge extends AbstractPSTNCollisionDetector {
    @Override
    List<String> getMySiteNames() {
        return ((PSTNStandardCharge)getMe().getChargeScheme()).getSiteNames();
    }

    @Override
    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
        Date endDate = ((PSTNStandardCharge)getMe().getChargeScheme()).getEffectiveBefore();
        if (endDate.before(getMe().getStartDate())) {
            return CollisionDetectResult.error("PSTN标准语音产品截止日期早于订购起始时间");
        }

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
                    if (OrderUtils.isTimeOverlapping(getMe(), order)) {
                        CollisionDetectResult detectResult = detectAgainst((PSTNStandardCharge) chargeScheme);
                        if (!detectResult.isOk()) {
                            return detectResult;
                        }
                    }
                    break;
                case PSTN_PERSONAL_CHARGE:
                    if (OrderUtils.isTimeOverlapping(getMe(), order)) {
                        CollisionDetectResult detectResult = detectAgainst((PSTNPersonalCharge) chargeScheme);
                        if (!detectResult.isOk()) {
                            return detectResult;
                        }
                    }
                    break;
            }
        }
        if (!isMyTimeSpanAlignWithConferenceTimeSpan()) {
            return CollisionDetectResult.error("PSTN标准语音产品有效时间超出同站点数据会议产品");
        }

        return CollisionDetectResult.ok();
    }


    private PSTNStandardCharge getChargeScheme() {
        return (PSTNStandardCharge) getMe().getChargeScheme();
    }


    private CollisionDetectResult detectAgainst(PSTNStandardCharge c) {
        String sameSite = findSameSite(c.getSiteNames(), getChargeScheme().getSiteNames());
        if (sameSite != null) {
            return buildDefaultErrorResult(sameSite);
        }
        return CollisionDetectResult.ok();
    }


    private CollisionDetectResult detectAgainst(PSTNPersonalCharge c) {
        String sameSite = findSameSite(Collections.singletonList(c.getSiteName()), getChargeScheme().getSiteNames());
        if (sameSite != null) {
            return buildDefaultErrorResult(sameSite);
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult buildDefaultErrorResult(String siteName) {
        return CollisionDetectResult.error("在以下站点上同时存在两个或以上PSTN标准语音计费产品: " + siteName);
    }



}
