package com.kt.biz.model.biz.impl;

import com.kt.biz.model.annotation.AttributeType;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.biz.AbstractBizScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.types.BizType;
import com.kt.exception.InvalidPortsException;
import com.kt.exception.SchemeValidationException;

/**
 * Created by Vega Zhou on 2016/3/11.
 */
public class BizWebExMC extends BizWebExConf {

    private static final int[] PORTS_ALLOWED = new int[]{3, 5, 8, 10, 20, 25, 30, 50, 100, 200, 999, 1000};

    @ModelAttribute(value = SchemeKeys.SESSION_PORTS, type = AttributeType.INT)
    private int ports = 3;

    @Override
    public BizType getType() {
        return BizType.WEBEX_MC;
    }

    @Override
    public void copyFrom(AbstractBizScheme scheme) {
        if (scheme instanceof BizWebExMC) {
            BizWebExMC src = (BizWebExMC) scheme;
            this.setPorts(src.getPorts());
        }
    }

    @Override
    public void validate() throws SchemeValidationException {
        validatePorts();
    }


    private void validatePorts() throws InvalidPortsException {
        for (int port : PORTS_ALLOWED) {
            if (port == ports) {
                return;
            }
        }
        throw new InvalidPortsException();
    }

    public int getPorts() {
        return ports;
    }

    public void setPorts(int ports) {
        this.ports = ports;
    }
}
