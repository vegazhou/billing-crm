package com.kt.biz.billing.component;

import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.billing.FeeCalculatorManager;
import com.kt.biz.model.charge.impl.TelecomCharge;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.util.OrderDurationUtil;
import com.kt.biz.types.FeeType;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/14.
 */
public class TelecomChargeFeeCalc extends FeeCalculator<TelecomCharge> {

    public TelecomChargeFeeCalc(OrderBean order, TelecomCharge chargeScheme) {
        super(order, chargeScheme);
    }

    @Override
    public List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod) {
        return null;
    }

    @Override
    public List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod) {
        int payMonths = getUnitsInPeriod(accountPeriod.getDate());
        float amount = payMonths * chargeScheme.getUnitPrice();
        amount = MathUtil.scale(amount);

        return Collections.singletonList(buildBillItem(accountPeriod, FeeType.WEBEX_CONFERENCE_FEE, new BigDecimal(amount)));
    }

    @Override
    public BillItem calculateRefund(OrderBean originalOrder, Date terminatedOn) {
        //TODO: 会议通产品提前中止，需测试退费
        FeeCalculator origCalc = FeeCalculatorManager.getFeeCalculator(originalOrder);
        FeeCalculator calc = FeeCalculatorManager.getFeeCalculator(order);
        int alreadyPaidMonths = origCalc.calcRecentPaidMonthsBefore(terminatedOn);
        int shouldPayMonths = this.calcRecentPaidMonthsBefore(terminatedOn);
        Date alreadyPaidPeriod = calc.getRecentPayAccountPeriodBefore(terminatedOn);

        BigDecimal gap = monthlyDue().multiply(new BigDecimal(alreadyPaidMonths - shouldPayMonths));
        if (gap.compareTo(new BigDecimal(0)) > 0) {
//            if (DateUtil.isSameMonth(alreadyPaidPeriod, order.getStartDate())) {
//                if (originalOrder.getPlacedDate() != null) {
//                    return buildBillItem(new AccountPeriod(originalOrder.getPlacedDate()), FeeType.WEBEX_FIRST_INSTALLMENT, gap.multiply(new BigDecimal(-1)));
//                } else {
//                    return buildBillItem(new AccountPeriod(originalOrder.getStartDate()), FeeType.WEBEX_FIRST_INSTALLMENT, gap.multiply(new BigDecimal(-1)));
//                }
//            }
            return buildBillItem(new AccountPeriod(alreadyPaidPeriod), com.kt.biz.types.FeeType.WEBEX_CONFERENCE_FEE, gap.multiply(new BigDecimal(-1)));
        }
        return null;
    }

    @Override
    protected BigDecimal monthlyDue() {
        return new BigDecimal(chargeScheme.getUnitPrice());
    }


    public List<Date> getPeriodicPaymentMonths() {
//        assert startDate != null;
//        assert endDate != null;
//        assert startDate.before(endDate);


        List<Date> results = new ArrayList<>();
        Date firstMonthDay = DateUtil.beginOfMonth(order.getStartDate());
        Date lastMonthDay = DateUtil.getEndOfMonth(firstMonthDay);;

        if (order.getPayInterval().isOneTime()) {
            results.add(firstMonthDay);
            return results;
        }

        do {
            if (!isExpiredOn(lastMonthDay)) {
                results.add(firstMonthDay);
            }
            firstMonthDay = DateUtil.xMonthLater(firstMonthDay, order.getPayInterval().getInterval());
            lastMonthDay = DateUtil.getEndOfMonth(firstMonthDay);
        } while (!isExpiredOn(lastMonthDay));
        Collections.sort(results);
        return results;
    }

    @Override
    protected boolean isExpiredOn(Date date) {
        return !date.before(order.getEndDate());
    }

    protected int getPayMonthsBetweenEndDate(Date date) {
        Date realStart = date;
        if (realStart.before(order.getStartDate())) {
            realStart = order.getStartDate();
        }
        return OrderDurationUtil.getPaymentMonthsBetween2(realStart, order.getEndDate());
    }
}
