package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.CollisionDetector;
import com.kt.biz.model.order.OrderBean;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/6/7.
 */
public class CollisionDetectorMisc implements CollisionDetector {
    @Override
    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
        return CollisionDetectResult.ok();
    }

    @Override
    public void setMe(OrderBean me) {

    }
}
