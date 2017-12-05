package com.kt.biz.model.util;

import com.kt.util.DateUtil;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vega Zhou on 2016/4/15.
 */
public class OrderDurationUtil {

    public static void main(String args[]) throws ParseException {

    }

    /**
     * 本函数根据订单的起始日期，和购买的月数，计算出订单的结束日期
     *
     * @param startDate  订单的起始日期
     * @param months     订购的月数
     * @return            订单的结束日期
     */
    public static Date getEndDate(Date startDate, int months) {
        Date endDate = DateUtils.addMonths(DateUtils.truncate(startDate, Calendar.DATE), months);
        if (isLastDayOfMonth(startDate)) {
            endDate = getLastDayOfMonth(endDate);
        }
        return endDate;
    }


    /**
     * 本函数用来计算某个订购起始日期到结束日期，一共跨越了多少个计费月
     *
     * @param start 订购的开始日期
     * @param end    订购的结束日期
     * @return       跨越的计费月
     */
    public static int getPaymentMonthsBetween(Date start, Date end) {
        int absMonth = DateUtil.getMonthBetween(start, end);
        if (isLastDayOfMonth(start)) {
            return absMonth;
        } else {
            Date d1 = DateUtil.getSameDayOfMonth(start, end);
            if (DateUtils.isSameDay(d1, end)) {
                return absMonth;
            } else {
                if (d1.after(end)) {
                    return absMonth;
                } else {
                    return absMonth + 1;
                }
            }
        }
    }



    public static int getPaymentMonthsBetween2(Date start, Date end) {
        return DateUtil.getMonthBetween(start, end);
    }

    private static boolean isLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH);
    }


    private static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }
}
