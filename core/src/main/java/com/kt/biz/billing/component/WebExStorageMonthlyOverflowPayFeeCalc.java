package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.WebExStorageMonthlyOverflowPay;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.FeeType;
import com.kt.service.EdrService;
import com.kt.sys.SpringContextHolder;
import com.kt.util.MathUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega Zhou on 2017/4/11.
 */
public class WebExStorageMonthlyOverflowPayFeeCalc extends FeeCalculator<WebExStorageMonthlyOverflowPay> {
    public WebExStorageMonthlyOverflowPayFeeCalc(OrderBean order, WebExStorageMonthlyOverflowPay chargeScheme) {
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
        EdrService edrService = SpringContextHolder.getBean(EdrService.class);
        try {
            int maxStorage = edrService.getMaxStorage(order.getCustomerId(), chargeScheme.getSiteName(), accountPeriod.toInt());
            int overflow = maxStorage - chargeScheme.getStorageSize();
            int units = overflow % 5 == 0 ? overflow / 5 : overflow / 5 + 1;
            if (units > 0) {
                BigDecimal amount = MathUtil.scale(new BigDecimal(chargeScheme.getOverflowUnitPrice() * units));
                return Collections.singletonList(buildBillItem(accountPeriod, FeeType.WEBEX_OVERFLOW_FEE, amount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


    @Override
    protected BigDecimal monthlyDue() {
        BigDecimal unitPrice = new BigDecimal(chargeScheme.getUnitPrice());
        return unitPrice.multiply(new BigDecimal(chargeScheme.getStorageSize()));
    }
}
