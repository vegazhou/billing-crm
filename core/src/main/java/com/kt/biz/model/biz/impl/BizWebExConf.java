package com.kt.biz.model.biz.impl;

import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.types.BizType;
import com.kt.biz.types.SchemeType;

/**
 * Created by Vega Zhou on 2016/3/21.
 */
public abstract class BizWebExConf extends AbstractBizScheme {

    @Override
    public abstract BizType getType();

    public abstract int getPorts();

    public abstract void setPorts(int ports);
}
