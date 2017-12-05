package com.kt.service.mail;

import com.kt.biz.types.RoleType;
import com.kt.entity.mysql.crm.ChargeScheme;
import com.kt.entity.mysql.mail.MailTemplate;
import com.kt.entity.mysql.user.OrgUser;
import com.kt.exception.InsufficientPrivilegeException;
import com.kt.exception.UserNotFoundException;
import com.kt.exception.WafException;
import com.kt.mail.MailBean;
import com.kt.mail.VelocityMailTemplate;
import com.kt.service.OrderService;
import com.kt.service.OrgUserService;
import com.kt.session.PrincipalContext;
import com.kt.sys.GlobalSettings;
import com.kt.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Rickey Zhu on 2016/9/6.
 */
@Service
public class ProvisionMailService {

    private static final Logger LOGGER = Logger.getLogger(ProvisionMailService.class);

    private static final String DEFAULT_SENDER_ADDRESS = "WebEx.Billing@ketianyun.com";
    //private static final String DEFAULT_SENDER_ADDRESS = "WebEx.Billing@ketianyun.com.cn";


    private static final String TEMPLATE_SERVICE_START = "PROVISION_SERVICE_START";
    private static final String TEMPLATE_SITE_CLOSE = "PROVISION_SITE_CLOSE";
    private static final String TEMPLATE_PSTN_STOP = "PROVISION_PSTN_STOP";
    private static final String TEMPLATE_PSTN_REOPEN = "PROVISION_PSTN_REOPEN";
    private static final String TEMPLATE_ALL_STOP = "PROVISION_ALL_STOP";
    private static final String TEMPLATE_ALL_REOPEN = "PROVISION_ALL_REOPEN";
    private static final String TEMPLATE_ORDER_EXPIRED_REMIND = "ORDER_EXPIRED_REMIND";
    private static final String TEMPLATE_ERROR_REMIND = "PROVISION_ERROR_REMIND";


    private static final String MAIL_SENDER = "科天云";
    private static final String TEL_SALE_EMAIL_GROUP ="webexproject@ketianyun.com";

    @Autowired
    MailTemplateService mailTemplateService;

    public void provServiceStart(String prodName, String siteName, String siteAdminEmail, String salesEmail, boolean isModify) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("F_PRODUCT_NAME", prodName);
        vars.put("F_SITE_NAME", siteName);
        vars.put("LOGIN_ACCOUNT", siteAdminEmail);
        MailBean mail = generateMail(TEMPLATE_SERVICE_START, vars, Arrays.asList(salesEmail), null);
        if(isModify) {
            String tmpBody = mail.getBody();
            mail.setBody(tmpBody.replace("成功开通!", "变更成功!"));
        }
        doSendMail(mail);
    }

    public void provCIHostServiceStart(String prodName, String siteName, String siteAdminEmail, String salesEmail, boolean isModify) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("F_PRODUCT_NAME", prodName);
        vars.put("F_SITE_NAME", siteName);
        vars.put("LOGIN_ACCOUNT", siteAdminEmail);
        MailBean mail = generateMail(TEMPLATE_SERVICE_START, vars, Arrays.asList(siteAdminEmail), Arrays.asList(salesEmail));
        if(isModify) {
            String tmpBody = mail.getBody();
            mail.setBody(tmpBody.replace("成功开通!", "变更成功!"));
        }
        doSendMail(mail);
    }

    public void provServiceStart(String prodName, String siteName, String siteAdminEmail,
                                       String customerEmail, String bccEmails, boolean isModify) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("F_PRODUCT_NAME", prodName);
        vars.put("F_SITE_NAME", siteName);
        vars.put("LOGIN_ACCOUNT", siteAdminEmail);
        String[] emails = bccEmails.split("\\|\\|");
        List<String> bccList = new ArrayList<String>();
        bccList.addAll(Arrays.asList(emails));
        MailBean mail = generateMail(TEMPLATE_SERVICE_START, vars, Arrays.asList(customerEmail), bccList);
        if(isModify) {
            String tmpBody = mail.getBody();
            mail.setBody(tmpBody.replace("成功开通!", "变更成功!"));
        }
        doSendMail(mail);
    }

    public void provPSTNStop(String siteName, String siteAdminEmail, String salesEmail) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("SITE_NAME", siteName);
        MailBean mail = generateMail(TEMPLATE_PSTN_STOP, vars, Arrays.asList(siteAdminEmail),Arrays.asList(salesEmail));
        doSendMail(mail);
    }

    public void provPSTNStop(String siteName, String siteAdminEmail,
                             String customerEmail, String bccEmails) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("SITE_NAME", siteName);
        String[] emails = bccEmails.split("\\|\\|");
        List<String> bccList = new ArrayList<String>();
        bccList.addAll(Arrays.asList(emails));
        MailBean mail = generateMail(TEMPLATE_PSTN_STOP, vars, Arrays.asList(customerEmail),bccList);
        doSendMail(mail);
    }

    public void provPSTNReopen(String siteName, String siteAdminEmail, String salesEmail) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("SITE_NAME", siteName);
        MailBean mail = generateMail(TEMPLATE_PSTN_REOPEN, vars, Arrays.asList(siteAdminEmail),Arrays.asList(salesEmail));
        doSendMail(mail);
    }

    public void provPSTNReopen(String siteName, String siteAdminEmail,
                               String customerEmail, String bccEmails) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("SITE_NAME", siteName);
        String[] emails = bccEmails.split("\\|\\|");
        List<String> bccList = new ArrayList<String>();
        bccList.addAll(Arrays.asList(emails));
        MailBean mail = generateMail(TEMPLATE_PSTN_REOPEN, vars, Arrays.asList(customerEmail),bccList);
        doSendMail(mail);
    }

    public void provAllStop(String prodName, String siteAdminEmail, String salesEmail) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("F_PRODUCT_NAME", prodName);
        MailBean mail = generateMail(TEMPLATE_ALL_STOP, vars, Arrays.asList(siteAdminEmail), Arrays.asList(salesEmail,TEL_SALE_EMAIL_GROUP));
        doSendMail(mail);
    }

    public void provAllStop(String prodName, String siteAdminEmail,
                            String customerEmail, String bccEmails) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("F_PRODUCT_NAME", prodName);
        String[] emails = bccEmails.split("\\|\\|");
        List<String> bccList = new ArrayList<String>();
        bccList.addAll(Arrays.asList(emails));
        bccList.add(TEL_SALE_EMAIL_GROUP);
        MailBean mail = generateMail(TEMPLATE_ALL_STOP, vars, Arrays.asList(customerEmail), bccList);
        doSendMail(mail);
    }

    public void provAllReopen(String prodName, String siteAdminEmail, String salesEmail) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("F_PRODUCT_NAME", prodName);
        MailBean mail = generateMail(TEMPLATE_ALL_REOPEN, vars, Arrays.asList(siteAdminEmail), Arrays.asList(salesEmail));
        doSendMail(mail);
    }

    public void provAllReopen(String prodName, String siteAdminEmail,
                              String customerEmail, String bccEmails) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("F_PRODUCT_NAME", prodName);
        String[] emails = bccEmails.split("\\|\\|");
        List<String> bccList = new ArrayList<String>();
        bccList.addAll(Arrays.asList(emails));
        MailBean mail = generateMail(TEMPLATE_ALL_REOPEN, vars, Arrays.asList(customerEmail), bccList);
        doSendMail(mail);
    }

    public void orderExpiredRemind(String customerName,String prodName, String expiredRemindDays,
                                   String salesEmail, String customerEmail) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("CUSTOMER_NAME", customerName);
        vars.put("PRODUCT_NAME", prodName);
        vars.put("REMIND_DAYS", expiredRemindDays);
        String[] emails = salesEmail.split("\\|\\|");
        List<String> bccList = new ArrayList<String>();
        bccList.addAll(Arrays.asList(emails));
        bccList.add(TEL_SALE_EMAIL_GROUP);
        MailBean mail = generateMail(TEMPLATE_ORDER_EXPIRED_REMIND, vars, Arrays.asList(customerEmail),bccList);
        doSendMail(mail);
    }

    public void provErrorRemind(String siteName, String errorMsg, String operatorEmail) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("SITE_NAME", siteName);
        vars.put("RESPONSE_ERROR", errorMsg);
        String[] emails = operatorEmail.split(",");
        MailBean mail = generateMail(TEMPLATE_ERROR_REMIND, vars, Arrays.asList(emails),null);
        doSendMail(mail);
    }

    private JavaMailSender getSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(GlobalSettings.getMailHost());
//        mailSender.setPort(587);
        mailSender.setUsername(GlobalSettings.getMailAuthUser());
        mailSender.setPassword(GlobalSettings.getMailAuthPass());
        mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.timeout", "25000");

//        mailSender.getJavaMailProperties().setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//        mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
//        mailSender.getJavaMailProperties().setProperty("mail.smtps.ssl.checkserveridentity", "true");
//        mailSender.getJavaMailProperties().setProperty("mail.smtps.ssl.trust", "*");

        return mailSender;
    }


    private MailBean generateMail(String templateName, Map<String, String> vars,
                                  List<String> recipients, List<String> bccRecipients) {
        MailTemplate template = mailTemplateService.getTemplateByName(templateName);
        VelocityMailTemplate vTemplate = transformTemplate(template);
        String subject = replaceVariables(vTemplate.getSubjectTemplate(), vars);
        String body = replaceVariables(vTemplate.getBodyTemplate(), vars);

        MailBean bean = new MailBean();
        List<String> bcc = new ArrayList<String>();
        if(bccRecipients!=null) {
            bcc.addAll(bccRecipients);
        }
        bcc.add("WebEx.Service@ketianyun.com");
        //For testing, all the provision email cc to rickey
        bcc.add("zicheng.zhu@ketianyun.com");
        if(!TEMPLATE_ERROR_REMIND.equals(templateName)) {
            bcc.add("support@ketianyun.com");
        }
        bean.setTo(recipients);
        bean.setSubject(subject);
        bean.setBody(body);
        bean.setBcc(bcc);
        return bean;
    }

    private VelocityMailTemplate transformTemplate(MailTemplate template) {
        Template bodyTemplate = createTemplateFromString(template.getTemplateName(), template.getBody());
        Template subjectTemplate = createTemplateFromString(template.getTemplateName(), template.getSubject());
        return new VelocityMailTemplate(subjectTemplate, bodyTemplate);
    }


    private static Template createTemplateFromString(String templateName, String templateBody) {
        try {
            RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
            StringReader reader = new StringReader(templateBody);
            SimpleNode node = runtimeServices.parse(reader, templateName);
            Template template = new Template();
            template.setRuntimeServices(runtimeServices);
            template.setData(node);
            template.initDocument();
            return template;
        } catch (ParseException e) {
            LOGGER.error("error occurred while parsing mail template" + templateName, e);
            return null;
        }
    }

    private String replaceVariables(Template template, Map<String, String> variables) {
        VelocityEngine ve = new VelocityEngine();
        ve.init();

        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, String> variableEntry : variables.entrySet()) {
            context.put(variableEntry.getKey(), variableEntry.getValue());
        }

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

    private void doSendMail(MailBean mail) {
        try {
            JavaMailSender mailSender = getSender();
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            if (StringUtils.isNotBlank(mail.getFrom())) {
                messageHelper.setFrom(mail.getFrom());
            } else {
                messageHelper.setFrom(DEFAULT_SENDER_ADDRESS, MAIL_SENDER);
            }

            if (!mail.getTo().isEmpty()) {
                List<String> to = mail.getTo();
                messageHelper.setTo(to.toArray(new String[to.size()]));
            }

            if (!mail.getBcc().isEmpty()) {
                List<String> bcc = mail.getBcc();
                messageHelper.setBcc(bcc.toArray(new String[bcc.size()]));
            }

            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getBody(), true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error("error occurred while sending mail: ", e);
        }
    }
}
