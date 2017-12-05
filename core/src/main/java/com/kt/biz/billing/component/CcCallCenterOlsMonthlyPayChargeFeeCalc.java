package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.CcCallCenterOlsMonthlyPayCharge;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.FeeType;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega on 2017/10/12.
 */
public class CcCallCenterOlsMonthlyPayChargeFeeCalc extends FeeCalculator<CcCallCenterOlsMonthlyPayCharge> {

    public CcCallCenterOlsMonthlyPayChargeFeeCalc(OrderBean order, CcCallCenterOlsMonthlyPayCharge chargeScheme) {
        super(order, chargeScheme);
    }


    private boolean shouldChargeInAccountPeriod(AccountPeriod accountPeriod) {
        return (order.getEndDate().after(accountPeriod.beginOfThisPeriod())) &&
                (order.getStartDate().before(accountPeriod.endOfThisPeriod())) &&
                (!DateUtil.isSameMonth(order.getStartDate(), accountPeriod.beginOfThisPeriod()));
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        if (shouldChargeInAccountPeriod(accountPeriod)) {
            float amount = chargeScheme.getHosts() * chargeScheme.getUnitPrice();
            amount = MathUtil.scale(amount);
            return Collections.singletonList(buildBillItem(accountPeriod, FeeType.CC_FEE, new BigDecimal(amount)));
        } else {
            return null;
        }
    }

    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        return null;
    }

    @Override
    protected BigDecimal monthlyDue() {
        return new BigDecimal(chargeScheme.getUnitPrice()).multiply(new BigDecimal(chargeScheme.getHosts()));
    }
}
