package com.kt.biz.batch;

import com.kt.entity.mysql.batch.BatchTask;
import com.kt.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * Created by Vega Zhou on 2015/10/28.
 */
@Component()
@Scope("prototype")
public class BatchTaskCallable implements Callable<BatchTaskResult> {

    @Autowired
    BatchService batchService;

    private String taskId;

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public BatchTaskResult call() throws Exception {
        IBatchTaskProcessor processor = batchService.loadTaskById(taskId);
        batchService.markTaskAsInProgress(taskId);
        try {
            BatchTaskResult result = processor.run();
            batchService.markTaskAsFinished(taskId);
            return result;
        } catch (Exception e) {
            batchService.markTaskAsFailure(taskId);
        }

        return BatchTaskResult.FAIL;
    }
}
