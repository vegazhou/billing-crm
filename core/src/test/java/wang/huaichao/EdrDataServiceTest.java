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
public class EdrDataServiceTest {
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

    @Test
    public void testFindPstnRecords() throws IOException, ParseException {
        DateBuilder db = new DateBuilder().beginOfMonth().prevMonth();
        Date end = db.build();
        Date start = db.prevMonth().prevMonth().prevMonth().build();

        List<String> siteNames = new ArrayList<>();
        siteNames.add("kland");
        siteNames.add("ktrktest-160219");
        siteNames.add("ktrktest20160323");

        List<CallDataRecord> records = edrDataService.findPstnRecords(start, end, siteNames);

        for (CallDataRecord record : records) {
            System.out.println(record.getSiteName() + "," + record.getHostName() + "," + record.getConfName());
        }
    }

    @Test
    public void testFindAllPstnStandardChargeOrder() throws IOException {
        DateBuilder dateBuilder = new DateBuilder().beginOfMonth();
        Date end = dateBuilder.build();
        Date start = dateBuilder.prevMonth().prevMonth().build();

        Map<String, PstnStandardChargeOrder> orderMap = crmDataService.findAllPstnStandardChargeOrder(start, end);

        for (PstnStandardChargeOrder order : orderMap.values()) {
            System.out.println(order.getPstnRatesId());
            System.out.println("=================================================================");
        }
    }

    @Test
    public void testFoo() throws IOException, InvalidBillingOperationException {
        Map<String, Integer> map = new HashMap<>();

        map.put("81d4d7c0-1834-47b3-b1a8-ea303695f0c7", 2916);
        map.put("bae9b71f-ec69-4dc2-b4e8-ba263ab9bdee", 1926);
        map.put("0e65cfe0-8d89-470a-9f61-9e174da7b247", 991);


        map.put("8c3c9be1-e920-440d-8df6-a933795f1b96", 4562 + 82);
        map.put("d80a3e31-9eb5-4fe5-a8cd-23800cbd1cd4", 0);

        map.put("acc05a2b-4323-4ea6-9864-b122ad21fc13", 69);
        map.put("c300e0d7-46e0-4268-bdf0-284cb320af52", 25683);
        map.put("2be4914b-116c-4c5a-a3f1-e68cb00d7d8d", 482);
        map.put("0ae137cc-cd8a-4a0e-89f8-37cfd46a3d6f", 441);

//        pstnPackageService.importPstnPackageFromBSS1_0(map, 201607);
//        pstnPackageService.updateJuly2016LeftMinutes(map);
    }


    @Test
    public void test_findVoipAndDataRecords() throws IOException {
        DateBuilder dateBuilder = new DateBuilder().beginOfMonth();
        Date end = dateBuilder.prevMonth().build();
        Date start = dateBuilder.prevMonth().build();
        List<String> siteNames = new ArrayList<>();
        siteNames.add("moma1");
        siteNames.add("moma2");

        System.out.println(start);
        System.out.println(end);

        List<CallDataRecord> voipAndDataRecords = edrDataService.findVoipAndDataRecords(start, end, siteNames);
        for (CallDataRecord record : voipAndDataRecords) {
            System.out.println(record.getHostName() + ":" + record.getDuration());
        }
    }

}
