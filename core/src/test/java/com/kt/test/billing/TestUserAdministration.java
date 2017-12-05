package com.kt.test.billing;

import com.kt.biz.types.RoleType;
import com.kt.entity.mysql.billing.AccountOperation;
import com.kt.exception.WafException;
import com.kt.repo.mysql.billing.AccountOperationRepository;
import com.kt.service.OrgUserService;
import com.kt.session.PrincipalContext;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * Created by Vega Zhou on 2016/3/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestUserAdministration {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    static class ContextConfiguration {
    }

    @Autowired
    OrgUserService orgUserService;

    @Autowired
    AccountOperationRepository accountOperationRepository;

    @org.junit.Test
    public void test() throws WafException {
//        PrincipalContext.storePrincipal(orgUserService._doFindByUserName("ciadmin@ketianyun.com"));
//        orgUserService.createUser("weijia.zdhou@tcl.com", Arrays.asList(RoleType.CHARGE_AUDITOR, RoleType.CONTRACT_AUDITOR));


//        accountOperationRepository.findOne(0l);
    }
}
