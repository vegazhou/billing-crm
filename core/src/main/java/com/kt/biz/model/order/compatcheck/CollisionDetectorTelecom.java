package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.*;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.CollisionDetector;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.util.OrderUtils;
import com.kt.biz.types.ChargeType;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/14.
 */
public class CollisionDetectorTelecom implements CollisionDetector {

    private OrderBean me;

    @Override
    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
        CollisionDetectResult tmp = CollisionDetectResult.ok();
        for (OrderBean order : orders) {
            if (order.equals(me)) {
                continue;
            }
            if (OrderUtils.isTimeOverlapping(me, order)) {
                AbstractChargeScheme chargeScheme = order.getChargeScheme();
                ChargeType chargeType = chargeScheme.getType();
                switch (chargeType) {
                    case MONTHLY_PAY_BY_HOSTS:
                        tmp = detectAgainst((WebExConfMonthlyPayByHosts) chargeScheme);
                        break;
                    case MONTHLY_PAY_BY_ACTIVEHOSTS:
                        tmp = detectAgainst((WebExConfMonthlyPayByAH) chargeScheme);
                        break;
                    case MONTHLY_PAY_BY_PORTS:
                        tmp = detectAgainst((WebExConfMonthlyPayByPorts) chargeScheme);
                        break;
                    case MONTHLY_PAY_BY_TOTAL_ATTENDEES:
                        tmp = detectAgainst((WebExConfMonthlyPayByTotalAttendees) chargeScheme);
                        break;
                    case EC_PAY_PER_USE:
                        tmp = detectAgainst((WebExECPayPerUse) chargeScheme);
                        break;
                    case EC_PREPAID:
                        tmp = detectAgainst((WebExECPrepaid) chargeScheme);
                        break;
                    case MONTHLY_PAY_BY_STORAGE:
                        tmp = detectAgainst((WebExStorageMonthlyPay) chargeScheme);
                        break;
                    case MONTHLY_PAY_BY_STORAGE_O:
                        tmp = detectAgainst((WebExStorageMonthlyOverflowPay) chargeScheme);
                        break;
                    case PSTN_STANDARD_CHARGE:
                        tmp = detectAgainst((PSTNStandardCharge) chargeScheme);
                        break;
                    case PSTN_PACKAGE_CHARGE:
                        tmp = detectAgainst((PSTNStandardCharge) chargeScheme);
                        break;
                    case PSTN_PERSONAL_CHARGE:
                        tmp = detectAgainst((PSTNPersonalCharge) chargeScheme);
                        break;
                    case PSTN_MONTHLY_PACKET:
                        tmp = detectAgainst((PSTNMonthlyPacketCharge) chargeScheme);
                        break;
                    case PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES:
                        tmp = detectAgainst((PSTNSinglePacket4MultiSites) chargeScheme);
                        break;
                    case PSTN_SINGLE_PACKET_FOR_ALL_SITES:
                        tmp = detectAgainst((PSTNSinglePacket4AllSites) chargeScheme);
                        break;
                    case TELECOM_CHARGE:
                        break;
                    case MISC_CHARGE:
                        break;
                }
            }
            if (!tmp.isOk()) {
                return tmp;
            }
        }
        return CollisionDetectResult.ok();
    }


    private CollisionDetectResult detectAgainst(WebExConfMonthlyPayByAH c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(c.getSiteName());
        }
        return CollisionDetectResult.ok();
    }


    private CollisionDetectResult detectAgainst(WebExECPrepaid c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(c.getSiteName());
        }
        return CollisionDetectResult.ok();
    }


    private CollisionDetectResult detectAgainst(WebExECPayPerUse c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(c.getSiteName());
        }
        return CollisionDetectResult.ok();
    }


    private CollisionDetectResult detectAgainst(WebExConfMonthlyPayByHosts c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(c.getSiteName());
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult detectAgainst(WebExConfMonthlyPayByPorts c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(c.getSiteName());
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult detectAgainst(WebExConfMonthlyPayByTotalAttendees c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(c.getSiteName());
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult detectAgainst(WebExStorageMonthlyPay c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(c.getSiteName());
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult detectAgainst(WebExStorageMonthlyOverflowPay c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(c.getSiteName());
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult detectAgainst(PSTNStandardCharge c) {
        if (hasSameSite(c.getSiteNames(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(getChargeScheme().getSiteName());
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult detectAgainst(PSTNPersonalCharge c) {
        if (hasSameSite(Collections.singletonList(c.getSiteName()), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(getChargeScheme().getSiteName());
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult detectAgainst(PSTNMonthlyPacketCharge c) {
        if (hasSameSite(c.getSiteNames(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(getChargeScheme().getSiteName());
        }
        return CollisionDetectResult.ok();
    }


    private CollisionDetectResult detectAgainst(PSTNSinglePacket4MultiSites c) {
        if (hasSameSite(c.getSiteNames(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(getChargeScheme().getSiteName());
        }
        return CollisionDetectResult.ok();
    }


    private CollisionDetectResult detectAgainst(PSTNSinglePacket4AllSites c) {
        if (hasSameSite(c.getSiteNames(), getChargeScheme().getSiteName())) {
            return buildDefaultErrorResult(getChargeScheme().getSiteName());
        }
        return CollisionDetectResult.ok();
    }




    private boolean hasSameSite(List<String> siteNames, String testedTarget) {
        for (String siteName : siteNames) {
            if (OrderUtils.isSameSite(siteName, testedTarget)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void setMe(OrderBean me) {
        this.me = me;
    }

    private TelecomCharge getChargeScheme() {
        return (TelecomCharge) me.getChargeScheme();
    }


    private CollisionDetectResult buildDefaultErrorResult(String siteName) {
        return CollisionDetectResult.error("电信会议通业务不能在以下站点上和其他WebEx业务并存: " + siteName);
    }
}
