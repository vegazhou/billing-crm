package com.kt.biz.model.biz.impl;

import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.types.BizType;

/**
 * Created by Vega Zhou on 2016/10/20.
 */
public class BizWebExCmr extends AbstractBizScheme {
    @Override
    public BizType getType() {
        return BizType.WEBEX_CMR;
    }

    @Override
    public void copyFrom(AbstractBizScheme scheme) {

    }
}
