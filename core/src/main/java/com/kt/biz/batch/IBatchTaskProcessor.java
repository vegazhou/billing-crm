package com.kt.biz.batch;

import java.util.concurrent.Callable;

/**
 * Created by Vega Zhou on 2015/10/27.
 */
public interface IBatchTaskProcessor {

    String toSerializableParameters();

    void fromSerializedParameters(String parameters);

    BatchTaskResult run();
}
