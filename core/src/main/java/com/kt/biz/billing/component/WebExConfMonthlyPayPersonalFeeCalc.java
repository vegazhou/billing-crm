package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayPersonal;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.FeeType;
import com.kt.util.MathUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega Zhou on 2017/5/8.
 */
public class WebExConfMonthlyPayPersonalFeeCalc extends FeeCalculator<WebExConfMonthlyPayPersonal> {

    public WebExConfMonthlyPayPersonalFeeCalc(OrderBean order, WebExConfMonthlyPayPersonal chargeScheme) {
        super(order, chargeScheme);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        int payMonths = getPeriodicUnitsInPeriodConsideringFirstInstallment(accountPeriod);
        float amount = payMonths * chargeScheme.getUnitPrice();
        amount = MathUtil.scale(amount);
        return Collections.singletonList(buildBillItem(accountPeriod, FeeType.WEBEX_CONFERENCE_FEE, new BigDecimal(amount)));
    }

    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        return Collections.emptyList();
    }

    @Override
    protected BigDecimal monthlyDue() {
        return new BigDecimal(chargeScheme.getUnitPrice());
    }
}
