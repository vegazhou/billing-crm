package com.kt.test.billing;

import com.kt.biz.model.biz.impl.BizWebExMC;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByHosts;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.compatcheck.CollisionDetectorWebExConfByHosts;
import com.kt.biz.types.BizType;
import com.kt.biz.types.ChargeType;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.TelephoneCallinNumberEntity;
import com.kt.entity.mysql.billing.TelephoneLandKindEntity;
import com.kt.entity.mysql.billing.TelephoneTollMappingEntity;
import com.kt.entity.mysql.crm.ChargeScheme;
import com.kt.entity.mysql.crm.Order;
import com.kt.exception.WafException;
import com.kt.repo.mysql.batch.ChargeSchemeRepository;
import com.kt.repo.mysql.batch.OrderRepository;
import com.kt.repo.mysql.batch.ProductRepository;
import com.kt.repo.mysql.billing.TelephoneCallinNumberRepository;
import com.kt.repo.mysql.billing.TelephoneLandKindRepository;
import com.kt.repo.mysql.billing.TelephoneTollMappingRepository;
import com.kt.service.*;
import com.kt.util.DateUtil;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestPSTNRepository {
    private static final Logger LOGGER = Logger.getLogger(TestPSTNRepository.class);
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    static class ContextConfiguration {
    }

    @Autowired
    private TelephoneCallinNumberRepository telephoneCallinNumberRepository;

    @Autowired
    private TelephoneLandKindRepository telephoneLandKindRepository;

    @Autowired
    private TelephoneTollMappingRepository telephoneTollMappingRepository;

    @Test
    public void testTelephoneCallinNumberRepository(){
        List<TelephoneCallinNumberEntity> telephoneCallinNumbers = telephoneCallinNumberRepository.findAll();
        LOGGER.debug(telephoneCallinNumbers.size());
        for(TelephoneCallinNumberEntity entity: telephoneCallinNumbers){
            LOGGER.debug(ToStringBuilder.reflectionToString(entity));
        }
    }

    @Test
    public void testTelephoneLandKindRepository(){
        List<TelephoneLandKindEntity> telephoneCallinNumbers = telephoneLandKindRepository.findAll();
        LOGGER.debug(telephoneCallinNumbers.size());
        for(TelephoneLandKindEntity entity: telephoneCallinNumbers){
            LOGGER.debug(ToStringBuilder.reflectionToString(entity));
        }
    }

    @Test
    public void testTelephoneTollMappingRepository(){
        List<TelephoneTollMappingEntity> telephoneCallinNumbers = telephoneTollMappingRepository.findAll();
        LOGGER.debug(telephoneCallinNumbers.size());
        for(TelephoneTollMappingEntity entity: telephoneCallinNumbers){
            LOGGER.debug(ToStringBuilder.reflectionToString(entity));
        }
    }
}
