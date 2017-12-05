package wang.huaichao;

import com.itextpdf.text.DocumentException;
import com.kt.exception.WafException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.huaichao.data.entity.crm.BillingProgress;
import wang.huaichao.data.entity.crm.BillingProgressPK;
import wang.huaichao.data.repo.BillingProgressRepository;

import java.util.Date;


/**
 * Created by Administrator on 8/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BillingProgressRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    public static class Config {
    }

    @Autowired
    private BillingProgressRepository billingProgressRepository;

    @Test
    public void test_create() {
        BillingProgress billingProgress = new BillingProgress();
        billingProgress.setCreatedAt(new Date());
        billingProgress.setCustomerId("000");
        billingProgress.setBillingPeriod(201608);
        billingProgress.setStartTime(new Date());
        billingProgress.setEndTime(new Date());
        billingProgress.setType(BillingProgressPK.BillingProgressType.SINGLE_PDF_GENERATION);
        billingProgress.setStatus(BillingProgress.BillingProgressStatus.IN_PROGRESS);

        billingProgressRepository.save(billingProgress);
    }

    @Test
    public void test_find() {
        BillingProgressPK billingProgressPK = new BillingProgressPK();

        billingProgressPK.setCustomerId("000");
        billingProgressPK.setBillingPeriod(201608);
        billingProgressPK.setType(BillingProgressPK.BillingProgressType.SINGLE_PDF_GENERATION);

        BillingProgress one = billingProgressRepository.findOne(
                billingProgressPK
        );

        System.out.println(one.getSucceededTasks());
    }
}
