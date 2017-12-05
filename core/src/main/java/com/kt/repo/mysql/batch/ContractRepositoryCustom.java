package com.kt.repo.mysql.batch;

import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Contract;
import com.kt.service.SearchFilter;

import java.util.List;

/**
 * Created by Garfield Chen on 2016/3/24.
 */
public interface ContractRepositoryCustom {

	Page<Contract> listAllByPage(int curPage, SearchFilter search);
	Page<Contract> listAllByPageForConfirm(int curPage, SearchFilter search);

	List<Contract> findNonReseller2Contracts(String directCustomerId);

	List<Contract> findContractsOfReseller2(String resellerId);

	List<Contract> findAccountableNonReseller2Contracts(String directCustomerId, AccountPeriod accountPeriod);

	List<Contract> findAccountableReseller2Contracts(String resellerCustomerId, AccountPeriod accountPeriod);
}
