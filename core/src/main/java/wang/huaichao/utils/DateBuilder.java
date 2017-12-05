package wang.huaichao.utils;

import wang.huaichao.Global;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 8/17/2016.
 */
public class DateBuilder {
    private int year;
    private int month;
    private int date;
    private int hour;
    private int minute;
    private int second;
    private int millisecond;

    private Calendar cal;

    public DateBuilder() {
        cal = Calendar.getInstance();
    }

    public DateBuilder(Date date) {
        cal = Calendar.getInstance();
        cal.setTime(date);
    }

    // 2016-08-17 16:37:12.123 ==> 2016-08-01 00:00:00.000
    public DateBuilder beginOfMonth() {
        cal.set(Calendar.DATE, 1);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return this;
    }

    public DateBuilder endOfMonth() {
        nextMonth();
        cal.set(Calendar.MILLISECOND, -1);
        return this;
    }

    public DateBuilder prevMonth() {
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        return this;
    }

    public DateBuilder nextMonth() {
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        return this;
    }

    public DateBuilder setDate(int dayOfMonth) {
        cal.set(Calendar.DATE, dayOfMonth);
        return this;
    }

    public Date build() {
        return cal.getTime();
    }

    public static void main(String[] args) {
        DateBuilder dateBuilder = new DateBuilder().beginOfMonth();
        Date build = dateBuilder.build();

        System.out.println(Global.DB_DATETIME_FMT.format(build));

        dateBuilder.prevMonth();
        build = dateBuilder.build();

        System.out.println(Global.DB_DATETIME_FMT.format(build));
    }
}
