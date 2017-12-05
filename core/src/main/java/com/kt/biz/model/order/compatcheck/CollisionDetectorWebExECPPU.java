package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.biz.impl.BizWebExConf;
import com.kt.biz.model.biz.impl.BizWebExEC;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.*;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.CollisionDetector;
import com.kt.biz.model.order.util.OrderUtils;
import com.kt.biz.types.BizType;
import com.kt.biz.types.ChargeType;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/21.
 */
public class CollisionDetectorWebExECPPU implements CollisionDetector {
    private OrderBean me;

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
                        tmp = detectAgainst(getBiz(), (WebExConfMonthlyPayByHosts) chargeScheme);
                        break;
                    case MONTHLY_PAY_BY_ACTIVEHOSTS:
                        tmp = detectAgainst((WebExConfMonthlyPayByAH) chargeScheme);
                        break;
                    case MONTHLY_PAY_BY_PORTS:
                        tmp = detectAgainst(getBiz(), (WebExConfMonthlyPayByPorts) chargeScheme);
                        break;
                    case MONTHLY_PAY_BY_TOTAL_ATTENDEES:
                        tmp = detectAgainst(getBiz(), (WebExConfMonthlyPayByTotalAttendees) chargeScheme);
                        break;
                    case EC_PAY_PER_USE:
                        tmp = detectAgainst(getBiz(), (WebExECPayPerUse) chargeScheme);
                        break;
                    case EC_PREPAID:
                        tmp = detectAgainst(getBiz(), (WebExECPrepaid) chargeScheme);
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


    private CollisionDetectResult detectAgainst(WebExConfMonthlyPayPersonal c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.error("同站点存在个人WebEx会议产品");
        }
        return CollisionDetectResult.ok();
    }
    

    private CollisionDetectResult detectAgainst(BizWebExConf b, WebExECPrepaid c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.error("同站点存在EC预存产品");
        } else {
            return CollisionDetectResult.ok();
        }
    }


    private CollisionDetectResult detectAgainst(BizWebExConf b, WebExECPayPerUse c) {
        if (!OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.ok();
        }
        if (b.getPorts() == getBiz().getPorts()) {
            return CollisionDetectResult.ok();
        }
        return CollisionDetectResult.error("同站点存在不同方数EC按次计费产品");
    }


    private CollisionDetectResult detectAgainst(BizWebExConf b, WebExConfMonthlyPayByHosts c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName()) && b.getType() == BizType.WEBEX_EC) {
            return CollisionDetectResult.error("同站点存在EC按用户数计费产品");
        } else {
            return CollisionDetectResult.ok();
        }
    }

    private CollisionDetectResult detectAgainst(BizWebExConf b, WebExConfMonthlyPayByPorts c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName()) && b.getType() == BizType.WEBEX_EC) {
            return CollisionDetectResult.error("同站点存在EC按并发数计费产品");
        } else {
            return CollisionDetectResult.ok();
        }
    }

    private CollisionDetectResult detectAgainst(BizWebExConf b, WebExConfMonthlyPayByTotalAttendees c) {
        if (OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName()) && b.getType() == BizType.WEBEX_EC) {
            return CollisionDetectResult.error("同站点存在EC按总参会人数计费产品");
        } else {
            return CollisionDetectResult.ok();
        }
    }


    private CollisionDetectResult detectAgainst(WebExConfMonthlyPayByAH c) {
        if (!OrderUtils.isSameSite(c.getSiteName(), getChargeScheme().getSiteName())) {
            return CollisionDetectResult.ok();
        }

        return CollisionDetectResult.error("同站点存在按激活用户数计费产品");
    }



    private int getMePorts() {
        return getBiz().getPorts();
    }

    private BizWebExEC getBiz() {
        return (BizWebExEC) me.getBiz();
    }

    private WebExECPayPerUse getChargeScheme() {
        return (WebExECPayPerUse) me.getChargeScheme();
    }

    public OrderBean getMe() {
        return me;
    }

    public void setMe(OrderBean me) {
        this.me = me;
    }
}
