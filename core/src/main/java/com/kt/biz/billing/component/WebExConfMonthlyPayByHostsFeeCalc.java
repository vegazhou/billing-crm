package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByHosts;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.FeeType;
import com.kt.util.MathUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
public class WebExConfMonthlyPayByHostsFeeCalc extends FeeCalculator<WebExConfMonthlyPayByHosts> {

    public WebExConfMonthlyPayByHostsFeeCalc(OrderBean order, WebExConfMonthlyPayByHosts chargeScheme) {
        super(order, chargeScheme);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        int payMonths = getPeriodicUnitsInPeriodConsideringFirstInstallment(accountPeriod);
        float amount = payMonths * chargeScheme.getHosts() * chargeScheme.getUnitPrice();
        amount = MathUtil.scale(amount);
        return Collections.singletonList(buildBillItem(accountPeriod, FeeType.WEBEX_CONFERENCE_FEE, new BigDecimal(amount)));
    }

    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        //按用户数购买产品不发生此类费用
        return null;
    }

    @Override
    protected BigDecimal monthlyDue() {
        return new BigDecimal(chargeScheme.getUnitPrice()).multiply(new BigDecimal(chargeScheme.getHosts()));
    }
}
