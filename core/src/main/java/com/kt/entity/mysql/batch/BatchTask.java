package com.kt.entity.mysql.batch;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2015/10/28.
 */
@Table(name = "kt_batch_tasks")
@Entity
public class BatchTask {

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "BATCH_ID")
    private String batchId;

    @Column(name = "PROCESSOR_CLASS")
    private String processorClass;

    private String parameters;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getProcessorClass() {
        return processorClass;
    }

    public void setProcessorClass(String processorClass) {
        this.processorClass = processorClass;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
