package com.kt.biz.model.order;


import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
public interface CollisionDetector {
    CollisionDetectResult detectAgainst(List<OrderBean> orders);

    void setMe(OrderBean me);
}
