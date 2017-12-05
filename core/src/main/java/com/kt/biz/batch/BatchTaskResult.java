package com.kt.biz.batch;

import com.kt.entity.mysql.batch.BatchTask;

/**
 * Created by Vega Zhou on 2015/10/28.
 */
public class BatchTaskResult {

    public static final BatchTaskResult FAIL = new BatchTaskResult();

    public static final BatchTaskResult SUCCESS = new BatchTaskResult(true);

    private boolean isSuccess = false;

    public BatchTaskResult() {
    }

    public BatchTaskResult(boolean success) {
        isSuccess = success;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

}
