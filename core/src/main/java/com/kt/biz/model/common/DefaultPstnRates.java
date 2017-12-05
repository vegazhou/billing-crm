package com.kt.biz.model.common;

import com.kt.entity.mysql.crm.Rate;
import com.kt.repo.mysql.batch.RateRepository;
import com.kt.sys.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/9.
 */
@Service
public class DefaultPstnRates {
    private static List<Rate> defaults;

    private static final String DEFAULT_RATE_ID = "0";



    public static List<Rate> getDefaultPstnRates() {
        if (defaults == null) {
            defaults = SpringContextHolder.getBean(RateRepository.class).findByPid(DEFAULT_RATE_ID);
        }

        List<Rate> results = new ArrayList<>();
        for (Rate rate : defaults) {
            results.add(clone(rate));
        }
        return results;
    }

    public static synchronized void flush() {
        defaults = null;
    }

    private static Rate clone(Rate rate) {
        Rate cloned = new Rate();
        cloned.setCode(rate.getCode());
        cloned.setDisplayName(rate.getDisplayName());
        cloned.setRate(rate.getRate());
        cloned.setServiceRate(rate.getServiceRate());
        cloned.setCountry(rate.getCountry());
        return cloned;
    }
}
