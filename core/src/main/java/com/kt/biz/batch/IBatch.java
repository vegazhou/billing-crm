package com.kt.biz.batch;

import java.util.List;

/**
 * Created by Vega Zhou on 2015/10/27.
 */
public interface IBatch {

    String getCategory();

    List<IBatchTaskProcessor> listTasks();
}
