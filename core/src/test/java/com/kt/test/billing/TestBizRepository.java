package com.kt.test.billing;

import com.kt.biz.model.biz.impl.BizWebExMC;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByHosts;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.order.CollisionDetectResult;
import com.kt.biz.model.order.compatcheck.CollisionDetectorWebExConfByHosts;
import com.kt.biz.types.BizType;
import com.kt.biz.types.ChargeType;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.ChargeScheme;
import com.kt.entity.mysql.crm.Order;
import com.kt.exception.WafException;
import com.kt.repo.mysql.batch.ChargeSchemeRepository;
import com.kt.repo.mysql.batch.OrderRepository;
import com.kt.repo.mysql.batch.ProductRepository;
import com.kt.service.*;
import com.kt.util.DateUtil;
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
public class TestBizRepository {

    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    static class ContextConfiguration {
    }

    @Autowired
    BizSchemeService bizSchemeService;
    @Autowired
    ChargeSchemeService chargeSchemeService;
    @Autowired
    ChargeSchemeRepository chargeSchemeRepository;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    WebExSiteService webExSiteService;


    @Test
    public void clearAllChargeSchemes() throws WafException {
        chargeSchemeService.deleteAllChargeSchemes();
    }

    @Test
    public void test1() throws WafException {

    }


    @Test
    public void test2() throws WafException {
//        BizWebExTC b = (BizWebExTC) bizSchemeService.createBizScheme("TC1-100", BizType.WEBEX_TC);
//        WebExConfMonthlyPayByPorts c = (WebExConfMonthlyPayByPorts) chargeSchemeService.createChargeScheme("TC1-100月租", ChargeType.MONTHLY_PAY_BY_PORTS);
//        chargeSchemeService.sendChargeScheme4Approval(c.getId());
//        chargeSchemeService.approveChargeScheme(c.getId());


//        AbstractBizScheme b = bizSchemeService.findBizSchemeById("ee5fe8e8-d5cf-4345-8c74-025b5ab5afdf");
//        AbstractChargeScheme c = chargeSchemeService.findChargeSchemeById("f0805159-cf45-46a2-9c1e-2fc6b90a4070");
//        productService.createProduct("TC1-100包月产品", b.getId(), c.getId());

        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setOrderByField("DISPLAY_NAME");
        searchFilter.setOrderType("DESC");
        searchFilter.setType("MONTHLY_PAY_BY_PORTS");
//        searchFilter.setDisplayName("EC");
//        searchFilter.setState(CommonState.DRAFT.toString());
//        searchFilter.setState(CommonState.END_OF_LIFE.toString());
//        searchFilter.setDisplayName("MC");
//        Page<Biz> page = bizSchemeService.listAllTemplates(1, searchFilter);
        Page<ChargeScheme> page = chargeSchemeService.listAllTemplates(0, searchFilter);
        return;
    }


    @Test
    public void test3() throws ParseException, WafException {
//        List<ChargeScheme> results = chargeSchemeRepository.findByStateOrderByTypeAsc(CommonState.DRAFT.toString());
        List<Order> records = orderRepository.findAll();
        List<OrderBean> orders = new ArrayList<>();
        for (Order record : records) {
            OrderBean order = new OrderBean("");
            order.setStartDate(DateUtil.toDate(record.getEffectiveStartDate()));
            order.setEndDate(DateUtil.toDate(record.getEffectiveEndDate()));
            order.setBiz(bizSchemeService.findBizSchemeById(record.getBizId()));
            order.setChargeScheme(chargeSchemeService.findChargeSchemeById(record.getChargeId()));
            orders.add(order);
        }

        Order newOrder = orderService.findOrderById("afd7d69e-f37a-4f5b-bc1d-e44417ef4640");
        OrderBean order = new OrderBean("");
        order.setStartDate(DateUtil.toDate(newOrder.getEffectiveStartDate()));
        order.setEndDate(DateUtil.toDate(newOrder.getEffectiveEndDate()));
        order.setBiz(bizSchemeService.findBizSchemeById(newOrder.getBizId()));
        order.setChargeScheme(chargeSchemeService.findChargeSchemeById(newOrder.getChargeId()));
        CollisionDetectorWebExConfByHosts check = new CollisionDetectorWebExConfByHosts();
        check.setMe(order);
        CollisionDetectResult r = check.detectAgainst(orders);
        return;
    }

    @Test
    public void test4() throws WafException {
//        webExSiteService.draftSite4Contract("f698b0af-4b73-4281-852f-0c24304ea662", "vega3");
        List<String> a =webExSiteService.listSiteNamesAvailable4Contract("f698b0af-4b73-4281-852f-0c24304ea662");
        return;
    }
}
