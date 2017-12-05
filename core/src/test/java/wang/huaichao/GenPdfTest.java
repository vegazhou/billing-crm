package wang.huaichao;

import com.itextpdf.text.DocumentException;
import com.kt.biz.billing.AccountPeriod;
import com.kt.entity.mysql.billing.BillConfirmation;
import com.kt.entity.mysql.crm.Customer;
import com.kt.exception.WafException;
import com.kt.repo.mysql.batch.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.huaichao.data.mongo.BillPdf;
import wang.huaichao.data.mongo.BillPdfRepository;
import wang.huaichao.data.repo.BillConfirmationRepository;
import wang.huaichao.data.service.BillingService;
import wang.huaichao.data.service.CrmDataService;
import wang.huaichao.data.service.EdrDataService;
import wang.huaichao.exception.BillingException;
import wang.huaichao.exception.InvalidBillingOperationException;
import wang.huaichao.utils.CollectionUtils;
import wang.huaichao.utils.DateBuilder;
import wang.huaichao.utils.FileUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 8/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class GenPdfTest {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    public static class Config {
    }

    @Autowired
    private EdrDataService edrDataService;

    @Autowired
    private CrmDataService crmDataService;

    @Autowired
    private BillingService billingService;

    @Autowired
    @Qualifier("edrJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("BillConfirmationRepository2")
    private BillConfirmationRepository billConfirmationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    public BillPdfRepository billPdfRepository;


    @Test
    public void test_async_generateBillPdf() throws Exception {

        String customerId = "a0804982-65eb-453d-a042-198e5a7b3592";
        int billingPeriod = 201710;

        long ts = System.currentTimeMillis();

        Future future = billingService.generateBillPdf(
            billingPeriod,
            customerId
        );

        while (!future.isDone()) {
            Thread.sleep(1000);
        }

        try {
            future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("generate single pdf time: " +
            (System.currentTimeMillis() - ts));

        _download(customerId, billingPeriod);
    }

    private void _download(String customerId, int billingPeriod)
        throws IOException {

        BillPdf one = billPdfRepository.findByCustomerIdAndBillingPeriod(
            customerId,
            billingPeriod
        );

        FileUtils.write(
            one.getPdfContent(),
            "e:\\" + customerId + "-" + billingPeriod + "+" +
                System.currentTimeMillis() + ".pdf"
        );
    }

    @Test
    public void test_generateBillPdfWithGivenIds() throws Exception {
        List<String> lst = new ArrayList<>();

        lst.add("1569");
        lst.add("4590");
        lst.add("1844");

        int billingPeriod = 201610;

        for (String id : lst) {
            Future future = billingService.generateBillPdf(billingPeriod, id);
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test_async_generateAllBillPdf() throws ParseException, WafException, DocumentException, IOException, InterruptedException {
        long ts = System.currentTimeMillis();
        Future future = billingService.generateAllBillPdf(201710);
        while (!future.isDone()) {
            Thread.sleep(1000);
        }

        System.out.println("total time used for generate pdf: " + (System.currentTimeMillis() - ts));
    }
}
