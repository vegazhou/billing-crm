package com.kt.entity.mysql.mail;

/**
 * Created by Vega Zhou on 2016/7/1.
 */
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2015/10/15.
 */
@Table(name = "kt_mail_jobs")
@Entity
public class MailJob {

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "BATCH_NO")
    private long batchNumber;

    private String template;

    private String type;

    private String variables;

    private String sender;

    private String recipients;

    @Column(name = "SCHEDULED_SEND_TIME")
    private String scheduledSendTime;

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "SENT_TIME")
    private String sentTime;

    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(long batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getScheduledSendTime() {
        return scheduledSendTime;
    }

    public void setScheduledSendTime(String scheduledSendTime) {
        this.scheduledSendTime = scheduledSendTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
