package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.PSTNMonthlyPacketCharge;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.FeeType;
import com.kt.util.MathUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
public class PSTNMonthlyPacketChargeFeeCalc extends FeeCalculator<PSTNMonthlyPacketCharge> {

    public PSTNMonthlyPacketChargeFeeCalc(OrderBean order, PSTNMonthlyPacketCharge chargeScheme) {
        super(order, chargeScheme);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        int payMonths = getPeriodicUnitsInPeriodConsideringFirstInstallment(accountPeriod);
        float amount = payMonths * chargeScheme.getPricePerMonth();
        amount = MathUtil.scale(amount);
        return Collections.singletonList(buildBillItem(accountPeriod, FeeType.WEBEX_PSTN_FEE, new BigDecimal(amount)));
    }

    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        return null;
    }

    @Override
    protected BigDecimal monthlyDue() {
        return new BigDecimal(chargeScheme.getPricePerMonth());
    }
}
