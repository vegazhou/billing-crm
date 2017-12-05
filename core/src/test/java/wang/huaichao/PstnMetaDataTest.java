package wang.huaichao;

import com.itextpdf.text.DocumentException;
import com.kt.exception.WafException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.huaichao.data.service.*;
import wang.huaichao.exception.BillingException;
import wang.huaichao.exception.InvalidBillingOperationException;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Administrator on 8/16/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PstnMetaDataTest {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    public static class Config {
    }

    @Autowired
    private PstnMetaData pstnMetaData;

    @Test
    public void test_initialize() {
        pstnMetaData.initialize(false);
    }
}
