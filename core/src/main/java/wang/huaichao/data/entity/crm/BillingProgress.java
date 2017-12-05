package wang.huaichao.data.entity.crm;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 9/20/2016.
 */
@Entity
@Table(name = "billing_progress")
@DynamicInsert
@DynamicUpdate
@IdClass(BillingProgressPK.class)
public class BillingProgress {
    // !!! this value should NOT be used as customer id
    public static String BATCH_CUSTOMER_ID = "!@#$%^&*()_+=-`";

    @Id
    private int billingPeriod;
    @Id
    private String customerId = BATCH_CUSTOMER_ID;
    @Id
    @Enumerated(EnumType.STRING)
    private BillingProgressPK.BillingProgressType type;

    private int totalTasks;
    private int succeededTasks;
    private int failedTasks;
    @Enumerated(EnumType.STRING)
    private BillingProgressStatus status;
    private Date createdAt;
    private Date startTime;
    private Date endTime;

    public enum BillingProgressStatus {
        IN_PROGRESS, COMPLETED, CANCELED
    }

    public int getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(int billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BillingProgressPK.BillingProgressType getType() {
        return type;
    }

    public void setType(BillingProgressPK.BillingProgressType type) {
        this.type = type;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public int getSucceededTasks() {
        return succeededTasks;
    }

    public void setSucceededTasks(int succeededTasks) {
        this.succeededTasks = succeededTasks;
    }

    public int getFailedTasks() {
        return failedTasks;
    }

    public void setFailedTasks(int failedTasks) {
        this.failedTasks = failedTasks;
    }

    public BillingProgressStatus getStatus() {
        return status;
    }

    public void setStatus(BillingProgressStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
