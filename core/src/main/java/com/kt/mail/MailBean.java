package com.kt.mail;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/7/1.
 */
public class MailBean {
    private String subject;
    private String from;
    private List<String> to = new LinkedList<String>();
    private List<String> bcc = new LinkedList<String>();
    private String body;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public void addTo(String to) {
        this.to.add(to);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public void addBcc(String bcc) {
        this.bcc.add(bcc);
    }

}
