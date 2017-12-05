package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.*;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.CollisionDetector;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.util.OrderUtils;
import com.kt.biz.model.order.util.TimeSpanMerger;
import com.kt.biz.types.ChargeType;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vega Zhou on 2017/4/11.
 */
public class CollisionDetectorWebExStorageOverflow implements CollisionDetector {
    private OrderBean me;

    private Map<String, TimeSpanMerger> mergers = new HashMap<>();

    @Override
    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
        CollisionDetectResult tmp = CollisionDetectResult.ok();
        for (OrderBean order : orders) {
            if (order.equals(me)) {
                continue;
            }
            AbstractChargeScheme chargeScheme = order.getChargeScheme();
            ChargeType chargeType = chargeScheme.getType();
            switch (chargeType) {
                case MONTHLY_PAY_BY_HOSTS:
                    putIntoMergers(((WebExConfMonthlyPayByHosts) chargeScheme).getSiteName(), order);
                    break;
                case MONTHLY_PAY_BY_ACTIVEHOSTS:
                    putIntoMergers(((WebExConfMonthlyPayByAH) chargeScheme).getSiteName(), order);
                    break;
                case MONTHLY_PAY_BY_PORTS:
                    putIntoMergers(((WebExConfMonthlyPayByPorts) chargeScheme).getSiteName(), order);
                    break;
                case MONTHLY_PAY_BY_TOTAL_ATTENDEES:
                    putIntoMergers(((WebExConfMonthlyPayByTotalAttendees) chargeScheme).getSiteName(), order);
                    break;
                case EC_PAY_PER_USE:
                    putIntoMergers(((WebExECPayPerUse) chargeScheme).getSiteName(), order);
                    break;
                case EC_PREPAID:
                    putIntoMergers(((WebExECPrepaid) chargeScheme).getSiteName(), order);
                    break;
                case MONTHLY_PAY_BY_STORAGE:
                    //TODO: 不允许和 存储计费有重叠
                    if (OrderUtils.isTimeOverlapping(me, order)) {
                        tmp = detectAgainst((WebExStorageMonthlyPay) chargeScheme);
                    }
                    break;
                case MONTHLY_PAY_BY_STORAGE_O:
                    //TODO: 不允许和溢出存储计费有重叠
                    if (OrderUtils.isTimeOverlapping(me, order)) {
                        tmp = detectAgainst((WebExStorageMonthlyOverflowPay) chargeScheme);
                    }
                    break;
            }


            if (!tmp.isOk()) {
                return tmp;
            }
        }

        String siteName = this.getChargeScheme().getSiteName();
        TimeSpanMerger merger = getMergerOfSite(siteName);
        if (merger == null) {
            return CollisionDetectResult.error("未找到支持存储产品的数据会议产品");
        } else {
            if (!merger.covers(me.getStartDate(), me.getEndDate())) {
                return CollisionDetectResult.error("WebEx存储产品有效时间超出同站点数据会议产品");
            }
        }

        return CollisionDetectResult.ok();
    }

    @Override
    public void setMe(OrderBean me) {
        this.me = me;
    }


    private CollisionDetectResult detectAgainst(WebExStorageMonthlyOverflowPay c) {
        if (!OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.ok();
        }

        return CollisionDetectResult.error("同站点存在其他存储类计费产品");
    }

    private CollisionDetectResult detectAgainst(WebExStorageMonthlyPay c) {
        if (!OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.ok();
        }

        return CollisionDetectResult.error("同站点存在其他存储类计费产品");
    }


    private void putIntoMergers(String siteName, OrderBean orderBean) {
        TimeSpanMerger merger = getMergerOfSite(siteName);
        if (merger != null) {
            merger.addTimeSpan(orderBean.getStartDate(), orderBean.getEndDate());
        }
    }

    private TimeSpanMerger getMergerOfSite(String siteName) {
        if (StringUtils.isBlank(siteName)) {
            return null;
        }
        String key = siteName.trim().toLowerCase();
        if (this.mergers.containsKey(key)) {
            return mergers.get(key);
        } else {
            TimeSpanMerger merger = new TimeSpanMerger();
            mergers.put(key, merger);
            return merger;
        }
    }

    private WebExStorageMonthlyOverflowPay getChargeScheme() {
        return (WebExStorageMonthlyOverflowPay) me.getChargeScheme();
    }
}