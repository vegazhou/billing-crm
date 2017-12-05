package wang.huaichao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.huaichao.data.entity.crm.PstnPackage;
import wang.huaichao.data.entity.crm.PstnPackageOrder;
import wang.huaichao.data.entity.crm.PstnPackageUsage;
import wang.huaichao.data.repo.MeetingRecordRepository;
import wang.huaichao.data.repo.PstnPackageRepository;
import wang.huaichao.data.service.PstnPackageService;
import wang.huaichao.utils.DateBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 8/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
//@TransactionConfiguration(transactionManager = "billingTransactionManager")
public class JpaDataRepoTest {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    public static class Config {
    }

    @Autowired
    private PstnPackageRepository pstnPackageRepository;

    @Autowired
    private PstnPackageService pstnPackageService;

    @Autowired
    private MeetingRecordRepository meetingRecordRepository;

    private static final String orderId = "this-is-pstn-order-id-2";

    @Test
    @Transactional
    public void testFindAllPstnPackages() {
        Iterable<PstnPackage> all = pstnPackageRepository.findAll();

        for (PstnPackage pstnPackage : all) {
            System.out.println(pstnPackage.getUsages().size());
        }
    }

    @Test
    public void findByOrder() {
        PstnPackageOrder pstnPackageOrder = new PstnPackageOrder();
        pstnPackageOrder.setOrderId(orderId);


    }


    @Test
    @Transactional
    public void testDynamicUpdate() {
        PstnPackage pstnPackage = new PstnPackage();

        pstnPackage.setTotalMinutes(10000);
        pstnPackage.setLeftMinutes(10000);
        pstnPackage.setId("id001");
        pstnPackage.setCustomerId("customer id");
        pstnPackage.setSiteName("site name");
        pstnPackage.setStartDate(new Date());
        pstnPackage.setEndDate(new Date());
        pstnPackage.setOrderId("order id");
        pstnPackage.setCreatedAt(new Date());

        pstnPackageRepository.save(pstnPackage);
    }

    @Test
    public void testFoo() {
        List<String> lst = new ArrayList<>();
        lst.add("kmeritsz");
        meetingRecordRepository.deleteByBillingPeriodAndSiteNameIn(201608,lst);
    }
}
