package com.kt.service.mail;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.types.RoleType;
import com.kt.entity.mysql.crm.ChargeScheme;
import com.kt.entity.mysql.crm.Contract;
import com.kt.entity.mysql.crm.Order;
import com.kt.entity.mysql.mail.MailTemplate;
import com.kt.entity.mysql.user.OrgUser;
import com.kt.exception.InsufficientPrivilegeException;
import com.kt.exception.UserNotFoundException;
import com.kt.exception.WafException;
import com.kt.mail.MailBean;
import com.kt.mail.VelocityMailTemplate;
import com.kt.service.*;
import com.kt.session.PrincipalContext;
import com.kt.sys.GlobalSettings;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
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
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.*;

/**
 * Created by Vega Zhou on 2016/7/1.
 */
@Service
public class MailService {

    private static final Logger LOGGER = Logger.getLogger(MailService.class);

    private static final String DEFAULT_SENDER_ADDRESS = "bss@ketianyun.com";


    private static final String TEMPLATE_CHARGE_WAITING_APPROVAL_AUDITOR = "TEMPLATE_CHARGE_WAITING_APPROVAL_AUDITOR";
    private static final String TEMPLATE_CHARGE_WAITING_APPROVAL_OWNER = "TEMPLATE_CHARGE_WAITING_APPROVAL_OWNER";
    private static final String TEMPLATE_CHARGE_WITHDRAWN_AUDITOR = "TEMPLATE_CHARGE_WITHDRAWN_AUDITOR";
    private static final String TEMPLATE_CHARGE_WITHDRAWN_OWNER = "TEMPLATE_CHARGE_WITHDRAWN_OWNER";
    private static final String TEMPLATE_CHARGE_DECLINED_OWNER = "TEMPLATE_CHARGE_DECLINED_OWNER";
    private static final String TEMPLATE_CHARGE_APPROVED_OWNER = "TEMPLATE_CHARGE_APPROVED_OWNER";

    private static final String CONTRACT_WAITING_APPROVAL = "CONTRACT_WAITING_APPROVAL";
    private static final String CONTRACT_WAITING_FIN_APPROVAL = "CONTRACT_WAITING_FIN_APPROVAL";
    private static final String CONTRACT_DECLINED = "CONTRACT_DECLINED";

    private static final String CREDIT_NOTE = "CREDIT_NOTE";

    @Autowired
    MailTemplateService mailTemplateService;
    @Autowired
    OrgUserService orgUserService;
    @Autowired
    ContractService contractService;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ProductService productService;


    public void notifyBizWaitingApproval(String bizId) {

    }


    public void notifyBizDeclined() {

    }


    public void notifyBizApproved() {

    }

    //TODO:将收件人替换成正确的收件人
    public void notifyChargeWaitingApproval(ChargeScheme charge) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("chargeName", charge.getDisplayName());
        vars.put("operator", getOperatorName());
        vars.put("date", DateUtil.now());
        MailBean mail = generateMail(TEMPLATE_CHARGE_WAITING_APPROVAL_AUDITOR,  vars, Arrays.asList("weijia.zhou@ketianyun.com", "weijia.zhou@ketianyun.com"));
//        MailBean mail = generateMail(TEMPLATE_CHARGE_WAITING_APPROVAL,  vars, findAllChargeAuditors());
//        doSendMail(mail);
        mail = generateMail(TEMPLATE_CHARGE_WAITING_APPROVAL_OWNER, vars, Arrays.asList("weijia.zhou@ketianyun.com"));
//        doSendMail(mail);
    }

    public void notifyChargeWithdrawn(ChargeScheme charge) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("chargeName", charge.getDisplayName());
        vars.put("operator", getOperatorName());
        vars.put("date", DateUtil.now());
        MailBean mail = generateMail(TEMPLATE_CHARGE_WITHDRAWN_AUDITOR, vars, Arrays.asList("weijia.zhou@ketianyun.com", "weijia.zhou@ketianyun.com"));
//        doSendMail(mail);
        mail = generateMail(TEMPLATE_CHARGE_WITHDRAWN_OWNER, vars, Arrays.asList("weijia.zhou@ketianyun.com"));
//        doSendMail(mail);
    }

    public void notifyChargeDeclined(ChargeScheme charge) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("chargeName", charge.getDisplayName());
        vars.put("operator", getOperatorName());
        vars.put("date", DateUtil.now());
        MailBean mail = generateMail(TEMPLATE_CHARGE_DECLINED_OWNER, vars, Arrays.asList("weijia.zhou@ketianyun.com", "weijia.zhou@ketianyun.com"));
//        doSendMail(mail);
    }


    public void notifyChargeApproved(ChargeScheme charge) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("chargeName", charge.getDisplayName());
        vars.put("operator", getOperatorName());
        vars.put("date", DateUtil.now());
        MailBean mail = generateMail(TEMPLATE_CHARGE_APPROVED_OWNER, vars, Arrays.asList("weijia.zhou@ketianyun.com", "weijia.zhou@ketianyun.com"));
//        doSendMail(mail);
    }

    public void notifyProductWaitingApproval() {

    }

    public void notifyProductDeclined() {

    }

    public void notifyProductApproved() {

    }

    public void notifyContractWaitingApproval(Contract contract) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("contractName", contract.getDisplayName());
        vars.put("customer", customerService.findByCustomerId(contract.getCustomerId()).getDisplayName());
        vars.put("operator", getOperatorName());
        vars.put("date", DateUtil.now());
        MailBean mail = generateMail(CONTRACT_WAITING_APPROVAL, vars, findAllContractAuditors());
        doSendMail(mail);
    }


    public void notifyContractDeclined(Contract contract) throws WafException {
        Map<String, String> vars = new HashMap<>();
        vars.put("contractName", contract.getDisplayName());
        vars.put("customer", customerService.findByCustomerId(contract.getCustomerId()).getDisplayName());
        vars.put("operator", getOperatorName());
        vars.put("date", DateUtil.now());
        MailBean mail = generateMail(CONTRACT_DECLINED, vars, Collections.singletonList(contract.getDraftedBy()));
        doSendMail(mail);
    }


    public void notifyContractApproved() {

    }


    public void notifyWaitingFirstInstallmentConfirmation(Contract contract) throws WafException {
//        MailBean mail = generateMail(CONTRACT_WAITING_FIN_APPROVAL, vars, findAllFinAuditor());
//        MailBean mail = generateFirstInstallmentNotificationMail(contract.getPid(), findAllFinAuditor());
        MailBean mail = generateFirstInstallmentNotificationMail(contract.getPid(), Collections.singletonList("weijia.zhou@ketianyun.com"));
        doSendMail(mail);
    }

    public void notifyFirstInstallmentConfirmed() {

    }


    public void sendCreditNote(String recipient, File file) {
        MailBean mail = generateMail(CREDIT_NOTE, new HashMap<String, String>(), Collections.singletonList(recipient));
        doSendMail(mail, file);
    }


    private String getOperatorName() throws InsufficientPrivilegeException, UserNotFoundException {
        String submitterEmail = PrincipalContext.getCurrentUserName();
        OrgUser submitter = orgUserService.findByUserName(submitterEmail);
        return submitter.getFullName();
    }


    private List<String> findAllChargeAuditors() {
        List<OrgUser> users = orgUserService.findByRole(RoleType.CHARGE_AUDITOR);
        return covert2emails(users);
    }

    private List<String> findAllContractAuditors() {
        List<OrgUser> users = orgUserService.findByRole(RoleType.CONTRACT_AUDITOR);
        return covert2emails(users);
    }

    private List<String> findAllOperators() {
        List<OrgUser> users = orgUserService.findByRole(RoleType.OPERATOR);
        return covert2emails(users);
    }

    private List<String> findAllFinAuditor() {
        List<OrgUser> users = orgUserService.findByRole(RoleType.FIN_AUDITOR);
        return covert2emails(users);
    }




    private List<String> covert2emails(List<OrgUser> users) {
        List<String> mails = new ArrayList<>();
        for (OrgUser user : users) {
            mails.add(user.getUserName());
        }
        return mails;
    }


    private JavaMailSender getSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(GlobalSettings.getMailHost());
        mailSender.setUsername(GlobalSettings.getMailAuthUser());
        mailSender.setPassword(GlobalSettings.getMailAuthPass());
        mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.timeout", "25000");
        return mailSender;
    }


    public static class FirstInstallmentNotificationRow {
        private String effectiveStartDate;

        private String effectiveEndDate;

        private String firstInstallment;

        private String product;

        public String getEffectiveStartDate() {
            return effectiveStartDate;
        }

        public void setEffectiveStartDate(String effectiveStartDate) {
            this.effectiveStartDate = effectiveStartDate;
        }

        public String getEffectiveEndDate() {
            return effectiveEndDate;
        }

        public void setEffectiveEndDate(String effectiveEndDate) {
            this.effectiveEndDate = effectiveEndDate;
        }

        public String getFirstInstallment() {
            return firstInstallment;
        }

        public void setFirstInstallment(String firstInstallment) {
            this.firstInstallment = firstInstallment;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }
    }


    public MailBean generateFirstInstallmentNotificationMail(String contractId, List<String> recipients) throws WafException {
        Contract contract = contractService.findByContractId(contractId);
        List<Order> orders = orderService.findOrdersByContractId(contractId);

        VelocityEngine ve = new VelocityEngine();
        ve.init();
        VelocityContext context = new VelocityContext();
        AccountPeriod accountPeriod = new AccountPeriod(new Date());
        context.put("accountPeriodStart", DateUtil.formatShortDate(accountPeriod.beginOfThisPeriod()));
        context.put("accountPeriodEnd", DateUtil.formatShortDate(accountPeriod.endOfThisPeriod()));
        context.put("customer", customerService.findByCustomerId(contract.getCustomerId()));
        List<FirstInstallmentNotificationRow> rows = new ArrayList<>();
        FirstInstallmentNotificationRow summary = new FirstInstallmentNotificationRow();
        double total = 0d;
        for (Order order : orders) {
            if (order.getFirstInstallment() > 0) {
                FirstInstallmentNotificationRow row = new FirstInstallmentNotificationRow();
                row.setEffectiveStartDate(order.getEffectiveStartDate());
                row.setEffectiveEndDate(order.getEffectiveEndDate());
                row.setProduct(productService.findById(order.getProductId()).getDisplayName());
                double fi = MathUtil.scale(order.getFirstInstallment());
                row.setFirstInstallment(String.valueOf(fi));
                total += fi;
                rows.add(row);
            }
        }
        context.put("rows", rows);
        total = MathUtil.scale(total);
        summary.setFirstInstallment(String.valueOf(total));
        context.put("summary", summary);

        MailTemplate template = mailTemplateService.getTemplateByName("CONTRACT_WAITING_FIN_APPROVAL");
        VelocityMailTemplate vTemplate = transformTemplate(template);
        String subject = replaceVariables(vTemplate.getSubjectTemplate(), new HashMap<String, String>());
        MailBean bean = new MailBean();
        bean.setTo(recipients);
        bean.setSubject(subject);
        StringWriter writer = new StringWriter();
        vTemplate.getBodyTemplate().merge(context, writer);
        bean.setBody(writer.toString());
        return bean;
    }


    private MailBean generateMail(String templateName, Map<String, String> vars, List<String> recipients) {
        MailTemplate template = mailTemplateService.getTemplateByName(templateName);
        VelocityMailTemplate vTemplate = transformTemplate(template);
        String subject = replaceVariables(vTemplate.getSubjectTemplate(), vars);
        String body = replaceVariables(vTemplate.getBodyTemplate(), vars);

        MailBean bean = new MailBean();
        bean.setTo(recipients);
        bean.setSubject(subject);
        bean.setBody(body);
        return bean;
    }

    private VelocityMailTemplate transformTemplate(MailTemplate template) {
        Template bodyTemplate = createTemplateFromString(template.getTemplateName(), template.getBody());
        Template subjectTemplate = createTemplateFromString(template.getTemplateName(), template.getSubject());
        return new VelocityMailTemplate(subjectTemplate, bodyTemplate);
    }


    private static Template createTemplateFromReader(String templateName, Reader reader) {
        try {
            RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
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
        doSendMail(mail, null);
    }


    private void doSendMail(MailBean mail, File file) {
        try {
            JavaMailSender mailSender = getSender();
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            if (StringUtils.isNotBlank(mail.getFrom())) {
                messageHelper.setFrom(mail.getFrom());
            } else {
                messageHelper.setFrom(DEFAULT_SENDER_ADDRESS, "科天CRM系统邮件");
            }

            if (!mail.getTo().isEmpty()) {
                List<String> to = mail.getTo();
                messageHelper.setTo(to.toArray(new String[to.size()]));
            }

            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getBody(), true);
            if (file != null) {
                messageHelper.addAttachment(file.getName(), file);
            }
            mailSender.send(mimeMessage);
        } catch (MailException | MessagingException | UnsupportedEncodingException e) {
            LOGGER.error("error occurred while sending mail ", e);
        }
    }
}
