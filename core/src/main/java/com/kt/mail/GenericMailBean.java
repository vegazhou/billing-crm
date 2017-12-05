package com.kt.mail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Vega Zhou on 2016/7/1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericMailBean {

    private String template;

    private String type;

    private String variables;

    private String sender;

    private String recipients;

    private String scheduledSendTime;

    private String createdTime;

    private String sentTime;

    private String status;

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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
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