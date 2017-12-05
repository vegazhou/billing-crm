package com.kt.biz.model.order.compatcheck;

import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.OrderBean;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/30.
 */
public class CollisionDetectorPSTNSinglePacket4AllSites extends AbstractPSTNCollisionDetector {
    @Override
    List<String> getMySiteNames() {
        return null;
    }

    @Override
    public CollisionDetectResult detectAgainst(List<OrderBean> orders) {
        //TODO: PSTN语音增量包(适用客户所有站点) 的冲突检测逻辑待定
        return CollisionDetectResult.ok();
    }
}
