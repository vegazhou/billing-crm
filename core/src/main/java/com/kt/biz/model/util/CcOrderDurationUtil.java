package com.kt.biz.model.util;

import com.kt.util.DateUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vega on 2017/11/20.
 */
public class CcOrderDurationUtil {

    public static void main(String args[]) throws ParseException {
        Date d1 = DateUtil.toShortDate("2017-10-31");
        Date d2 = DateUtil.toShortDate("2017-11-01");
        System.out.println(getPaymentMonthsBetween(d1, d2));
    }


    public static double getFirstMonthPercentage(Date start) {
        int startDayOfMonth = DateUtil.getDayOfMonth(start) - 1;
        int maxDayOfMonth = DateUtil.getMaxDayOfMonth(start);
        double difference = (double)startDayOfMonth / (double)maxDayOfMonth;
        return 1d - difference;
    }

    /**
     * 计算从start日期开始到end日期之间共有多少个付费月数
     *
     * 首月如果不足月，按比例收取费用；结束日期必须为某月的月末
     *
     * @param start
     * @param end
     * @return
     */
    public static double getPaymentMonthsBetween(Date start, Date end) {
        Date d1 = DateUtil.beginOfMonth(start);
        Date d2 = DateUtil.beginOfMonth(end);
        if (!DateUtil.isFirstDayOfMonth(end)) {
            d2 = DateUtil.oneMonthLater(d2);
        }

        double fullMonths = getMonthBetween(d1, d2);

        if (DateUtil.isFirstDayOfMonth(start)) {
            return fullMonths;
        } else {
            int startDayOfMonth = DateUtil.getDayOfMonth(start) - 1;
            int maxDayOfMonth = DateUtil.getMaxDayOfMonth(start);
            double difference = (double)startDayOfMonth / (double)maxDayOfMonth;
            return fullMonths - difference;
        }

    }


    private static int getMonthBetween(Date d1, Date d2) {
        Calendar earlier = Calendar.getInstance();
        Calendar later = Calendar.getInstance();
        if (d1.before(d2)) {
            earlier.setTime(d1);
            later.setTime(d2);
            return (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR)) * 12 + later.get(Calendar.MONTH)
                    - earlier.get(Calendar.MONTH);
        } else {
            return 0;
        }
    }
}
