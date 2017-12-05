package com.kt.biz.agent;

import com.kt.biz.bean.AgentRebateBean;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.model.charge.impl.PSTNStandardCharge;
import com.kt.entity.mysql.crm.Rate;
import com.kt.exception.WafException;
import com.kt.sys.SpringContextHolder;
import wang.huaichao.data.entity.crm.MeetingRecord;
import wang.huaichao.data.repo.MeetingRecordRepository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Vega on 2017/8/9.
 */
public class RebateAlgorithmPstnImpl2 extends AbstractRebateAlgorithm {

    public RebateAlgorithmPstnImpl2(AgentRebateBean bean) throws WafException {
        super(bean);
        init();
    }

    private boolean computed = false;
    private Rate domesticRate = null;
    private Rate domesticAgentRate = null;
    private BigDecimal agentPrice = null;

    private void init() {

    }

    @Override
    public BigDecimal computeUnitPrice() throws ParseException {
        computeIfNecessary();
        return new BigDecimal(domesticRate.getRate() + domesticRate.getServiceRate());
    }

    @Override
    public BigDecimal computeAgentUnitPrice() throws ParseException {
        computeIfNecessary();
        return new BigDecimal(domesticAgentRate.getRate() + domesticAgentRate.getServiceRate());
    }

    @Override
    public BigDecimal computeAgentAmount() throws WafException, ParseException {
        computeIfNecessary();
        return agentPrice;
    }

    @Override
    public BigDecimal computeRegisterSettleAmount() throws WafException, ParseException {
        computeIfNecessary();
        return agentPrice;
    }

    @Override
    public BigDecimal computeNonRegisterSettleAmount() throws WafException, ParseException {
        computeIfNecessary();
        return agentPrice;
    }


    private void computeIfNecessary() throws ParseException {
        if (computed)
            return;
        domesticRate = findSpecifiedRate(((PSTNStandardCharge) orderBean.getChargeScheme()).getUnitPriceList(), "6");
        domesticAgentRate = findSpecifiedRate(((PSTNStandardCharge) agentChargeScheme).getUnitPriceList(), "6");
        MeetingRecordRepository meetingRecordRepository = SpringContextHolder.getBean(MeetingRecordRepository.class);
        List<MeetingRecord> pstnRecords = meetingRecordRepository.findByBillingPeriodAndOrderId(new AccountPeriod(bean.getAccountPeriod()).previousPeriod().toInt(), orderBean.getId());
        int domesticMinutes = 0;
        BigDecimal internationalFee = new BigDecimal(0);
        for (MeetingRecord pstnRecord : pstnRecords) {
            if (pstnRecord.isInternational()) {
                internationalFee = internationalFee.add(pstnRecord.getCost());
            } else {
                domesticMinutes += pstnRecord.getDuration();
            }
        }
        agentPrice = new BigDecimal(domesticMinutes).multiply(new BigDecimal(domesticAgentRate.getRate() + domesticAgentRate.getServiceRate()));
        BigDecimal internationalAgentFee = internationalFee.multiply(new BigDecimal(0.9));

        agentPrice = agentPrice.add(internationalAgentFee);

        computed = true;
    }

    Rate findSpecifiedRate(List<Rate> rates, String code) {
        for (Rate rate : rates) {
            if (rate.getCode().equals(code)) {
                return  rate;
            }
        }
        return null;
    }
}
