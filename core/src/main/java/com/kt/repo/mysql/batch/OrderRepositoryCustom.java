package com.kt.repo.mysql.batch;

import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Order;
import com.kt.service.SearchFilter;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/23.
 */
public interface OrderRepositoryCustom {

    List<Order> findOrderContext(String contractId);
    List<Order> listAllByPage(int curPage, SearchFilter search);
    List<Order> listAllByPageForReport(int curPage, SearchFilter search);
    List<Order> findMainProductOrders(String customerId, AccountPeriod accountPeriod);
    List<Order> findPstnProductOrders(String customerId, AccountPeriod accountPeriod);
    List<Order> findPortsProductOrders(String customerId, AccountPeriod accountPeriod);
    List<Order> findInEffectTrialProductOrdersByEndDate(String endDate);
    List<Order> findInEffectNormalProductOrdersByEndDateAndAgentType(String endDate, String agentType);
    List<Order> findOrderByStateAndDateAndProductIdOrderByHost(String orderState, String date, List<String> productIds);
}
