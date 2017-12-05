package com.kt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vega Zhou on 2015/10/21.
 */
public class DateUtil {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String SHORT_FORMAT = "yyyy-MM-dd";
    
    private static final String SHORT_INVOICE_FORMAT = "yyyy/MM/dd";
    
    private static final String SHORT_INVOICE_NAME = "yyyyMMdd";
    
    private static final String SHORT_FORMAT_NO_DAY = "yyyyMM";

    public static Date oneDayLater() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date xDaysBefore(int days, Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DATE, -days);
        return calendar.getTime();
    }

    public static Date xDaysLater(int x) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, x);
        return calendar.getTime();
    }

    public static String formatDate(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        return f.format(date);
    }


    public static String formatShortDate(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat f = new SimpleDateFormat(SHORT_FORMAT);
        return f.format(date);
    }
    
    public static String formatShortInvoiceDate(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat f = new SimpleDateFormat(SHORT_INVOICE_FORMAT);
        return f.format(date);
    }
    
    public static String formatInvoiceNameDate(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat f = new SimpleDateFormat(SHORT_INVOICE_NAME);
        return f.format(date);
    }
    
    
    public static String formatEnDate(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat f = new SimpleDateFormat("yyyy/MMM ",  
                Locale.ENGLISH);
        return f.format(date);
    }
    
    
    public static String formatEnDate2(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat f = new SimpleDateFormat("MMM yyyy",  
                Locale.ENGLISH);
        return f.format(date);
    }


    
    public static Date toShortNoDayDate(String dateStringInDefaultFormat) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat(SHORT_FORMAT_NO_DAY);
        return f.parse(dateStringInDefaultFormat);
    }
    
    public static Date toInvoiceDate(String dateStringInDefaultFormat) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat(SHORT_INVOICE_NAME);
        return f.parse(dateStringInDefaultFormat);
    }


    /**
     * 获取系统当前日期，时分秒清零
     */
    public static Date getSystemDate() {
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date yesterday() {
        Date d = new Date();
//        Date d = toDate("2001-01-01 00:00:00");
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DAY_OF_YEAR, -1);
        return c.getTime();
    }


    public static String now() {
        return formatDate(new Date());
    }

    public static Date toDate(String dateStringInDefaultFormat) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        return f.parse(dateStringInDefaultFormat);
    }

    public static Date toShortDate(String dateStringInShortFormat) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat(SHORT_FORMAT);
        return f.parse(dateStringInShortFormat);
    }
    
    public static Date toShortInvoiceDate(String dateStringInDefaultFormat) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat(SHORT_INVOICE_FORMAT);
        return f.parse(dateStringInDefaultFormat);
    }
    

    public static Date pstnDefaultEndDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2000);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date oneMonthLater(Date date) {
        return xMonthLater(date, 1);
    }

    public static Date xMonthLater(Date date, int x) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, x);
        return c.getTime();
    }

    public static Date xMonthBefore(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -month);
        return c.getTime();
    }


    public static int getMonthBetween(Date d1, Date d2) {
        Calendar earlier = Calendar.getInstance();
        Calendar later = Calendar.getInstance();
        if (d1.before(d2)) {
            earlier.setTime(d1);
            later.setTime(d2);
        } else {
            earlier.setTime(d2);
            later.setTime(d1);
        }

        return (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR)) * 12 + later.get(Calendar.MONTH)
                - earlier.get(Calendar.MONTH);
    }

    public static Date beginOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date beginOfToday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


    public static boolean isSameMonth(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
    }


    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH);
    }

    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public static int getMaxDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date getEndOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, getMaxDayOfMonth(date));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }


    /**
     * 计算source日期在对应月份的日期，用以计算订购周期
     * @param source
     * @param target
     * @return
     */
    public static Date getSameDayOfMonth(Date source, Date target) {
        if ((getDayOfMonth(source) > getMaxDayOfMonth(target)) ||
                (getDayOfMonth(source) == getMaxDayOfMonth(source))) {
            return getEndOfMonth(target);
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(source);
            c.set(Calendar.YEAR, getYear(target));
            c.set(Calendar.MONTH, getMonth(target));
            return c.getTime();
        }
    }


    public static boolean isFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH) == 1;
    }

    public static boolean isLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH);
    }


    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    public static Date addOneMonth(Date date){
    	
    	Calendar  g = Calendar.getInstance();  
        g.setTime(date);  
        g.add(Calendar.MONTH, +1);
        Date d2 = g.getTime();
        return d2;
    }
    
    public static Date addTwoMonth(Date date){
    	
    	Calendar  g = Calendar.getInstance();  
        g.setTime(date);  
        g.add(Calendar.MONTH,+2);             
        Date d2 = g.getTime();
        return d2;
    }

    public static Date mergeMonthAndDay(Date monthToBeMerged, Date dayToBeMerged) {
        Calendar  g = Calendar.getInstance();
        g.setTime(monthToBeMerged);

        Calendar  tmp = Calendar.getInstance();
        tmp.setTime(dayToBeMerged);

        int max = g.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfMonth = tmp.get(Calendar.DAY_OF_MONTH);
        if (dayOfMonth > max) {
            dayOfMonth = max;
        }
        g.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return g.getTime();
    }


    public static Date setDayOfMonth(Date date, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return c.getTime();
    }


    public static void main(String args[]) throws ParseException {
        Date d1 = toShortDate("2017-02-05");
        Date d2 = toShortDate("2017-01-31");
        Date d = mergeMonthAndDay(d1 , d2);
        return;
    }

}
