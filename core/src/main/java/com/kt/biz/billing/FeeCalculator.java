package com.kt.biz.billing;

import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.model.util.OrderDurationUtil;
import com.kt.biz.types.FeeType;
import com.kt.entity.mysql.billing.BillFormalDetail;
import com.kt.exception.FormalBillNotFoundException;
import com.kt.service.billing.BillService;
import com.kt.sys.SpringContextHolder;
import com.kt.util.DateUtil;


import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
public abstract class FeeCalculator<T extends AbstractChargeScheme> {

    protected T chargeScheme;

    protected OrderBean order;

    public FeeCalculator(OrderBean order, T chargeScheme) {
        assert order != null;

        this.chargeScheme = chargeScheme;
        this.order = order;
    }

    /**
     * 计算出指定计费周期的预付费用
     *
     * @param accountPeriod  计费周期
     * @return                  客户账单条目
     */
    public abstract List<BillItem> calculatePrepaidDue(AccountPeriod accountPeriod);

    /**
     * 计算指定计费周期中发生的后付款费用
     * @param accountPeriod  计费周期
     * @return                  客户账单条目
     */
    public abstract List<BillItem> calculatePostpaidDue(AccountPeriod accountPeriod);

    /**
     *计算订单变更引起的退款
     *
     * @param originalOrder  原订单
     * @param terminatedOn   停机日期
     * @return                  返回退款项
     */
    public BillItem calculateRefund(OrderBean originalOrder, Date terminatedOn) {
        //CC订单不涉及任何退款
        if (originalOrder.isCallCenterOrder()) {
            return null;
        }
        FeeCalculator origCalc = FeeCalculatorManager.getFeeCalculator(originalOrder);
        FeeCalculator calc = FeeCalculatorManager.getFeeCalculator(order);
        //计算出原订单到中止日期，上一个付费时间点已经支付的月数
        int alreadyPaidMonths = origCalc.calcRecentPaidMonthsBefore(terminatedOn);
        //计算出原订单到中止日期，从上一个付费时间点开始理应支付的月数
        int shouldPayMonths = this.calcRecentPaidMonthsBefore(terminatedOn);
        Date alreadyPaidPeriod = calc.getRecentPayAccountPeriodBefore(terminatedOn);
        BigDecimal shouldPay = monthlyDue().multiply(new BigDecimal(shouldPayMonths));
        BigDecimal gap = monthlyDue().multiply(new BigDecimal(alreadyPaidMonths - shouldPayMonths));
        if (gap.compareTo(new BigDecimal(0)) > 0) {
            if (DateUtil.isSameMonth(alreadyPaidPeriod, order.getStartDate())) {
                Date firstInstallmentCountInDate = originalOrder.getPlacedDate() != null ? originalOrder.getPlacedDate() : originalOrder.getStartDate();
                BillService billService = SpringContextHolder.getBean(BillService.class);
                FeeType firstInstallmentFeeType = chargeScheme.getType().getFirstInstallmentFeeType();
                try {
                    BillFormalDetail firstInstallmentBill = billService.findFormalBill(order.getCustomerId(), order.getOriginalOrderId(), new AccountPeriod(firstInstallmentCountInDate), firstInstallmentFeeType);
                    return buildBillItem(new AccountPeriod(firstInstallmentCountInDate), originalOrder.getId(), firstInstallmentFeeType, shouldPay.subtract(new BigDecimal(firstInstallmentBill.getAmount())));
                } catch (FormalBillNotFoundException e) {
                    return buildBillItem(new AccountPeriod(firstInstallmentCountInDate), originalOrder.getId(), firstInstallmentFeeType, gap.multiply(new BigDecimal(-1)));
                }

            } else {
                FeeType periodicFeeType = chargeScheme.getType().getPeriodicFeeType();
                return buildBillItem(new AccountPeriod(alreadyPaidPeriod), periodicFeeType, gap.multiply(new BigDecimal(-1)));
            }
        } else {
            return null;
        }
    }

    protected abstract BigDecimal monthlyDue();

    private BillItem buildBillItem(AccountPeriod accountPeriod, String orderId, FeeType feeType, BigDecimal amount) {
        BillItem item = new BillItem();
        item.setAccountPeriod(accountPeriod);
        item.setOrderId(orderId);
        item.setFeeType(feeType);
        item.setAmount(amount);
        return item;
    }



    protected BillItem buildBillItem(AccountPeriod accountPeriod, FeeType feeType, BigDecimal amount) {
        return buildBillItem(accountPeriod, order.getId(), feeType, amount);
    }


    protected boolean isExpiredOn(Date date) {
        return !date.before(order.getEndDate()) && !DateUtil.isSameMonth(order.getEndDate(), date);
    }

    protected int getPaymentInterval() {
        return order.getPayInterval().getInterval();
    }


    protected boolean shallPayIn(Date accountingPeriod) {
        if (accountingPeriod == null) {
            return false;
        }
        List<Date> payDays = getPeriodicPaymentMonths();
        for (Date payDay : payDays) {
            if (DateUtil.isSameMonth(accountingPeriod, payDay)) {
                return true;
            }
        }
        return false;
    }


    /**
     * @return the sorted months that need to pay the periodic fee
     */
    public List<Date> getPeriodicPaymentMonths() {
//        assert startDate != null;
//        assert endDate != null;
//        assert startDate.before(endDate);

        List<Date> results = new ArrayList<>();
        Date nextPayPoint = order.getStartDate();
        results.add(DateUtil.beginOfMonth(nextPayPoint));

        if (order.getPayInterval().isOneTime()) {
            return results;
        }
        do {
            nextPayPoint = DateUtil.xMonthLater(nextPayPoint, order.getPayInterval().getInterval());
            if (!nextPayPoint.before(order.getEndDate())) {
                break;
            } else {
                if (isStartedInFirstHalfMonth()) {
                    results.add(DateUtil.beginOfMonth(nextPayPoint));
                } else {
                    results.add(DateUtil.beginOfMonth(DateUtil.oneMonthLater(nextPayPoint)));
                }
            }
        } while (true);
        Collections.sort(results);
        return results;
    }

    /**
     * 计算出当前订单，某一个日期归属的付费账期，若不属于任一付费账期，返回空
     * @param date  日期
     * @return       该日期所属的付费账期
     */
    public Date getRecentPayAccountPeriodBefore(Date date) {
        List<Date> payDays = getPeriodicPaymentMonths();
        Date last = null;
        Date current = null;
        if (payDays.size() == 0) {
            return null;
        }
        if (date.before(payDays.get(0))) {
            return null;
        }
        for (Date payDay : payDays) {
            current = payDay;
            if (current.after(date)) {
                return last;
            } else if (current.before(date)) {
                last = payDay;
            } else {
                return current;
            }
        }
        return current;
    }


    /**
     * 计算在指定账期中，某笔订单的周期性费用所应覆盖的月份数
     * @param accountPeriod 账期
     * @return                 周期性费用覆盖的月份
     */
    public int getPeriodicUnitsInPeriodConsideringFirstInstallment(AccountPeriod accountPeriod) {
        return getPeriodicUnitsInPeriodConsideringFirstInstallment(accountPeriod.getDate());
    }


    private int getPeriodicUnitsInPeriodConsideringFirstInstallment(Date date) {
        if (shallPayIn(date)) {
            if (isTheFirstMonthOfOrder(date)) {
                //订单的首月不发生周期性费用，该笔费用已在首付款中收取
                return 0;
            }
            if (isTheLastPayDay(date)) {
                //最后一次付费，不一定覆盖整个付费周期，而是覆盖剩余月份
                return getPayMonthsBetweenEndDate(date);
            } else {
                //不是最后一次付费，则一定覆盖整个付费周期
                return getPaymentInterval();
            }
        } else {
            return 0;
        }
    }



    public int getUnitsInPeriod(Date date) {
        int interval = getPaymentInterval();
        if (shallPayIn(date)) {
            if (isTheLastPayDay(date)) {
                //最后一次付费，不一定覆盖整个付费周期，而是覆盖剩余月份
                return getPayMonthsBetweenEndDate(date);
            } else {
                //不是最后一次付费，则一定覆盖整个付费周期
                return interval;
            }
        } else {
            return 0;
        }
    }

    public int calcRecentPaidMonthsBefore(Date date) {
        Date d = getRecentPayAccountPeriodBefore(date);
        return getUnitsInPeriod(d);
    }


    protected boolean isStartedInFirstHalfMonth() {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(order.getStartDate());

        return startDate.get(Calendar.DAY_OF_MONTH) <= 15;
    }


    protected boolean isTheFirstMonthOfOrder(Date date) {
        return DateUtil.isSameMonth(order.getStartDate(), date);
    }


    protected boolean isTheLastMonthOfOrder(Date date) {
        return DateUtil.isSameMonth(order.getEndDate(), date);
    }

    /**
     * 判断某个日期是不是处于订单的最后付款账期中
     * @param date
     * @return
     */
    protected boolean isTheLastPayDay(Date date) {
        List<Date> dates = getPeriodicPaymentMonths();
        // the date is the last pay day of the order
        return dates.size() > 0 && DateUtil.isSameMonth(dates.get(dates.size() - 1), date);
    }

    protected int getPayMonthsBetweenEndDate(Date date) {
        Date realStart = date;
        if (realStart.before(order.getStartDate())) {
            realStart = order.getStartDate();
        }

        realStart = DateUtil.mergeMonthAndDay(realStart, order.getStartDate());
        if (realStart.after(order.getEndDate())) {
            return 1;
        }

        if (isStartedInFirstHalfMonth()) {
            return OrderDurationUtil.getPaymentMonthsBetween(realStart, order.getEndDate());
//            return DateUtil.getMonthBetween(date, order.getEndDate());
        } else {
            if (!isTheFirstMonthOfOrder(date)) {
                return OrderDurationUtil.getPaymentMonthsBetween(realStart, order.getEndDate()) + 1;
//                return DateUtil.getMonthBetween(date, order.getEndDate()) + 1;
            } else {
                return OrderDurationUtil.getPaymentMonthsBetween(realStart, order.getEndDate());
//                return DateUtil.getMonthBetween(date, order.getEndDate());
            }
        }
    }
}
