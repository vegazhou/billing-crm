package com.kt.repo.mysql.billing;

import com.kt.biz.billing.AccountPeriod;
import com.kt.entity.mysql.billing.PstnMonthlyPackage;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/13.
 */
public interface PstnMonthlyPackageRepositoryCustom {

    /**
     * 查找某个PSTN标准计费订单在某个计费周期中
     *
     * @param pstnStandardOrderId
     * @param accountPeriod
     * @return
     */
    List<PstnMonthlyPackage> findWorkingPackagesInAccountPeriod(String pstnStandardOrderId, AccountPeriod accountPeriod);
}
