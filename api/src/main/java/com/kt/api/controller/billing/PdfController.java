package com.kt.api.controller.billing;

import com.kt.entity.mysql.crm.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wang.huaichao.data.entity.crm.BillingProgress;
import wang.huaichao.data.entity.crm.BillingProgressPK;
import wang.huaichao.data.mongo.BillPdf;
import wang.huaichao.data.mongo.BillPdfRepository;
import wang.huaichao.data.repo.BillingProgressRepository;
import wang.huaichao.data.repo.CustomerRepository;
import wang.huaichao.data.service.BillingService;
import wang.huaichao.data.service.EmailService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 1/18/2017.
 */
@RestController
@RequestMapping("/pdf")
public class PdfController {
    private Logger log = LoggerFactory.getLogger(PdfController.class);
    private static final File BILLING_LOCAL_STORAGE;
    private static final String ZIP_SUFFIX = ".zip";

    private static Integer downloading = null;
    private static Integer packing = null;

    @Autowired
    private BillingProgressRepository billingProgressRepository;
    @Autowired
    private BillingService billingService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BillPdfRepository billPdfRepository;
    @Autowired
    @Qualifier("CustomerRepo2")
    private CustomerRepository customerRepository;
    @Autowired
    private PdfService pdfService;


    static {
        String userHome = System.getProperty("user.home");
        BILLING_LOCAL_STORAGE = new File(userHome, "_billing_local_storage_");
    }


    @RequestMapping("/status")
    public ActionResult status(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize) {

        pageNumber = Math.max(0, pageNumber);
        pageSize = Math.max(1, pageSize);

        Page<BillingProgress> progresses =
            billingProgressRepository.findByTypeOrderByBillingPeriodDesc(
                BillingProgressPK.BillingProgressType.BATCH_PDF_GENERATION,
                new PageRequest(pageNumber, pageSize)
            );


        ActionResult ret = new ActionResult();
        ret.addPayload("page", progresses);

        Map<String, String> map = _listZipFiles();

        if (map.size() > 0) ret.addPayload("zips", map);

        if (pdfService.packing != null) ret.addPayload("packing", pdfService.packing);
        if (downloading != null) ret.addPayload("downloading", downloading);


        return ret;
    }

    @RequestMapping("/generate/{billingPeriod}")
    public ActionResult calculate(@PathVariable int billingPeriod) {
        try {
            billingService.generateAllBillPdf(billingPeriod);
            return ActionResult.SUCCESS;
        } catch (Exception e) {
            return new ActionResult(true, e.getMessage());
        }
    }

    @RequestMapping("/status/{billingPeriod}")
    public ActionResult progressStatus(@PathVariable int billingPeriod) {
        List<BillingProgress> progresses = billingProgressRepository.findByBillingPeriodAndType(
            billingPeriod,
            BillingProgressPK.BillingProgressType.BATCH_PDF_GENERATION
        );
        return new ActionResult().addPayload("progresses", progresses);
    }

    @Async
    @RequestMapping("/pack/{billingPeriod}")
    public ActionResult pack(@PathVariable int billingPeriod) {

        pdfService.pack(billingPeriod);

        return ActionResult.SUCCESS;
    }

    @Async
    public Future<Integer> _pack(int billingPeriod) {

        try {
            _asyncPack(billingPeriod);
        } catch (IOException e) {
            log.error("pack pdf error", e);
        } finally {
            packing = null;
        }

        return new AsyncResult<>(billingPeriod);
    }

    private void _asyncPack(int billingPeriod) throws IOException {

        BILLING_LOCAL_STORAGE.mkdirs();


        List<String> cids = emailService.getMailingCustomers(billingPeriod);
        List<Customer> customers = customerRepository.findByPidIn(cids);
        Map<String, Customer> customerMap = new HashMap<>();

        for (Customer customer : customers) {
            customerMap.put(customer.getPid(), customer);
        }

        List<BillPdf> pdfs = billPdfRepository
            .findByBillingPeriodAndCustomerIdIn(billingPeriod, cids);


        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
            new File(BILLING_LOCAL_STORAGE, billingPeriod + ZIP_SUFFIX)));

        for (BillPdf pdf : pdfs) {
            Customer customer = customerMap.get(pdf.getCustomerId());

            String entryName = customer.getDisplayName()
                + "-" + customer.getPid()
                + "-" + customer.getCode() + ".pdf";

            out.putNextEntry(new ZipEntry(entryName));

            out.write(pdf.getPdfContent());
            out.closeEntry();
        }

        out.close();
    }

    @RequestMapping("/download/{billingPeriod}")
    public void download(@PathVariable int billingPeriod,
                         HttpServletResponse response) throws IOException {

        synchronized (this) {
            if (downloading != null) {
                return;
            } else {
                downloading = billingPeriod;
            }
        }

        try {
            _download(billingPeriod, response);
        } catch (IOException e) {
            throw e;
        } finally {
            downloading = null;
        }
    }


    private void _download(
        int billingPeriod,
        HttpServletResponse response) throws IOException {


        File zip = new File(BILLING_LOCAL_STORAGE, billingPeriod + ZIP_SUFFIX);

        ServletOutputStream os = response.getOutputStream();

        if (!zip.exists()) {
            response.sendError(404);
            os.close();
            return;
        }


        response.addHeader("content-type", "application/zip");
        response.addHeader("content-disposition",
            "attachment;filename=" + billingPeriod + ZIP_SUFFIX);


        FileInputStream fis = new FileInputStream(zip);
        byte[] buffer = new byte[4096];
        int len;

        while ((len = fis.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }

        fis.close();
        os.close();

    }


    private Map<String, String> _listZipFiles() {
        Map<String, String> map = new HashMap<>();
        Pattern ptn = Pattern.compile("^(\\d{6})\\.zip$");

        File[] files = BILLING_LOCAL_STORAGE.listFiles();

        if (files != null) {
            for (File file : files) {
                Matcher matcher = ptn.matcher(file.getName());
                if (matcher.find()) {
                    map.put(matcher.group(1), matcher.group(1));
                }
            }
        }

        return map;
    }
}