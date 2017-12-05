package wang.huaichao;

import com.kt.biz.billing.AccountPeriod;
import com.kt.exception.WafException;
import com.kt.service.billing.BillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.huaichao.data.entity.crm.PstnStandardChargeOrder;
import wang.huaichao.data.entity.edr.CallDataRecord;
import wang.huaichao.data.repo.PstnPackageRepository;
import wang.huaichao.data.service.BillingService;
import wang.huaichao.data.service.CrmDataService;
import wang.huaichao.data.service.EdrDataService;
import wang.huaichao.data.service.PstnPackageService;
import wang.huaichao.exception.InvalidBillingOperationException;
import wang.huaichao.utils.DateBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 8/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CrmDataServiceTest {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    public static class Config {
    }

    @Autowired
    private EdrDataService edrDataService;

    @Autowired
    private CrmDataService crmDataService;

    @Autowired
    private PstnPackageRepository pstnPackageRepository;

    @Autowired
    private PstnPackageService pstnPackageService;

    @Autowired
    private BillingService billingService;

    @Autowired
    private BillService billService;

    @Test
    public void test_findCustomersUnderReseller2() throws IOException {
        int billingPeriod = 201701;
        List<String> customerIds = crmDataService.findCustomersUnderReseller2(billingPeriod);
        for (String customerId : customerIds) {
            System.out.println(customerId);
        }
    }
}
