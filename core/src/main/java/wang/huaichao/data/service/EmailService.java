package wang.huaichao.data.service;

import com.google.common.net.MediaType;
import com.kt.biz.billing.AccountPeriod;
import com.kt.entity.mysql.billing.BillConfirmation;
import com.kt.entity.mysql.crm.Customer;
import com.kt.repo.mysql.batch.CustomerRepository;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wang.huaichao.Global;
import wang.huaichao.data.entity.crm.BillingProgress;
import wang.huaichao.data.entity.crm.BillingProgressPK;
import wang.huaichao.data.entity.crm.CustomerToIgnore;
import wang.huaichao.data.mongo.BillPdf;
import wang.huaichao.data.mongo.BillPdfRepository;
import wang.huaichao.data.repo.BillConfirmationRepository;
import wang.huaichao.data.repo.BillingProgressRepository;
import wang.huaichao.data.repo.CustomerToIgnoreRepository;
import wang.huaichao.exception.BillingException;
import wang.huaichao.utils.CollectionUtils;
import wang.huaichao.utils.DateBuilder;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 10/25/2016.
 */
@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${email.user}")
    private String emailUser;

    @Value("${email.pass}")
    private String emailPass;

    @Value("${email.smtp.host}")
    private String smtpHost;

    @Value("${email.smtp.port}")
    private String smtpPort;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BillPdfRepository billPdfRepository;

    @Autowired
    private BillingProgressRepository billingProgressRepository;

    @Autowired
    @Qualifier("BillConfirmationRepository2")
    private BillConfirmationRepository billConfirmationRepository;

    @Autowired
    private CustomerToIgnoreRepository customerToIgnoreRepository;

    @Autowired
    private CrmDataService crmDataService;

    private Set<String> customerToIgnoreSet;

    @Async
    public Future batchSendPdfBill(int billingPeriod, String sendTo) throws BillingException {
        return batchSendPdfBill(billingPeriod, sendTo, true);
    }

    @Async
    public Future batchSendPdfBill(int billingPeriod, String sendTo, boolean ignoreSucceeded) throws BillingException {
        List<String> customerIds = getMailingCustomers(billingPeriod);
        return batchSendPdfBill(customerIds, billingPeriod, sendTo, ignoreSucceeded);
    }


    @Async
    public Future batchSendPdfBill(List<String> customerIds, int billingPeriod, String sendTo, boolean ignoreSucceeded) throws BillingException {
        _checkIsBatchMailingAllowed(billingPeriod);

        if (customerIds == null || customerIds.size() == 0) {
            throw new BillingException("no bill confirmed");
        }

        // download all pdfs
        try {
            _download_pdfs(billingPeriod);
        } catch (IOException e) {
            log.error("error downloading pdf sending to customer", e);
        }

        // remove ignored customers
        customerIds.removeAll(getCustomerToIgnoreSet());

        // start
        BillingProgress bp = _batchStart(billingPeriod, customerIds.size());

        int succeeded = 0;
        int failed = 0;

        Set<String> succeedOnes = _getCustomerIdsSendPdfSucceed(billingPeriod);

        for (String customerId : customerIds) {
            try {
                if (ignoreSucceeded == false || succeedOnes.contains(customerId) == false) {
                    sendPdfBill(customerId, billingPeriod, sendTo);
                    succeeded++;
                } else {
                    succeeded++;
                    continue;
                }
                bp.setSucceededTasks(succeeded);
                billingProgressRepository.save(bp);
            } catch (Exception e) {
                log.error("send pdf to customer {} failed", customerId);
                failed++;
                bp.setFailedTasks(failed);
                billingProgressRepository.save(bp);
            }
        }

        // end
        bp.setStatus(BillingProgress.BillingProgressStatus.COMPLETED);
        bp.setEndTime(new Date());
        billingProgressRepository.save(bp);

        return null;
    }

    private void _download_pdfs(int billingPeriod) throws IOException {
        // create dir
        File dir = _getPdfStoreDir(billingPeriod);
        log.debug("pdf temp directory {}", dir.getAbsolutePath());
        dir.mkdirs();


        List<String> cids = getMailingCustomers(billingPeriod);
        List<BillPdf> pdfs = billPdfRepository
            .findByBillingPeriodAndCustomerIdIn(billingPeriod, cids);

        for (BillPdf pdf : pdfs) {
            FileOutputStream os = new FileOutputStream(
                new File(dir, pdf.getCustomerId() + ".pdf"));
            os.write(pdf.getPdfContent());
            os.close();
        }

    }

    private File _getPdfStoreDir(int billingPeriod) {
        return new File(System.getProperty("user.home"),
            "bss-pdf-" + billingPeriod);
    }

    private boolean _isMailSentSuccessfully(int billingPeriod, String customerId) {
        BillingProgressPK pk = new BillingProgressPK();

        pk.setType(BillingProgressPK.BillingProgressType.SINGLE_PDF_BILL_MAILING);
        pk.setBillingPeriod(billingPeriod);
        pk.setCustomerId(customerId);

        BillingProgress progress = billingProgressRepository.findOne(pk);

        if (progress != null &&
            progress.getStatus() == BillingProgress.BillingProgressStatus.COMPLETED &&
            progress.getSucceededTasks() == progress.getTotalTasks()) {
            return true;
        }

        return false;
    }

    private Set<String> _getCustomerIdsSendPdfSucceed(int billingPeriod) {
        List<BillingProgress> successTasks = billingProgressRepository.findByBillingPeriodAndTypeAndFailedTasks(
            billingPeriod,
            BillingProgressPK.BillingProgressType.SINGLE_PDF_BILL_MAILING,
            0
        );

        List<String> customerIds = CollectionUtils.collectString(successTasks, "customerId");

        Set<String> ret = new HashSet<>();
        ret.addAll(customerIds);

        return ret;
    }

    private BillingProgress _batchStart(int billingPeriod, int tasks) {
        BillingProgress bp = new BillingProgress();
        bp.setBillingPeriod(billingPeriod);
        bp.setType(BillingProgressPK.BillingProgressType.BATCH_PDF_BILL_MAILING);
        bp.setStatus(BillingProgress.BillingProgressStatus.IN_PROGRESS);
        bp.setTotalTasks(tasks);
        Date now = new Date();
        bp.setCreatedAt(now);
        bp.setStartTime(now);
        billingProgressRepository.save(bp);
        return bp;
    }

    private void _checkIsBatchMailingAllowed(int billingPeriod) throws BillingException {
        BillingProgressPK pk = new BillingProgressPK();
        pk.setType(BillingProgressPK.BillingProgressType.BATCH_PDF_BILL_MAILING);
        pk.setBillingPeriod(billingPeriod);

        BillingProgress progress = billingProgressRepository.findOne(pk);

        if (_isInProgress(progress, 3600 * 1000)) {
            throw new BillingException("task in progress");
        }
    }

    private boolean _isInProgress(BillingProgress progress, long threshold) {
        final boolean inProgress = true;

        if (progress == null) return !inProgress;

        long diff = System.currentTimeMillis() - progress.getStartTime().getTime();

        if (progress.getStatus() == BillingProgress.BillingProgressStatus.IN_PROGRESS && diff < threshold) {
            return inProgress;
        } else {
            return !inProgress;
        }
    }

    public List<String> getMailingCustomers(int billingPeriod) {
        AccountPeriod accountPeriod;
        try {
            accountPeriod = new AccountPeriod(billingPeriod + "");
        } catch (ParseException e) {
            throw new RuntimeException("invalid billing period: " + billingPeriod);
        }
        accountPeriod = accountPeriod.nextPeriod();
        List<BillConfirmation> confirmations = billConfirmationRepository.findByAccountPeriod(accountPeriod.toString());
        List<String> customerIds = CollectionUtils.collectString(confirmations, "customerId");

        try {
            DateBuilder db = new DateBuilder(Global.yyyyMM_FMT.parse("" + billingPeriod));
            Date start = db.beginOfMonth().build();
            Date end = db.nextMonth().build();
            List<String> formalActiveCustomers = crmDataService.findFormalActiveCustomers(billingPeriod);
            List<String> reseller2Custs = crmDataService.findCustomersUnderReseller2(billingPeriod);
            List<String> nonDirectCustomerIds = crmDataService.findNonDirectCustomerIds();
            List<String> f2fUsers = crmDataService.findF2fUsers();
            customerIds.retainAll(formalActiveCustomers);
            customerIds.removeAll(reseller2Custs);
            customerIds.removeAll(nonDirectCustomerIds);
            customerIds.removeAll(f2fUsers);
        } catch (Exception e) {
            throw new RuntimeException("failed to exclude customer from mailing list", e);
        }


        return customerIds;
    }

    public void sendPdfBill(String customerId, int billingPeriod, String sendTo) throws Exception {
        if (getCustomerToIgnoreSet().contains(customerId)) {
            return;
        }

        _checkIfPdfBillSendAllowed(billingPeriod, customerId);

        BillingProgress bp = _startSinglePdfBillMailing(billingPeriod, customerId);

        Customer customer = customerRepository.findOne(customerId);

        try {
            MimeMessage message = _createMessage(customer, billingPeriod, sendTo);

            Global.removeCryptographyRestrictions();

            Transport.send(message);

            bp.setSucceededTasks(1);
            billingProgressRepository.save(bp);

            log.debug("send pdf bill {} to {} succeeded", billingPeriod, customerId);
        } catch (Exception e) {

            bp.setFailedTasks(1);
            billingProgressRepository.save(bp);

            log.error("send pdf bill {} to {} failed", billingPeriod, customerId);
            log.error("email: {}", customer.getContactEmail());
            log.error("display name: {}", customer.getDisplayName());
            log.error("reason: {}", e.getMessage());
            throw e;
        } finally {
            bp.setEndTime(new Date());
            bp.setStatus(BillingProgress.BillingProgressStatus.COMPLETED);
            billingProgressRepository.save(bp);
        }
    }

    private BillingProgress _startSinglePdfBillMailing(int billingPeriod, String customerId) {
        BillingProgress bp = new BillingProgress();
        bp.setBillingPeriod(billingPeriod);
        bp.setType(BillingProgressPK.BillingProgressType.SINGLE_PDF_BILL_MAILING);
        bp.setStatus(BillingProgress.BillingProgressStatus.IN_PROGRESS);
        bp.setCustomerId(customerId);
        bp.setTotalTasks(1);
        Date now = new Date();
        bp.setCreatedAt(now);
        bp.setStartTime(now);
        billingProgressRepository.save(bp);
        return bp;
    }

    private void _checkIfPdfBillSendAllowed(int billingPeriod, String customerId) throws BillingException {
        BillingProgressPK pk = new BillingProgressPK();

        pk.setType(BillingProgressPK.BillingProgressType.SINGLE_PDF_BILL_MAILING);
        pk.setBillingPeriod(billingPeriod);
        pk.setCustomerId(customerId);

        BillingProgress progress = billingProgressRepository.findOne(pk);

        if (_isInProgress(progress, 3600 * 1000)) {
            throw new BillingException("task in progress");
        }
    }

    private Session _createEmailSession() {
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", smtpHost);
        props.put("mail.smtp.timeout", 5000);
        props.put("mail.smtp.connectiontimeout", 5000);
        props.put("mail.smtp.auth", "true");

        if (smtpHost.endsWith("qq.com")) {
//            props.setProperty("mail.smtp.port", smtpPort);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.starttls.enable", "true");
        }

        return Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUser, emailPass);
            }
        });
    }

    private BodyPart _createHtmlBody(int billingPeriod) throws MessagingException, IOException {
        Calendar cal;
        try {
            long ms = Global.yyyyMM_FMT.parse(billingPeriod + "").getTime();
            cal = Calendar.getInstance();
            cal.setTimeInMillis(ms);
        } catch (ParseException e) {
            throw new RuntimeException("invalid billing period " + billingPeriod);
        }

        DateFormat cnFmt = new SimpleDateFormat("yyyy年MM月");
        Map<String, String> params = new HashMap<>();
        params.put("date_cn", cnFmt.format(cal.getTime()));
        params.put("date_en", Global.MonthFullNamesEn[cal.get(Calendar.MONTH)] + ' ' + cal.get(Calendar.YEAR));

        String html = _getHtmlFromTemplate(params);

        BodyPart content = new MimeBodyPart();
        content.setContent(html, "text/html; charset=utf-8");
        return content;
    }

    private String _getHtmlFromTemplate(Map<String, String> params) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty("eventhandler.referenceinsertion.class", Biller.InsertHanler.class.getName());
        ve.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
        ve.init();
        Template template = ve.getTemplate("wang/huaichao/html/bss-mail-template.vm", "utf-8");


        VelocityContext vctx = new VelocityContext();

        for (String s : params.keySet()) {
            vctx.put(s, params.get(s));
        }

        StringWriter sw = new StringWriter();
        template.merge(vctx, sw);

        String html = sw.toString();

        return html;
    }

    private MimeBodyPart _createAttachment(Customer customer, int billingPeriod)
        throws MessagingException, IOException {

        File dir = _getPdfStoreDir(billingPeriod);

        File file = new File(dir, customer.getPid() + ".pdf");

        DataSource source;

        if (file.exists()) {

            FileInputStream fis = new FileInputStream(file);
            source = new ByteArrayDataSource(fis,
                MediaType.PDF.toString());
            fis.close();

        } else {
            BillPdf pdf = billPdfRepository.findByCustomerIdAndBillingPeriod(
                customer.getPid(), billingPeriod);

            if (pdf == null) {
                throw new RuntimeException("pdf not found customer id: " +
                    customer.getPid() + ", billing period: " + billingPeriod);
            }

            source = new ByteArrayDataSource(pdf.getPdfContent(),
                MediaType.PDF.toString());
        }


        MimeBodyPart attachment = new MimeBodyPart();
        attachment.setDataHandler(new DataHandler(source));
        attachment.setFileName(MimeUtility.encodeText(
            customer.getDisplayName() + "-" + billingPeriod + ".pdf",
            "utf-8",
            null
        ));
        return attachment;
    }

    private MimeMessage _createMessage(Customer customer, int billingPeriod, String sendTo) throws MessagingException, IOException {
        Session session = _createEmailSession();

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailUser));
        message.setRecipient(
            Message.RecipientType.TO,
            new InternetAddress(sendTo != null ? sendTo : customer.getContactEmail())
        );
        message.setRecipients(
            Message.RecipientType.BCC,
            new InternetAddress[]{
                new InternetAddress("huaichao.wang@ketianyun.com"),
                new InternetAddress("binrong.fang@ketianyun.com")
            }
        );
        message.setSubject("科天云" + billingPeriod + "账单");

        BodyPart content = _createHtmlBody(billingPeriod);
        MimeBodyPart attachment = _createAttachment(customer, billingPeriod);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(content);
        multipart.addBodyPart(attachment);

        message.setContent(multipart);
        return message;
    }

    private Set<String> getCustomerToIgnoreSet() {
        if (customerToIgnoreSet == null) {
            customerToIgnoreSet = new HashSet<>();
            fillInCustomerToIgnore();
        }
        return customerToIgnoreSet;
    }

    private void fillInCustomerToIgnore() {
        Iterable<CustomerToIgnore> all = customerToIgnoreRepository.findByType(
            CustomerToIgnore.CustomerToIgnoreType.EMAIL_PDF);
        for (CustomerToIgnore toIgnore : all) {
            customerToIgnoreSet.add(toIgnore.getCustomerId());
        }
    }
}
