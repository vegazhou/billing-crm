package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.PSTNPersonalCharge;
import com.kt.biz.model.order.OrderBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2017/5/8.
 */
public class PSTNPersonalChargeFeeCalc extends FeeCalculator<PSTNPersonalCharge> {

    public PSTNPersonalChargeFeeCalc(OrderBean order, PSTNPersonalCharge chargeScheme) {
        super(order, chargeScheme);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        //不产生周期性费用
        return null;
    }

    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        //TODO: 等JEFF的代码
//        BillItem item = buildBillItem(accountPeriod, FeeType.WEBEX_PSTN_FEE, new BigDecimal(RandomUtils.nextInt(1000)));
//        return Collections.singletonList(item);
        return Collections.emptyList();
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

