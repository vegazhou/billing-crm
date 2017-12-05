package com.kt.service;

import com.kt.biz.batch.BatchConstants;
import com.kt.biz.batch.IBatch;
import com.kt.biz.batch.IBatchTaskProcessor;
import com.kt.entity.mysql.batch.Batch;
import com.kt.entity.mysql.batch.BatchTask;
import com.kt.repo.mysql.batch.BatchRepository;
import com.kt.repo.mysql.batch.BatchTaskRepository;
import com.kt.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vega Zhou on 2015/10/27.
 */
@Service
public class BatchService {

    private static final Logger LOGGER = Logger.getLogger(BatchService.class);

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    BatchTaskRepository batchTaskRepository;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Batch addNewBatch(IBatch batch) {
        Batch entity = new Batch();
        entity.setCategory(batch.getCategory());
        entity.setStatus(BatchConstants.STATUS_NOT_STARTED);
        entity.setCreateTime(DateUtil.now());
        Batch result = batchRepository.save(entity);

        List<IBatchTaskProcessor> tasks = batch.listTasks();
        if (tasks != null && tasks.size() > 0) {
            for (IBatchTaskProcessor task : tasks) {
                BatchTask taskEntity = newBatchTask(task);
                taskEntity.setBatchId(result.getId());
                batchTaskRepository.save(taskEntity);
            }
        }
        return result;
    }

    private BatchTask newBatchTask(IBatchTaskProcessor task) {
        BatchTask batchTask = new BatchTask();
        batchTask.setStatus(BatchConstants.STATUS_NOT_STARTED);
        batchTask.setProcessorClass(task.getClass().getName());
        batchTask.setParameters(task.toSerializableParameters());
        return batchTask;
    }

    public Batch nextNotStartedBatch() {
        return batchRepository.findFirstByStatusOrderByCreateTimeAsc(BatchConstants.STATUS_NOT_STARTED);
    }


    public void markBatchAsFinished(String batchId) {
        Batch batch = batchRepository.findOne(batchId);
        if (batch == null)
            return;

        batch.setStatus(BatchConstants.STATUS_FINISHED);
        batchRepository.save(batch);
    }

    public void markTaskAsInProgress(String taskId) {
        BatchTask task = batchTaskRepository.findOne(taskId);
        if (task == null)
            return;

        task.setStatus(BatchConstants.STATUS_IN_PROGRESS);
        batchTaskRepository.save(task);
    }

    public void markTaskAsFinished(String taskId) {
        BatchTask task = batchTaskRepository.findOne(taskId);
        if (task == null)
            return;

        task.setStatus(BatchConstants.STATUS_FINISHED);
        batchTaskRepository.save(task);
    }

    public void markTaskAsFailure(String taskId) {
        BatchTask task = batchTaskRepository.findOne(taskId);
        if (task == null)
            return;

        task.setStatus(BatchConstants.STATUS_FAIL);
        batchTaskRepository.save(task);
    }

    public List<BatchTask> findTasksByBatchId(String batchId) {
        Batch batchEntity = batchRepository.findOne(batchId);
        if (batchEntity == null)
            return null;

        return batchTaskRepository.findByBatchId(batchEntity.getId());
    }

    public List<IBatchTaskProcessor> loadTasksByBatchId(String batchId) {
        Batch batchEntity = batchRepository.findOne(batchId);
        if (batchEntity == null)
            return null;

        List<BatchTask> tasks = batchTaskRepository.findByBatchId(batchEntity.getId());
        if (tasks == null || tasks.size() == 0) {
            return null;
        }

        List<IBatchTaskProcessor> results = new LinkedList<IBatchTaskProcessor>();
        for (BatchTask task : tasks) {
            results.add(fromEntity2TaskProcessor(task));
        }
        return results;
    }


    public IBatchTaskProcessor loadTaskById(String id) {
        BatchTask taskEntity = batchTaskRepository.findOne(id);
        return fromEntity2TaskProcessor(taskEntity);
    }

    private IBatchTaskProcessor fromEntity2TaskProcessor(BatchTask taskEntity) {
        IBatchTaskProcessor task = null;
        try {
            task = (IBatchTaskProcessor) Class.forName(taskEntity.getProcessorClass()).newInstance();
        } catch (Exception e) {
            LOGGER.error("error instantiating " + taskEntity.getProcessorClass(), e);
        }
        if (task == null) {
            LOGGER.error("got NULL while instantiating " + taskEntity.getProcessorClass());
            return null;
        }

        task.fromSerializedParameters(taskEntity.getParameters());
        return task;
    }
}
