package com.kt.repo.mysql.mail;

import com.kt.entity.mysql.mail.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vega Zhou on 2016/7/1.
 */
public interface MailTemplateRepository  extends JpaRepository<MailTemplate, String> {

    MailTemplate findOneByTemplateName(String templateName);
}
