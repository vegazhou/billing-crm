package com.kt.api.controller.billing;

import com.kt.entity.mysql.crm.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import wang.huaichao.data.mongo.BillPdf;
import wang.huaichao.data.mongo.BillPdfRepository;
import wang.huaichao.data.repo.CustomerRepository;
import wang.huaichao.data.service.EmailService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 1/18/2017.
 */
@Component
public class PdfService {
    private Logger log = LoggerFactory.getLogger(PdfService.class);
    private static final File BILLING_LOCAL_STORAGE;
    private static final String ZIP_SUFFIX = ".zip";

    public static Integer downloading = null;
    public static Integer packing = null;

    @Autowired
    private EmailService emailService;
    @Autowired
    private BillPdfRepository billPdfRepository;
    @Autowired
    @Qualifier("CustomerRepo2")
    private CustomerRepository customerRepository;


    static {
        String userHome = System.getProperty("user.home");
        BILLING_LOCAL_STORAGE = new File(userHome, "_billing_local_storage_");
    }

    @Async
    public Future<Integer> pack(int billingPeriod) {
        synchronized (this) {
            if (packing != null) {
                return null;
            } else {
                packing = billingPeriod;
            }
        }

        try {
            _pack(billingPeriod);
        } catch (IOException e) {
            log.error("pack pdf error", e);
        } finally {
            packing = null;
        }

        return new AsyncResult<>(billingPeriod);
    }

    private void _pack(int billingPeriod) throws IOException {

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
}