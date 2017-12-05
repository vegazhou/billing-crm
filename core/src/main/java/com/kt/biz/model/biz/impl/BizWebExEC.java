package com.kt.biz.model.biz.impl;

import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.types.BizType;
import com.kt.biz.types.SchemeType;

/**
 * Created by Vega Zhou on 2016/3/11.
 */
public class BizWebExEC extends BizWebExConf {

    @ModelAttribute(value = SchemeKeys.SESSION_PORTS)
    private int ports = 0;

    @Override
    public BizType getType() {
        return BizType.WEBEX_EC;
    }

    @Override
    public void copyFrom(AbstractBizScheme scheme) {
        if (scheme instanceof BizWebExEC) {
            BizWebExEC src = (BizWebExEC) scheme;
            setPorts(src.getPorts());
        }
    }

    public int getPorts() {
        return ports;
    }

    public void setPorts(int ports) {
        this.ports = ports;
    }
}
