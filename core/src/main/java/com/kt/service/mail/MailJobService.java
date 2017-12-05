package com.kt.service.mail;

import com.kt.entity.mysql.mail.MailJob;
import com.kt.mail.GenericMailBean;
import com.kt.mail.MailStatus;
import com.kt.repo.mysql.mail.MailJobRepository;
import com.kt.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Vega Zhou on 2016/7/1.
 */
@Service
public class MailJobService {

    @Autowired
    MailJobRepository mailJobRepository;


    public MailJob save(MailJob job) {
        return mailJobRepository.save(job);
    }



    public void markJobAsInProgress(String id) {
        MailJob job = mailJobRepository.findOne(id);
        if (job != null) {
            job.setStatus("IN_PROGRESS");
            mailJobRepository.save(job);
        }
    }

    public void markJobAsFinished(String id) {
        MailJob job = mailJobRepository.findOne(id);
        if (job != null) {
            job.setStatus("SENT");
            mailJobRepository.save(job);
        }
    }


    public void addMail(GenericMailBean mailBean) throws ParseException {
        addMail(mailBean.getTemplate(),
                mailBean.getType(),
                mailBean.getVariables(),
                mailBean.getSender(),
                mailBean.getRecipients(),
                DateUtil.toDate(mailBean.getScheduledSendTime())
        );
    }

    public void addMail(String templateName, String type, String variables, String sender, String recipients,
                        Date scheduledSendTime) {
        MailJob mailJob = new MailJob();
        mailJob.setStatus(MailStatus.NOT_SENT.toString());
        mailJob.setTemplate(templateName);
        mailJob.setType(type);
        mailJob.setVariables(variables);
        mailJob.setSender(sender);
        mailJob.setRecipients(recipients);
        mailJob.setScheduledSendTime(DateUtil.formatDate(scheduledSendTime));
        mailJob.setCreateTime(DateUtil.now());
        save(mailJob);
    }
}