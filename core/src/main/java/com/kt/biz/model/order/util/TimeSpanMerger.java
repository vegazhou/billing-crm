package com.kt.biz.model.order.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Vega Zhou on 2016/3/21.
 */
public class TimeSpanMerger {

    List<TimeSpan> timeSpans = new ArrayList<>();

    public void addTimeSpan(TimeSpan timeSpan) {
        this.timeSpans.add(timeSpan);
    }


    public void addTimeSpan(Date start, Date end) {
        this.timeSpans.add(new TimeSpan(start, end));
        mergeTimeSpans();
    }


    public void mergeTimeSpans() {
        Collections.sort(timeSpans);
        this.timeSpans = joinTimeSpans(timeSpans);
    }


    private List<TimeSpan> joinTimeSpans(List<TimeSpan> timeSpans) {
        if (timeSpans.size() <= 1) {
            return timeSpans;
        }

        List<TimeSpan> result = new LinkedList<>();

        TimeSpan firstElement = timeSpans.get(0);
        TimeSpan secondElement = timeSpans.get(1);
        if (firstElement.isOverlappedWith(secondElement)) {
            firstElement.merge(secondElement);
            timeSpans.remove(0);
            timeSpans.remove(0);
            timeSpans.add(0, firstElement);
            return joinTimeSpans(timeSpans);
        } else {
            result.add(firstElement);
            timeSpans.remove(0);
            result.addAll(joinTimeSpans(timeSpans));
        }
        return result;
    }

    public boolean covers(Date start, Date end) {
        return covers(new TimeSpan(start, end));
    }

    public boolean covers(TimeSpan span) {
        if (timeSpans.size() > 0) {
            for (TimeSpan timeSpan : timeSpans) {
                if (timeSpan.covers(span)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void main(String args[]) throws ParseException {
        TimeSpanMerger merger = new TimeSpanMerger();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

//        for (int i = 0; i < 4; i++) {
//            Date start = randomDate(f.parse("2016-02-01"), f.parse("2016-12-31"));
//            Date end = randomDate(start, f.parse("2016-12-31"));
//            System.out.print(f.format(start));
//            System.out.print(" --> ");
//            System.out.println(f.format(end));
//            merger.addTimeSpan(start, end);
//        }
        merger.addTimeSpan(f.parse("2016-02-01"), f.parse("2016-02-10"));
        merger.addTimeSpan(f.parse("2016-02-10"), f.parse("2016-02-14"));
        merger.mergeTimeSpans();
        return;
    }
//
//
//    private static Date randomDate(Date start, Date end) {
//        try {
//            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
//            if (start.getTime() >= end.getTime()) {
//                return null;
//            }
//            long date = random(start.getTime(), end.getTime());
//
//            return new Date(date);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static long random(long begin, long end) {
//        long rtn = begin + (long) (Math.random() * (end - begin));
//        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
//        if (rtn == begin || rtn == end) {
//            return random(begin, end);
//        }
//        return rtn;
//    }
}
