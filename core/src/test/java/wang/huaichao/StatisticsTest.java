package wang.huaichao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.huaichao.data.service.StatisticsService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 12/13/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class StatisticsTest {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    public static class Config {
    }

    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void testExportBySalse() throws IOException {
        int billingPeriod = 201701;
        statisticsService.exportMontholyPstnFeeSummarizedBySalse(
            billingPeriod,
            new FileOutputStream("e:\\" + billingPeriod + "-电话费用-按销售汇总.xlsx")
        );
    }
}
