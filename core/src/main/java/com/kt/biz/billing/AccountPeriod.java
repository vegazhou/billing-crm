package com.kt.biz.billing;

import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
public class AccountPeriod {

    private Date date;

    private SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
    private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");

    public AccountPeriod(int year, int month) {
        date = new Date(year - 1900, month - 1, 1);
    }

    public AccountPeriod(String period) throws ParseException {
        date = format.parse(period);
    }

    public AccountPeriod(Date date) {
        this.date = DateUtils.truncate(date, Calendar.MONTH);
    }



    @Override
    public String toString() {
        return format.format(date);
    }

    //yyyy-MM
    public String toFormat2() {
        return format2.format(date);
    }

    public Date getDate() {
        return date;
    }

    public int toInt() {
        return Integer.valueOf(toString());
    }

    public AccountPeriod previousPeriod() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return new AccountPeriod(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1 );
    }

    public AccountPeriod nextPeriod() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return new AccountPeriod(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
    }

    public Date endOfThisPeriod() {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, lastDay);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    public Date beginOfThisPeriod() {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
}
