package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.WebExCmrMonthlyPay;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.FeeType;
import com.kt.util.MathUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/10/20.
 */
public class WebExCmrMonthlyPayFeeCalc extends FeeCalculator<WebExCmrMonthlyPay> {

    public WebExCmrMonthlyPayFeeCalc(OrderBean order, WebExCmrMonthlyPay chargeScheme) {
        super(order, chargeScheme);
    }


    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        int payMonths = getPeriodicUnitsInPeriodConsideringFirstInstallment(accountPeriod);
        return Collections.singletonList(buildBillItem(accountPeriod, FeeType.WEBEX_CMR_FEE, monthlyDue().multiply(BigDecimal.valueOf(payMonths))));
    }


    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        //TODO: CMR按月租产品需要计算溢出
        return null;
    }

    @Override
    protected BigDecimal monthlyDue() {
        return BigDecimal.valueOf(chargeScheme.getUnitPrice()).multiply(BigDecimal.valueOf(chargeScheme.getPorts()));
    }
}
