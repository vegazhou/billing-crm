package com.kt.test.billing;

import com.kt.biz.bean.PersonalWebExBill;
import com.kt.biz.bean.SapBillItem;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.types.ChargeType;
import com.kt.entity.mysql.crm.Contract;
import com.kt.exception.OrderCollisionsDetectedException;
import com.kt.exception.OrderIncompleteException;
import com.kt.exception.WafException;
import com.kt.service.ChargeSchemeService;
import com.kt.service.ContractService;
import com.kt.service.OrgUserService;
import com.kt.service.SapReportService;
import com.kt.service.billing.BillService;
import com.kt.service.billing.PersonalWebExBillService;
import com.kt.service.mail.MailService;
import com.kt.session.PrincipalContext;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Vega Zhou on 2017/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestCollision {

    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    static class ContextConfiguration {
    }

    @Autowired
    ContractService contractService;
    @Autowired
    MailService mailService;
    @Autowired
    ChargeSchemeService chargeSchemeService;
    @Autowired
    OrgUserService orgUserService;
    @Autowired
    BillService billService;
    @Autowired
    SapReportService sapReportService;
    @Autowired
    PersonalWebExBillService personalWebExBillService;


    @org.junit.Test
    public void test() throws WafException, OrderIncompleteException, OrderCollisionsDetectedException, ParseException, IOException {
//        billService.recalculateBill("9482a623-7bca-4e83-abcc-f8405692fc01", new AccountPeriod("201705"));

//        List<PersonalWebExBill> bills = personalWebExBillService.listPersonWebExBill("jun.zhang@ketianyun.com");

//        AccountPeriod accountPeriod = new AccountPeriod("201704");
//        List<SapBillItem> items = sapReportService.findResellerVoipBillsByAccountPeriod(accountPeriod, new String[]{"KT01102", "SM40125HN4"});

//        return;


//        PrincipalContext.storePrincipal(orgUserService._doFindByUserName("weijia.zhou@tcl.com"));
//        chargeSchemeService.createChargeScheme("测试PSTN_PERSONAL_CHARGE", ChargeType.PSTN_PERSONAL_CHARGE);

//        Contract contract = contractService.findByContractId("4eeb5e58-fd1d-49a4-87db-37e3ee562e2d");
//        mailService.notifyWaitingFirstInstallmentConfirmation(contract);
        contractService.assertContractCompletionAndNoCollision("e1e46fe6-c00f-4802-8690-142513fb84d0");
    }
}
