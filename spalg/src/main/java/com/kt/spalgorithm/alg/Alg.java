package com.kt.spalgorithm.alg;

import com.kt.biz.model.order.OrderBean;
import com.kt.spalgorithm.model.payload.WbxPayload;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public abstract class Alg {

    public abstract void start(OrderBean order, WbxPayload initialState);

    public abstract void terminate(OrderBean order, WbxPayload initialState);
}
