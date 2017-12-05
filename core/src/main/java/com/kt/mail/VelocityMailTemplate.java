package com.kt.mail;

import org.apache.velocity.Template;

/**
 * Created by Vega Zhou on 2016/7/1.
 */
public class VelocityMailTemplate {

    private Template subjectTemplate;

    private Template bodyTemplate;

    public VelocityMailTemplate(Template subjectTemplate, Template bodyTemplate) {
        this.subjectTemplate = subjectTemplate;
        this.bodyTemplate = bodyTemplate;
    }

    public Template getSubjectTemplate() {
        return subjectTemplate;
    }

    public void setSubjectTemplate(Template subjectTemplate) {
        this.subjectTemplate = subjectTemplate;
    }

    public Template getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(Template bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }
}
