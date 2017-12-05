package com.kt.entity.mysql.billing;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/5/4.
 */
@Table(name = "BB_TBILL_TUNELOG")
@Entity
@SequenceGenerator(name="SEQ_TBILL_TUNELOG", sequenceName="SEQ_TBILL_TUNELOG", allocationSize = 1)
public class TempBillTuneLog {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQ_TBILL_TUNELOG")
    @Column(name = "SEQUENCE_ID")
    private Long sequenceId;

    @Column(name = "BILL_ID")
    private Long billId;

    @Column(name = "PRIMAL_AMOUNT")
    private Float primalAmount;

    @Column(name = "CURRENT_AMOUNT")
    private Float currentAmount;

    @Column(name = "OPERATOR")
    private String operator;

    @Column(name = "COMMENTS")
    private String comments;

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
}
