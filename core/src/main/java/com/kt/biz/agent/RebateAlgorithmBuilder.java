package com.kt.biz.agent;

import com.kt.biz.bean.AgentRebateBean;
import com.kt.biz.types.FeeType;
import com.kt.exception.WafException;
import com.kt.util.DateUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Vega on 2017/8/9.
 */
public class RebateAlgorithmBuilder {

    public static RebateAlgorithm build(AgentRebateBean bean) throws WafException, ParseException {
        FeeType feeType = FeeType.valueOf(bean.getFeeType());
        switch (feeType) {
            case WEBEX_FIRST_INSTALLMENT:
                if (isBefore20170710(bean)) {
                    return new RebateAlgorithmWebExFirstInstallmentImpl1(bean);
                } else {
                    return new RebateAlgorithmWebExFirstInstallmentImpl2(bean);
                }
            case WEBEX_CONFERENCE_FEE:
                if (isBefore20170710(bean)) {
                    return new RebateAlgorithmWebExPeriodicImpl1(bean);
                } else {
                    return new RebateAlgorithmWebExPeriodicImpl2(bean);
                }
            case WEBEX_STORAGE_FEE:
                if (isBefore20170710(bean)) {
                    return new RebateAlgorithmWebExPeriodicImpl1(bean);
                } else {
                    return new RebateAlgorithmWebExPeriodicImpl2(bean);
                }
            case WEBEX_CMR_FEE:
                if (isBefore20170710(bean)) {
                    return new RebateAlgorithmWebExPeriodicImpl1(bean);
                } else {
                    return new RebateAlgorithmWebExPeriodicImpl2(bean);
                }
            case WEBEX_PSTN_FEE:
                if (isBefore20170710(bean)) {
                    return new RebateAlgorithmPstnImpl1(bean);
                } else {
                    return new RebateAlgorithmPstnImpl2(bean);
                }
            case WEBEX_OVERFLOW_FEE:
                return null;
            case WEBEX_EC_DEPOSIT:
                return null;
            case WEBEX_POSTPAID_CONFERENCE_FEE:
                return null;
            default:
                return null;
        }
    }

    private static boolean isBefore20170710(AgentRebateBean bean) throws ParseException {
        Date  d = DateUtil.toDate(bean.getEffectiveStartDate());
        Date _20170710 = DateUtil.toDate("2017-07-10 00:00:00");
        return d.before(_20170710);
    }
}
