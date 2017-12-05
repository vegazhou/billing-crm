package com.kt.biz.model.biz.impl;

import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.types.BizType;
import com.kt.biz.types.SchemeType;

/**
 * Created by Vega Zhou on 2016/3/11.
 */
public class BizWebExTC extends BizWebExConf {

    @ModelAttribute(value = SchemeKeys.SESSION_PORTS)
    private int ports = 0;

    @Override
    public void copyFrom(AbstractBizScheme scheme) {
        if (scheme instanceof BizWebExTC) {
            BizWebExTC src = (BizWebExTC) scheme;
            this.setPorts(src.getPorts());
        }
    }

    @Override
    public BizType getType() {
        return BizType.WEBEX_TC;
    }

    public int getPorts() {
        return ports;
    }

    public void setPorts(int ports) {
        this.ports = ports;
    }
}
