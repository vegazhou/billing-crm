package com.kt.repo.mysql.batch;

import com.kt.biz.bean.PstnRateBean;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/9/6.
 */
public interface RateRepositoryCustom {

    List<String> listAllPids();

    List<PstnRateBean> listDetailedRates(String pid);
}
