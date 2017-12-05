package com.kt.biz.billing.component;

import com.kt.biz.bean.PortsUsageBean;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByTotalAttendees;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.types.FeeType;
import com.kt.service.EdrService;
import com.kt.sys.SpringContextHolder;
import com.kt.util.MathUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega Zhou on 2017/4/25.
 */
public class WebExConfMonthlyPayByTotalAttendeesFeeCalc extends FeeCalculator<WebExConfMonthlyPayByTotalAttendees> {
    public WebExConfMonthlyPayByTotalAttendeesFeeCalc(OrderBean order, WebExConfMonthlyPayByTotalAttendees chargeScheme) {
        super(order, chargeScheme);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        int payMonths = getPeriodicUnitsInPeriodConsideringFirstInstallment(accountPeriod);
        float amount = payMonths * chargeScheme.getPorts() * chargeScheme.getUnitPrice();
        amount = MathUtil.scale(amount);
        return Collections.singletonList(buildBillItem(accountPeriod, FeeType.WEBEX_CONFERENCE_FEE, new BigDecimal(amount)));
    }

    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        EdrService edrService = SpringContextHolder.getBean(EdrService.class);
        try {
            PortsUsageBean portsUsageBean = edrService.getTotalAttendeesFee(order.getStartDate(), order.getEndDate(), chargeScheme, order.getBiz().getType(), accountPeriod, true);
            BigDecimal amount = portsUsageBean.getPortsFee();
            return Collections.singletonList(buildBillItem(accountPeriod, FeeType.WEBEX_POSTPAID_CONFERENCE_FEE, amount));
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    protected BigDecimal monthlyDue() {
        BigDecimal unitPrice = new BigDecimal(chargeScheme.getUnitPrice());
        return unitPrice.multiply(new BigDecimal(chargeScheme.getPorts()));
    }
}
