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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.huaichao.data.repo.BillConfirmationRepository;
import wang.huaichao.data.service.Biller;
import wang.huaichao.data.service.BillingService;
import wang.huaichao.data.service.CrmDataService;
import wang.huaichao.data.service.EdrDataService;
import wang.huaichao.exception.BillingException;
import wang.huaichao.exception.InvalidBillingOperationException;
import wang.huaichao.utils.CollectionUtils;
import wang.huaichao.utils.DateBuilder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 8/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BillingServiceTest {
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

    @Test
    public void test_getPstnFeeByBillingPeriodAndOrderId() throws BillingException {
        float cost = billingService.getPstnFeeByBillingPeriodAndOrderId("442b876b-7853-4fdd-a545-b085a826dab3", 201607);
        System.out.println("cost: " + cost);
    }

    @Test
    public void test_getPstnFeeByBillingPeriodAndCustomerId() throws BillingException {
        float cost = billingService.getPstnFeeByBillingPeriodAndCustomerId(
            "4668980b-e776-4fcd-9aa2-f021a4cdfc49",
            201706
        );

        System.out.println("cost for customer: " + cost);
    }


    @Test
    public void test_async_batchCalculatingPstnFee() throws ParseException, InvalidBillingOperationException,
        IOException, InterruptedException, BillingException {

        long ts = System.currentTimeMillis();
        Future future = billingService.batchCalculatingPstnFee(201702);

        while (!future.isDone()) {
            System.out.println("====== sleep 1s ======");
            Thread.sleep(1000);
        }

        System.out.println("total time used for calculating pstn fee: " + (System.currentTimeMillis() - ts));

    }

    @Test
    public void test_async_calculatingPstnFeeByCustomer() throws ParseException, InvalidBillingOperationException, IOException, InterruptedException {
        Future future = billingService.calculatingPstnFeeByBillingPeriodAndCustomerId(
            201709,
            "e954349d-6cd7-4f63-a03a-9d26252c7c2e"
        );
        while (!future.isDone()) {
            System.out.println("====== sleep 1s ======");
            Thread.sleep(1000);
        }
    }

    @Test
    public void test_async_calculatingPstnFeeByCustomer2() throws ParseException, InvalidBillingOperationException, IOException, InterruptedException {
        String customerId = "a23e7e1a-2d65-4512-b46e-fcca19c2a69a";

        for (int bp = 201702; bp < 201707; bp++) {

            Future future = billingService.calculatingPstnFeeByBillingPeriodAndCustomerId(
                bp, customerId
            );

            while (!future.isDone()) {
                System.out.println("====== sleep 1s ======");
                Thread.sleep(1000);
            }

        }

    }

    @Test
    public void test_redoFailedPstnCalculations() throws InvalidBillingOperationException, InterruptedException {
        int billingPeriod = 201612;
        Future<Integer> rst = billingService.redoFailedPstnCalculations(billingPeriod);

        while (!rst.isDone()) {
            System.out.println("====== sleep 1s ======");
            Thread.sleep(1000);
        }
    }

    @Test
    public void recalculateFromBegin() throws ParseException, IOException, InvalidBillingOperationException {
        String customerId = "5885";
        int start = 201608;
        int end = 201610;


        DateBuilder db = new DateBuilder(Global.yyyyMM_FMT.parse(start + ""));

        while (true) {
            int billingPeriod = dateToBillingPeriod(db.build());
            if (billingPeriod > end) break;


            Future future = billingService.calculatingPstnFeeByBillingPeriodAndCustomerId(
                billingPeriod, customerId);

            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            db.nextMonth();
        }
    }

    private int dateToBillingPeriod(Date date) {
        return Integer.valueOf(Global.yyyyMM_FMT.format(date));
    }

    @Test
    public void test_async_generateBillPdf() throws Exception {
        long ts = System.currentTimeMillis();
        Future future = billingService.generateBillPdf(
            201710,
            "7682"
        );
        while (!future.isDone()) {
            Thread.sleep(1000);
        }
        try {
            future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("generate single pdf time: " + (System.currentTimeMillis() - ts));
    }

    @Test
    public void test_generateBillPdfWithGivenIds() throws Exception {
        List<String> lst = new ArrayList<>();

        lst.add("1569");
        lst.add("4590");
        lst.add("1844");
        lst.add("1644");
        lst.add("5143");
        lst.add("1382");
        lst.add("1945");
        lst.add("3997");
        lst.add("2181");
        lst.add("6304");
        lst.add("6496f0dc-9df0-486f-885d-c754a85812dc");
        lst.add("45024222-d53f-4073-8f5e-47779f48f005");
        lst.add("0986314c-ab08-4ceb-b99d-e82bb1c9dbad");
        lst.add("6c108769-5582-4046-bae2-3c5ea30ef9fd");
        lst.add("a910a33d-3c5d-40f0-9d3e-882c01e2a43d");
        lst.add("48430a76-f38d-4345-a85a-c7caecebcbb7");

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
        Future future = billingService.generateAllBillPdf(201612);
        while (!future.isDone()) {
            Thread.sleep(1000);
        }

        System.out.println("total time used for generate pdf: " + (System.currentTimeMillis() - ts));
    }

    @Test
    public void test_exportPstnFeeAsExcel() throws IOException, ParseException, InvalidBillingOperationException, InterruptedException, ExecutionException, BillingException {
        String customerId = "7382";
        FileOutputStream os = new FileOutputStream("e:\\" + customerId + ".xlsx");
        billingService.exportPstnFeeAsExcel(customerId, 201701, os);
        os.close();
    }

    @Test
    public void test_exportPstnFeeAsExcelUnderGivenAgent() throws IOException, ParseException, InterruptedException, ExecutionException, BillingException, InvalidBillingOperationException {
        int billingPeriod = 201702;

        // reseller
//        String customerId = "c6edfb14-81d8-4c4c-b97e-d2f0a5a33511";
        String customerId = "c6edfb14-81d8-4c4c-b97e-d2f0a5a33511";


        FileOutputStream os = new FileOutputStream("e:\\pstn-records-under-agent-" + customerId + "-" + billingPeriod + ".xlsx");
        billingService.exportPstnFeeAsExcel(customerId, billingPeriod, os);
        os.close();
    }

    @Test
    public void test_exportPstnFeeAsExcelUnderEachReseller2()
        throws IOException, InterruptedException, ParseException, ExecutionException,
        BillingException, InvalidBillingOperationException {

        int billingPeriod = 201701;
        List<String> reseller2s = crmDataService.findReseller2s();
        for (String id : reseller2s) {
            Customer one = customerRepository.findOne(id);
            FileOutputStream os = new FileOutputStream(
                "e:\\" + one.getDisplayName() + "-" + billingPeriod + ".xlsx"
            );
            billingService.exportPstnFeeAsExcel(id, billingPeriod, os);
            os.close();
        }

    }

    @Test
    public void test_findPstnCustomers() throws ParseException, IOException {
        String billingPeriod = "201610";
        Date x = Global.yyyyMM_FMT.parse(billingPeriod);
        DateBuilder db = new DateBuilder(x);
        Date start = db.beginOfMonth().build();
        Date end = db.nextMonth().build();

        List<String> customerIds;

        customerIds = crmDataService.findStandardChargeCustomerByEffectiveDateIntersect(
            start, end
        );

        System.out.println(customerIds.size());

        for (String customerId : customerIds) {
            if (customerId.equals("7382"))
                System.out.println("=== found ===");
        }
    }

    @Test
    public void test_findBillConfirmedCustomers() throws ParseException {
        int billingPeriod = 201610;

        AccountPeriod accountPeriod = new AccountPeriod(billingPeriod + "");
        accountPeriod = accountPeriod.nextPeriod();

        List<BillConfirmation> confirmations = billConfirmationRepository.findByAccountPeriod(
            accountPeriod.toString());

        for (BillConfirmation confirmation : confirmations) {
            System.out.println(confirmation.getCustomerId());
        }

        System.out.println(confirmations.size());
    }


    @Test
    public void foo() throws IOException, ParseException {
        int billingPeriod = 201611;

        DateBuilder db = new DateBuilder();
        Date start = db.beginOfMonth().build();
        Date end = db.nextMonth().build();

        List<BillConfirmation> confirmations = billConfirmationRepository.findByAccountPeriod("201612");
        List<String> customerIds = CollectionUtils.collectString(confirmations, "customerId");
        List<String> customerIdsOrderInEffect = crmDataService.findStandardChargeCustomerByEffectiveDateIntersect(
            start,
            end
        );
        List<String> formalActiveCustomers = crmDataService.findFormalActiveCustomers(billingPeriod);

        List<String> customerIdsOrderInEffect2 = crmDataService.findCustomerWithEffectiveOrder(
            start,
            end
        );

        Collections.sort(customerIds);
        Collections.sort(customerIdsOrderInEffect);
        customerIds.retainAll(customerIdsOrderInEffect2);
        customerIds.retainAll(formalActiveCustomers);

        System.out.println(customerIds.size());


    }
}
