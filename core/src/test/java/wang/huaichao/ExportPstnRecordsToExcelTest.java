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
import wang.huaichao.data.repo.BillConfirmationRepository;
import wang.huaichao.data.service.BillingService;
import wang.huaichao.data.service.CrmDataService;
import wang.huaichao.data.service.EdrDataService;
import wang.huaichao.exception.BillingException;
import wang.huaichao.exception.InvalidBillingOperationException;
import wang.huaichao.utils.CollectionUtils;
import wang.huaichao.utils.DateBuilder;

import java.io.File;
import java.io.FileNotFoundException;
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
public class ExportPstnRecordsToExcelTest {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    public static class Config {
    }

    @Autowired
    private CrmDataService crmDataService;

    @Autowired
    private BillingService billingService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void test_export()
        throws IOException, InterruptedException,
        ParseException, ExecutionException, BillingException,
        InvalidBillingOperationException {

        String customerId = "40d7090c-4dae-489d-a39e-b0780ea6757a";
        int billingPeriod = 201710
            ;

        File file = new File("e:\\pstn-export-csv\\" + customerId + billingPeriod + ".xlsx");
        file.getParentFile().mkdirs();

        billingService.exportPstnFeeAsExcel(customerId, billingPeriod, new FileOutputStream(file));
    }

    @Test
    public void test_exportAllAgentRecords() throws IOException, InterruptedException, ParseException, ExecutionException, BillingException, InvalidBillingOperationException {
        int billingPeriod = 201703;
        List<String> ids = crmDataService.findNonDirectCustomerIds();
        for (String id : ids) {
            Customer agent = customerRepository.findOne(id);

            File file = new File("e:\\pstn-export-csv\\"
                + agent.getDisplayName() + "-"
                + billingPeriod + "-"
                + id + ".xlsx");
            file.getParentFile().mkdirs();

            billingService.exportPstnFeeAsExcel(id, billingPeriod, new FileOutputStream(file));
        }
    }

    @Test
    public void test_exportPstnFeeToExcel() throws IOException, ParseException, InvalidBillingOperationException, InterruptedException, ExecutionException, BillingException {
        List<String> customerIds = new ArrayList<>();
        customerIds.add("5861");
        customerIds.add("5225");

        List<Integer> billingPeriods = new ArrayList<>();
        billingPeriods.add(201612);
        billingPeriods.add(201701);
        billingPeriods.add(201702);

        for (String customerId : customerIds) {
            for (Integer billingPeriod : billingPeriods) {
                Customer customer = customerRepository.findOne(customerId);

                FileOutputStream os = new FileOutputStream(
                    "e:\\" + customer.getDisplayName() + "-" + billingPeriod + ".xlsx"
                );

                billingService.exportPstnFeeAsExcel(customerId, billingPeriod, os);
                os.close();
            }
        }
    }


    @Test
    public void test_exportPstnFeeToExcelUnderGivenAgent()
        throws IOException, ParseException, InterruptedException,
        ExecutionException, BillingException, InvalidBillingOperationException {
        int billingPeriod = 201707;

        // reseller
//        String customerId = "c6edfb14-81d8-4c4c-b97e-d2f0a5a33511";
        String customerId = "909b24fc-239c-4949-ac2b-e27d369637b9";


        FileOutputStream os = new FileOutputStream("e:\\pstn-records-under-agent-" + customerId + "-" + billingPeriod + ".xlsx");
        billingService.exportPstnFeeAsExcel(customerId, billingPeriod, os);
        os.close();
    }

    @Test
    public void test_exportPstnFeeToExcelUnderEachReseller2()
        throws IOException, InterruptedException, ParseException, ExecutionException,
        BillingException, InvalidBillingOperationException {

        int billingPeriod = 201710;
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

}
