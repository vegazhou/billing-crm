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
public class CalculatePSTNTest {
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
    public void calcWithCustomerIds() throws ParseException,
        InvalidBillingOperationException,
        IOException,
        ExecutionException,
        InterruptedException {


        int billingPeriod = 201709;

        List<String> ids = new ArrayList<>();

        ids.add("e954349d-6cd7-4f63-a03a-9d26252c7c2e");
        ids.add("48746660-95ea-45ed-9d97-fac342be53ed");
        ids.add("2b47a5e0-ce4d-4ffc-8669-3e1c27687b3d");

        for (String id : ids) {
            Future<Integer> f = billingService.calculatingPstnFeeByBillingPeriodAndCustomerId(
                billingPeriod,
                id
            );

            f.get();
        }

    }
}
