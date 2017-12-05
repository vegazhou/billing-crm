package com.kt.biz.model.biz.impl;

import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.types.BizType;

/**
 * Created by Vega on 2017/10/9.
 */
public class BizCallCenter extends AbstractBizScheme {
    @Override
    public BizType getType() {
        return BizType.CC;
    }

    @Override
    public void copyFrom(AbstractBizScheme scheme) {

    }
}
