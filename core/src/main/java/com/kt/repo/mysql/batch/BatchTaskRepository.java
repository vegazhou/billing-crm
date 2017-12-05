package com.kt.repo.mysql.batch;

import com.kt.entity.mysql.batch.BatchTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Vega Zhou on 2015/10/28.
 */
public interface BatchTaskRepository extends JpaRepository<BatchTask, String> {

    List<BatchTask> findByBatchId(String batchId);
}
