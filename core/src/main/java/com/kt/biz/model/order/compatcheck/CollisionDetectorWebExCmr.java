package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.*;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.CollisionDetector;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.util.TimeSpanMerger;
import com.kt.biz.types.BizType;
import com.kt.biz.types.ChargeType;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vega Zhou on 2016/10/20.
 */
public class CollisionDetectorWebExCmr implements CollisionDetector {
    private OrderBean me;

    private Map<String, TimeSpanMerger> mergers = new HashMap<>();


    @Override
    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
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
            }
        }

        String siteName = this.getChargeScheme().getSiteName();
        TimeSpanMerger merger = getMergerOfSite(siteName);
        if (merger == null) {
            return CollisionDetectResult.error("未找到支持CMR产品的MC产品");
        } else {
            if (!merger.covers(me.getStartDate(), me.getEndDate())) {
                return CollisionDetectResult.error("WebEx CMR产品有效时间超出同站点MC产品");
            }
        }

        return CollisionDetectResult.ok();
    }

    @Override
    public void setMe(OrderBean me) {
        this.me = me;
    }

    private WebExCmrMonthlyPay getChargeScheme() {
        return (WebExCmrMonthlyPay) me.getChargeScheme();
    }


    private void putIntoMergers(String siteName, OrderBean orderBean) {
        if (orderBean.getBiz().getType() == BizType.WEBEX_MC || orderBean.getBiz().getType() == BizType.WEBEX_EE) {
            //CMR 只能存在于MC或EE的站点上
            TimeSpanMerger merger = getMergerOfSite(siteName);
            if (merger != null) {
                merger.addTimeSpan(orderBean.getStartDate(), orderBean.getEndDate());
            }
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
}
