package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.PSTNPersonalCharge;
import com.kt.biz.model.charge.impl.PSTNStandardCharge;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayPersonal;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.util.OrderUtils;
import com.kt.biz.model.order.util.TimeSpanMerger;
import com.kt.biz.types.ChargeType;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2017/5/8.
 */
public class CollisionDetectorPSTNPersonalCharge extends AbstractPSTNCollisionDetector {

    private TimeSpanMerger merger = new TimeSpanMerger();

    @Override
    List<String> getMySiteNames() {
        return Collections.singletonList(((PSTNPersonalCharge) getMe().getChargeScheme()).getSiteName());
    }

    @Override
    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
        Date endDate = ((PSTNPersonalCharge) getMe().getChargeScheme()).getEffectiveBefore();
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
                case MONTHLY_PAY_PERSONAL_WEBEX:
                    tryToMerge((WebExConfMonthlyPayPersonal) chargeScheme, order);
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
        if (!isPstnTimeSpanAlignWithConferenceTimeSpan()) {
            return CollisionDetectResult.error("PSTN个人标准语音产品有效时间超出同站点数据会议产品");
        }

        return CollisionDetectResult.ok();
    }


    private void tryToMerge(WebExConfMonthlyPayPersonal charge, OrderBean orderBean) {
        if (StringUtils.equals(charge.getSiteName(), getChargeScheme().getSiteName()) &&
                StringUtils.equals(charge.getUserName(), getChargeScheme().getUserName())) {
            merger.addTimeSpan(orderBean.getStartDate(), orderBean.getEndDate());
        }
    }


    private boolean isPstnTimeSpanAlignWithConferenceTimeSpan() {
        return merger.covers(getMe().getStartDate(), getMe().getEndDate());
    }


    private CollisionDetectResult detectAgainst(PSTNStandardCharge c) {
        String sameSite = findSameSite(c.getSiteNames(), getChargeScheme().getSiteName());
        if (sameSite != null) {
            return buildSiteLevelPersonalLevelPstnErrorMessage(sameSite);
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult detectAgainst(PSTNPersonalCharge c) {
        if (StringUtils.equalsIgnoreCase(c.getSiteName(), getChargeScheme().getSiteName()) &&
                StringUtils.equalsIgnoreCase(c.getUserName(), getChargeScheme().getUserName())) {
            return buildSiteLevelPersonalLevelPstnErrorMessage(c.getSiteName(), c.getUserName());
        }

        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult buildSiteLevelPersonalLevelPstnErrorMessage(String siteName) {
        return CollisionDetectResult.error("在以下站点上同时存在站点级和个人PSTN标准语音计费产品: " + siteName);
    }

    private CollisionDetectResult buildSiteLevelPersonalLevelPstnErrorMessage(String siteName, String userName) {
        return CollisionDetectResult.error(siteName + "站点上的" + userName + "账户同时存在2个及以上的个人PSTN标准语音计费产品: " );
    }


    private PSTNPersonalCharge getChargeScheme() {
        return (PSTNPersonalCharge) getMe().getChargeScheme();
    }
}
