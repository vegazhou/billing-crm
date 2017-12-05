package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.biz.impl.BizWebExConf;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.*;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.CollisionDetector;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.util.OrderUtils;
import com.kt.biz.types.BizType;
import com.kt.biz.types.ChargeType;
import org.apache.commons.lang.StringUtils;


import java.util.List;

/**
 * Created by Vega Zhou on 2017/5/8.
 */
public class CollisionDetectorWebExConfPersonal implements CollisionDetector {

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
                        tmp = detectAgainst((BizWebExConf)order.getBiz(), (WebExConfMonthlyPayByHosts) chargeScheme);
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
                    case MONTHLY_PAY_PERSONAL_WEBEX:
                        tmp = detectAgainst((WebExConfMonthlyPayPersonal) chargeScheme);
                        break;
                }
            }
            if (!tmp.isOk()) {
                return tmp;
            }
        }
        return CollisionDetectResult.ok();
    }

    @Override
    public void setMe(OrderBean me) {
        this.me = me;
    }

    private CollisionDetectResult detectAgainst(WebExConfMonthlyPayPersonal c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName()) &&
                StringUtils.equalsIgnoreCase(c.getUserName(), getChargeScheme().getUserName())) {
            return CollisionDetectResult.error("同站点存在2个及以上个人WebEx会议产品");
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult detectAgainst(WebExECPrepaid c) {
        if (me.getBiz().getType() == BizType.WEBEX_EC && OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.error("同站点存在EC预存计费产品");
        }
        return CollisionDetectResult.ok();
    }


    private CollisionDetectResult detectAgainst(WebExECPayPerUse c) {
        if (me.getBiz().getType() == BizType.WEBEX_EC && OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.error("同站点存在EC按次计费产品");
        }
        return CollisionDetectResult.ok();
    }


    private CollisionDetectResult detectAgainst(BizWebExConf b, WebExConfMonthlyPayByHosts c) {
        if (me.getBiz().getType() == BizType.WEBEX_EC && OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.error("同站点存在按用户数计费类产品");
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult detectAgainst(WebExConfMonthlyPayByPorts c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.error("同站点存在按并发数计费类产品");
        }
        return CollisionDetectResult.ok();
    }

    private CollisionDetectResult detectAgainst(WebExConfMonthlyPayByTotalAttendees c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.error("同站点存在按总参会人次计费类产品");
        }
        return CollisionDetectResult.ok();
    }


    private CollisionDetectResult detectAgainst(WebExConfMonthlyPayByAH c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.error("同站点存在按激活用户数计费类产品");
        }
        return CollisionDetectResult.ok();
    }

    private WebExConfMonthlyPayPersonal getChargeScheme() {
        return (WebExConfMonthlyPayPersonal) me.getChargeScheme();
    }
}
