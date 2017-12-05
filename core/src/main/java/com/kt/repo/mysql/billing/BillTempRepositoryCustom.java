package com.kt.repo.mysql.billing;
import com.kt.biz.bean.TempBillDetailBean;
import com.kt.biz.billing.AccountPeriod;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.BillTemp;
import com.kt.service.SearchFilter;

import java.util.List;


/**
 * Created by Garfield Chen on 2016/5/5.
 */
public interface BillTempRepositoryCustom {

   
    Page<BillTemp> listAllByPage(int curPage, SearchFilter search);
    
    Page<BillTemp> listAllByPageForConfirm(int curPage, SearchFilter search);

    List<TempBillDetailBean> findTempBillDetail(String customerId, AccountPeriod accountPeriod,  SearchFilter search);

    List<TempBillDetailBean> findPeriodicBillGroupByCustomer(AccountPeriod accountPeriod);

    List<TempBillDetailBean> findPeriodicBillByCustomer(AccountPeriod accountPeriod, String customerId);
}
