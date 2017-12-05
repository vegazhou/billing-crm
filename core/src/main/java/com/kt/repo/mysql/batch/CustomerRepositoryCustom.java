package com.kt.repo.mysql.batch;

import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Customer;
import com.kt.service.SearchFilter;

import java.util.List;

/**
 * Created by Garfield Chen on 2016/3/24.
 */
public interface CustomerRepositoryCustom {

    Page<Customer> listAllNonAgentCustomers(int curPage, SearchFilter search);

    Page<Customer> listAllAgentCustomers(int curPage, SearchFilter search);

    Page<Customer> paginateReseller2(int curPage, SearchFilter search);

    List<Customer> listAllAgents();

    List<Customer> listAllAgentsAndReseller2();

    String nextCode();
}
