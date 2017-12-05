package com.kt.service.mail;

import com.kt.entity.mysql.mail.MailTemplate;
import com.kt.repo.mysql.mail.MailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Vega Zhou on 2016/7/1.
 */
@Service
public class MailTemplateService {
    @Autowired
    MailTemplateRepository mailTemplateRepository;

    public MailTemplate getTemplateByName(String templateName) {
        return mailTemplateRepository.findOneByTemplateName(templateName);
    }
}
