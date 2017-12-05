package com.kt.biz.batch;

import com.kt.common.util.BeanHelper;
import com.thoughtworks.xstream.XStream;

/**
 * Created by Vega Zhou on 2015/10/28.
 */
public abstract class XmlParameterTaskProcessor implements IBatchTaskProcessor {

    public String toSerializableParameters() {
        return new XStream().toXML(this);
    }

    public void fromSerializedParameters(String parameters) {
        Object copy = new XStream().fromXML(parameters);
        BeanHelper.copyProperties(copy, this);
    }
}
