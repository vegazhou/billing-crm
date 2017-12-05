package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.CcCallCenterPstnMonthlyPackageCharge;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.FeeType;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega on 2017/10/11.
 */
public class CcCallCenterPstnMonthlyPackageChargeFeeCalc extends FeeCalculator<CcCallCenterPstnMonthlyPackageCharge> {
    public CcCallCenterPstnMonthlyPackageChargeFeeCalc(OrderBean order, CcCallCenterPstnMonthlyPackageCharge chargeScheme) {
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
            return Collections.singletonList(buildBillItem(accountPeriod, FeeType.CC_FEE, monthlyDue()));
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
        return new BigDecimal(chargeScheme.getPricePerMonth()).multiply(BigDecimal.valueOf(chargeScheme.getAmount()));
    }
}
