package com.kt.service.telerate;

import com.kt.entity.mysql.crm.Rate;
import com.kt.repo.mysql.batch.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/9/6.
 */
@Service
public class TeleRateImportService {

    @Autowired
    RateRepository rateRepository;

    public List<String> listRatePids() {
        return rateRepository.listAllPids();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void importRate(String pid, List<Rate> rates) {
        rateRepository.deleteByPid(pid);
        for (Rate rate : rates) {
            rate.setPid(pid);
            rateRepository.save(rate);
        }
    }
}
