package com.kt.entity.mysql.billing;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/4/8.
 */
@Table(name = "BB_ACCOUNT_OPERATION" )
@Entity
@SequenceGenerator(name="SEQ_ACCOUNT_OPERATION", sequenceName="SEQ_ACCOUNT_OPERATION", allocationSize = 1)
public class AccountOperation {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQ_ACCOUNT_OPERATION")
    @Column(name = "SEQUENCE_ID")
    private Long sequenceId;

    @Column(name = "ACCOUNT_ID")
    private long accountId;

    @Column(name = "OPERATION_TYPE")
    private int operationType;

    @Column(name = "PRIMAL_AMOUNT")
    private Double primalAmount;

    @Column(name = "CURRENT_AMOUNT")
    private Double currentAmount;

    @Column(name = "BATCH_ID")
    private long batchId;

    @Column(name = "OPERATE_TIME")
    private String operateTime;

    @Column(name = "OPERATOR")
    private String operator;

    @Column(name = "SAPSYNCED")
    private int sapSynced;

    @Column(name = "REF")
    private String reference;

    @Transient
    private String customerCode;

    @Transient
    private String customerName;

    @Transient
    private String accountType;

    public Long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public Double getPrimalAmount() {
        return primalAmount;
    }

    public void setPrimalAmount(Double primalAmount) {
        this.primalAmount = primalAmount;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getSapSynced() {
        return sapSynced;
    }

    public void setSapSynced(int sapSynced) {
        this.sapSynced = sapSynced;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    //Transient

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
