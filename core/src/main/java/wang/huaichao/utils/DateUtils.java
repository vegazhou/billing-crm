package wang.huaichao.utils;

import wang.huaichao.Global;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Administrator on 8/17/2016.
 */
public class DateUtils {
    public static Date max(Date... dates) {
        Date max = dates[0];
        for (int i = 1; i < dates.length; i++) {
            if (max.getTime() < dates[i].getTime()) {
                max = dates[i];
            }
        }
        return max;
    }

    public static Date min(Date... dates) {
        Date min = dates[0];
        for (int i = 1; i < dates.length; i++) {
            if (min.getTime() > dates[i].getTime()) {
                min = dates[i];
            }
        }
        return min;
    }

    public static DateRange dateRange(int billingPeriod) {
        DateBuilder db;
        try {
            db = new DateBuilder(Global.yyyyMM_FMT.parse(billingPeriod + ""));
        } catch (ParseException e) {
            throw new RuntimeException("invalid billing period: " + billingPeriod);
        }
        Date startDate = db.beginOfMonth().build();
        Date endDate = db.nextMonth().build();
        return new DateRange(startDate, endDate);
    }

    public static class DateRange {
        private Date startDate;
        private Date endDate;

        public DateRange(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }
}
