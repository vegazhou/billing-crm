package com.kt.biz.model.biz.impl;

import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.types.BizType;
import com.kt.biz.types.SchemeType;

/**
 * Created by Vega Zhou on 2016/3/11.
 */
public class BizWebExStorage extends AbstractBizScheme {
    @Override
    public BizType getType() {
        return BizType.WEBEX_STORAGE;
    }

    @Override
    public void copyFrom(AbstractBizScheme scheme) {

    }
}
