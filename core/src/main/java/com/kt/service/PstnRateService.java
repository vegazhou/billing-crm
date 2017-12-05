package com.kt.service;

import com.kt.biz.bean.PstnRateBean;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.common.SchemeKeys;
import com.kt.biz.types.CommonState;
import com.kt.entity.mysql.crm.ChargeScheme;
import com.kt.entity.mysql.crm.ChargeSchemeAttribute;
import com.kt.entity.mysql.crm.Rate;
import com.kt.exception.OnlyDraftingSchemeAllowedException;
import com.kt.exception.PstnPriceListNotFoundException;
import com.kt.exception.WafException;
import com.kt.repo.mysql.batch.ChargeSchemeAttributeRepository;
import com.kt.repo.mysql.batch.RateRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/28.
 */
@Service
public class PstnRateService {

    @Autowired
    RateRepository rateRepository;
    @Autowired
    ChargeSchemeAttributeRepository chargeSchemeAttributeRepository;
    @Autowired
    ChargeSchemeService chargeSchemeService;



    public List<PstnRateBean> findById(String priceListId) throws PstnPriceListNotFoundException {
        List<PstnRateBean> rates = rateRepository.listDetailedRates(priceListId);
        if (rates == null || rates.size() == 0) {
            throw new PstnPriceListNotFoundException();
        }
        return rates;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePriceList(String pid, List<Rate> rates) throws WafException {
        assertPriceListCanBeUpdated(pid);
        for (Rate rate : rates) {
            Rate entity = rateRepository.findFirstByPidAndCode(pid, rate.getCode());
            entity.setRate(rate.getRate());
            entity.setServiceRate(rate.getServiceRate());
            rateRepository.save(entity);
        }
        rateRepository.flush();
    }


    private void assertPriceListCanBeUpdated(String pid) throws WafException {
        if (StringUtils.isBlank(pid)) {
            throw new PstnPriceListNotFoundException();
        }
        List<ChargeSchemeAttribute> attributes =
                chargeSchemeAttributeRepository.findByNameAndValue(SchemeKeys.PSTN_RATES_ID, pid);
        for (ChargeSchemeAttribute attribute : attributes) {
            String chargeSchemeId = attribute.getEntityId();
            assertChargeSchemeCanBeUpdated(chargeSchemeId);
        }
    }

    private void assertChargeSchemeCanBeUpdated(String id) throws WafException {
        AbstractChargeScheme scheme = chargeSchemeService.findChargeSchemeById(id);
        if (scheme.getState() != CommonState.DRAFT) {
            throw new OnlyDraftingSchemeAllowedException();
        }
    }
}
