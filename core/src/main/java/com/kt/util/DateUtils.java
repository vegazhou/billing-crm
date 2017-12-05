package com.kt.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/10.
 */
public final class DateUtils extends org.apache.commons.lang.time.DateUtils{
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class.getName());
    private static final String DEFAULT_TIME_PATTER = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_SHORT_DATE_PATTER = "yyyy-MM-dd";
    /**
     * default format YYYY-MM-dd HH:mm:ss
     * @param source
     * @return
     */
    public static Date parseDate(String source) {
        String format = DEFAULT_SHORT_DATE_PATTER;
        if(StringUtils.isNotBlank(source) && source.trim().length() > 10){
            format = DEFAULT_TIME_PATTER;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date result = null;
        try {
            if (source != null) {
                result = sdf.parse(source);
            }
        } catch (ParseException ex) {
            logger.error("Date format must be " + format);
        }
        return result;
    }

    public static String format2DefaultFullTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(DEFAULT_TIME_PATTER);
        return format.format(date);
    }

    public static String format(String pattern, Date date) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        return format.format(date);
    }

    public static Date parseFullDate(String str) {
        if (str == null){
            return null;
        }
        try {
            return parseDate(str.toString(), new String[]{DEFAULT_TIME_PATTER});
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, DEFAULT_TIME_PATTER);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, DEFAULT_SHORT_DATE_PATTER);
        }
        return formatDate;
    }

    public static String getDateNoSeparator() {
        Date date = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMdd");
        return outFormat.format(date);
    }

    /**
     * return today's start second timestamp
     * @return
     */
    public static Date getTodayStartTime() {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * return today's last second timestamp
     * @return
     */
    public static Date getTodayEndTime() {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date[] getStartEndDateByBillPeriod(int billPeriod){
        if(billPeriod >= 1000000 || billPeriod <=200000){
            throw new RuntimeException("The bill period is invalid!");
        }
        int iYear = billPeriod/100;
        int iMonth = billPeriod%100;

        Calendar cal = Calendar.getInstance();
        cal.set(iYear, iMonth-1, 1, 0,0,0);

        Date startDate = cal.getTime();
        cal.set(Calendar.MONTH, iMonth);
        Date endDate = cal.getTime();

        return new Date[]{startDate, endDate};
    }
}
