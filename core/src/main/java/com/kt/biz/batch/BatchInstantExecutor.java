package com.kt.biz.batch;

/**
 * Created by Vega Zhou on 2015/11/4.
 */

import com.kt.exception.BatchExecutionException;

import java.util.List;

/**
 * BatchInstantExecutor will start a batch instantly
 */
public class BatchInstantExecutor {

    public static void execute(IBatch iBatch) throws BatchExecutionException {
        List<IBatchTaskProcessor> tasks = iBatch.listTasks();
        if (tasks != null) {
            for (IBatchTaskProcessor task : tasks) {
                BatchTaskResult result = task.run();
                if (!result.isSuccess()) {
                    throw new BatchExecutionException();
                }
            }
        }
    }
}
