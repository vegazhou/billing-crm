package com.kt.entity.mysql.billing;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/5/5.
 */
@Table(name = "BB_BILL_FORMAL")
@Entity
@SequenceGenerator(name="SEQ_BILLING_LOG", sequenceName="SEQ_BILLING_LOG", allocationSize = 1)
public class BillingLog {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQ_BILLING_LOG")
    @Column(name = "SEQ_ID")
    private Long seqId;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "ACCOUNTPERIOD")
    private String accountPeriod;

    @Column(name = "LOG")
    private String log;

    @Column(name = "LOG_TIMESTAMP")
    private String timeStamp;

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
