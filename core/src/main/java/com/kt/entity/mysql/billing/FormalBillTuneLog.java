package com.kt.entity.mysql.billing;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/5/4.
 */
@Table(name = "BB_FBILL_TUNELOG")
@Entity
@SequenceGenerator(name="SEQ_FBILL_TUNELOG", sequenceName="SEQ_FBILL_TUNELOG", allocationSize = 1)
public class FormalBillTuneLog {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQ_FBILL_TUNELOG")
    @Column(name = "SEQUENCE_ID")
    private Long sequenceId;

    @Column(name = "BILL_ID")
    private Long billId;

    @Column(name = "PRIMAL_AMOUNT")
    private Float primalAmount;

    @Column(name = "CURRENT_AMOUNT")
    private Float currentAmount;

    @Column(name = "PRIMAL_UNPAID")
    private Float primalUnpaidAmount;

    @Column(name = "CURRENT_UNPAID")
    private Float currentUnpaidAmount;

    @Column(name = "OPERATOR")
    private String operator;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "LOG_DATE")
    private String logDate;

    public Long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Float getPrimalAmount() {
        return primalAmount;
    }

    public void setPrimalAmount(Float primalAmount) {
        this.primalAmount = primalAmount;
    }

    public Float getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Float currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Float getPrimalUnpaidAmount() {
        return primalUnpaidAmount;
    }

    public void setPrimalUnpaidAmount(Float primalUnpaidAmount) {
        this.primalUnpaidAmount = primalUnpaidAmount;
    }

    public Float getCurrentUnpaidAmount() {
        return currentUnpaidAmount;
    }

    public void setCurrentUnpaidAmount(Float currentUnpaidAmount) {
        this.currentUnpaidAmount = currentUnpaidAmount;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }
}
