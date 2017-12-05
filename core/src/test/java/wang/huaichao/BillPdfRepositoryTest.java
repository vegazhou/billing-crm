package wang.huaichao;

import com.kt.entity.mysql.crm.Customer;
import com.kt.repo.mysql.batch.CustomerRepository;
import com.mongodb.BasicDBObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.huaichao.data.mongo.BillPdf;
import wang.huaichao.data.mongo.BillPdfRepository;
import wang.huaichao.data.service.BillingService;
import wang.huaichao.data.service.CrmDataService;
import wang.huaichao.data.service.EmailService;
import wang.huaichao.utils.CollectionUtils;
import wang.huaichao.utils.FileUtils;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Created by Administrator on 8/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BillPdfRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    public static class Config {
    }

    @Autowired
    public BillPdfRepository billPdfRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private CrmDataService crmDataService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BillingService billingService;

    @Autowired
    private EmailService emailService;

    @Test
    public void test_savePdf() throws IOException {
        ByteArrayOutputStream baos = FileUtils.readRaw(new FileInputStream("e:\\test-mongo-limit.rar"));

        BillPdf billPdf = new BillPdf();
        billPdf.setCreatedAt(new Date());
        billPdf.setId("00112");
        billPdf.setCustomerId("00112");
        billPdf.setCustomerCode("code 001");
        billPdf.setPdfContent(baos.toByteArray());

        billPdfRepository.save(billPdf);
    }

    @Test
    public void test_saveFileLargeThan16m() throws FileNotFoundException {
        FileInputStream is = new FileInputStream("e:\\test-mongo-limit.rar");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("customerId", "customer id 001");
        gridFsTemplate.store(is, "abc.rar", basicDBObject);
    }

    @Test
    public void test_findByCustomerIdAndBillingPeriod() throws IOException {
        BillPdf one = billPdfRepository.findByCustomerIdAndBillingPeriod("6c108769-5582-4046-bae2-3c5ea30ef9fd", 201609);
        FileUtils.write(one.getPdfContent(), "e:\\6c108769-5582-4046-bae2-3c5ea30ef9fd.pdf");
    }

    @Test
    public void test_downloadPdfByCustomerId() throws IOException {
        String customerId = "7824";
        int billingPeriod = 201710;


        BillPdf one = billPdfRepository.findByCustomerIdAndBillingPeriod(customerId, billingPeriod);

        FileUtils.write(one.getPdfContent(), "e:\\" + customerId + "-" + billingPeriod + ".pdf");
    }

    @Test
    public void downloadAll() throws IOException {
        List<BillPdf> pdfs = billPdfRepository.findByBillingPeriod(201612);

        String zipFile = Global.USER_HOME_DIR + File.separator + "all.zip";

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

        for (BillPdf pdf : pdfs) {
            out.putNextEntry(createEntry(pdf));
            out.write(pdf.getPdfContent());
            out.closeEntry();
        }

        out.close();
    }

    private ZipEntry createEntry(BillPdf pdf) {
        Customer c = customerRepository.findOne(pdf.getCustomerId());
        return new ZipEntry(c.getDisplayName() + "-" + pdf.getCustomerCode() + "-" + pdf.getCustomerId() + ".pdf");
    }

    @Test
    public void downloadAll2() throws IOException, ParseException {
        int billingPeriod = 201612;

        List<String> cids = crmDataService.findFormalActiveCustomers(billingPeriod);

        List<BillPdf> pdfs = billPdfRepository.findByBillingPeriodAndCustomerIdIn(billingPeriod, cids);

        String zipFile = Global.USER_HOME_DIR + File.separator + "all.zip";

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

        for (BillPdf pdf : pdfs) {
            out.putNextEntry(createEntry(pdf));
            out.write(pdf.getPdfContent());
            out.closeEntry();
        }

        out.close();
    }

    @Test
    public void downloadAllPdfsEmailToCustomers() throws IOException {
//        1509941716415
//        1509941748795
//        1509941772206
//        1509941834495
        int billingPeriod = 201710;
        System.out.println(System.currentTimeMillis());
        List<String> cids = emailService.getMailingCustomers(billingPeriod);
        System.out.println(System.currentTimeMillis());
        List<BillPdf> pdfs = billPdfRepository.findByBillingPeriodAndCustomerIdIn(billingPeriod, cids);
        System.out.println(System.currentTimeMillis());
        String zipFile = "e:\\" + billingPeriod + "-pdfs-send-to-customers.zip";

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

        for (BillPdf pdf : pdfs) {
            out.putNextEntry(createEntry(pdf));
            out.write(pdf.getPdfContent());
            out.closeEntry();
        }

        out.close();
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void genAndDownloadPdf() throws Exception {
        String customerId = "d73720f6-e7ca-4952-b5bd-983d577fb9d5";
        int billingPeriod = 201708;

        Future future = billingService.generateBillPdf(billingPeriod, customerId);

        try {
            future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return;
        }


        BillPdf one = billPdfRepository.findByCustomerIdAndBillingPeriod(
            customerId, billingPeriod
        );

        FileUtils.write(one.getPdfContent(), "e:\\" + customerId + "-" + billingPeriod + ".pdf");
    }
}
