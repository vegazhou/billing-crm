package com.kt.repo.mysql.billing;


import com.kt.biz.bean.*;
import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.BillFormalDetail;
import com.kt.service.SearchFilter;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/6/16.
 */
public interface BillFormalDetailRepositoryCustom {

    List<BillFormalDetail> findUnpaidBills();

    Page<MonthlyBillBean> paginateMonthlyBill(AccountPeriod accountPeriod, int curPage, SearchFilter search);

    Page<MonthlyBillBean> paginateBillByCustomerName(String customerName, int curPage, SearchFilter search);

    Page<BillAge> listAllByPageForBillAge(int curPage, SearchFilter search);


    List<BillFormalDetail> findFirstInstallmentBillOfContract(String contractId);

    /**
     * 该函数用于查询出所有的正式账单明细，
     *
     */
    List<FormalBillDetailBean> findBillDetail(String customerId, AccountPeriod accountPeriod, SearchFilter search);

    Page<FormalBillDetailBean> paginateBillDetail(String customerName, AccountPeriod accountPeriod, int curPage, SearchFilter searchFilter);

    List<BillFormalDetail> listUnpaidBills(String customerId);

    List<BillFormalDetail> listUnpaidBills(String customerId, AccountPeriod accountPeriod);

    List<BillFormalDetail> listBillsByAccountType(String customerName);

    List<SapBillItem> listSapBillReport(AccountPeriod accountPeriod);

    List<SapBillItem> listSapPrepaidBillReport(AccountPeriod accountPeriod);

    List<SapBillItem> listSapPrepaidBillReport(AccountPeriod accountPeriod, List<String> customerCodes);

    List<SapBillItem> listSapPostpaidBillReport(AccountPeriod accountPeriod);

    List<SapBillItem> listSapPostpaidBillReport(AccountPeriod accountPeriod, List<String> customerCodes);

    List<AgentRebateBean> listRebateBillsOfAgent(String agentId, AccountPeriod accountPeriod);

    List<SapBillItem> listResellerVoipBills(AccountPeriod accountPeriod, String[] resellerCodes);

    List<SapBillItem> listResellerPstnBills(AccountPeriod accountPeriod, String[] resellerCodes);

    List<PersonalWebExBill> listPersonWebExBill(String email);

    List<FormalBillDetailBean> listResellerVoipBills(String customerNamePattern, AccountPeriod accountPeriod);
}
