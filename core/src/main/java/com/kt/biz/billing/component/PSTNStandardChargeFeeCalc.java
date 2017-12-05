package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.PSTNStandardCharge;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.FeeType;
import com.kt.sys.SpringContextHolder;
import wang.huaichao.data.service.BillingService;
import wang.huaichao.exception.BillingException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
public class PSTNStandardChargeFeeCalc extends FeeCalculator<PSTNStandardCharge> {

    private transient BillingService billingService;

    public PSTNStandardChargeFeeCalc(OrderBean order, PSTNStandardCharge chargeScheme) {
        super(order, chargeScheme);
        billingService = SpringContextHolder.getBean(BillingService.class);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        //电话语音产品不产生周期性费用
        return null;
    }


    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        BillItem item = null;
        try {
            float amount = billingService.getPstnFeeByBillingPeriodAndOrderId(order.getId(), accountPeriod.toInt());
            item = buildBillItem(accountPeriod, FeeType.WEBEX_PSTN_FEE, new BigDecimal(amount));
        } catch (BillingException e) {
            item = buildBillItem(accountPeriod, FeeType.WEBEX_PSTN_FEE, new BigDecimal(0));
        }

        return Collections.singletonList(item);
    }

    @Override
    public BillItem calculateRefund(OrderBean order, Date terminatedOn) {
        //语音标准计费产品提前中止不产生金额减免
        return null;
    }

    @Override
    protected BigDecimal monthlyDue() {
        return new BigDecimal(0);
    }
}
