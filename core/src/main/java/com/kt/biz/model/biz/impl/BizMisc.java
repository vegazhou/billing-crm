package com.kt.biz.model.biz.impl;

import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.types.BizType;

/**
 * Created by Vega Zhou on 2016/6/7.
 */
public class BizMisc extends AbstractBizScheme {
    @Override
    public BizType getType() {
        return BizType.MISC;
    }

    @Override
    public void copyFrom(AbstractBizScheme scheme) {

    }
}
