package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.WebExStorageMonthlyPay;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.FeeType;
import com.kt.util.MathUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
public class WebExStorageMonthlyPayFeeCalc extends FeeCalculator<WebExStorageMonthlyPay> {
    public WebExStorageMonthlyPayFeeCalc(OrderBean order, WebExStorageMonthlyPay chargeScheme) {
        super(order, chargeScheme);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        int payMonths = getPeriodicUnitsInPeriodConsideringFirstInstallment(accountPeriod);
        float amount = payMonths * chargeScheme.getStorageSize() * chargeScheme.getUnitPrice();
        amount = MathUtil.scale(amount);
        return Collections.singletonList(buildBillItem(accountPeriod, FeeType.WEBEX_STORAGE_FEE, new BigDecimal(amount)));
    }


    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        //存储产品不发生此类费用
        return null;
    }


    @Override
    protected BigDecimal monthlyDue() {
        BigDecimal unitPrice = new BigDecimal(chargeScheme.getUnitPrice());
        return unitPrice.multiply(new BigDecimal(chargeScheme.getStorageSize()));
    }
}
