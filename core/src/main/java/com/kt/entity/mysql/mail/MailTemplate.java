package com.kt.entity.mysql.mail;

/**
 * Created by Vega Zhou on 2016/7/1.
 */
import javax.persistence.*;

/**
 * Created by Vega Zhou on 2015/10/16.
 */
@Table(name = "kt_mail_templates")
@Entity
public class MailTemplate {

    @Id
    @Column(name = "TEMPLATE_NAME")
    private String templateName;

    private String locale;

    private String subject;

    private String body;

    private String type;


    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}