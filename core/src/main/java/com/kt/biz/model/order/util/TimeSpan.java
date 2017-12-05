package com.kt.biz.model.order.util;

import java.util.Date;

/**
 * Created by Vega Zhou on 2016/3/21.
 */
public class TimeSpan implements Comparable<TimeSpan> {

    private Date start;

    private Date end;

    public TimeSpan(Date start, Date end) {
        assert start != null && end != null;
        assert !start.after(end);
        this.start = start;
        this.end = end;
    }

    public boolean isOverlappedWith(TimeSpan span) {
        Date laterStart = this.start.after(span.getStart()) ? this.start : span.getStart();
        Date earlierEnd = this.end.before(span.getEnd()) ? this.end : span.getEnd();
        return !laterStart.after(earlierEnd);
    }

    public boolean covers(TimeSpan span) {
        return !start.after(span.getStart()) && !end.before(span.getEnd());
    }


    public void merge(TimeSpan timeSpan) {
        if (isOverlappedWith(timeSpan)) {
            this.start = this.start.before(timeSpan.getStart()) ? this.start : timeSpan.getStart();
            this.end = this.end.after(timeSpan.getEnd()) ? this.end : timeSpan.getEnd();
        }
    }



    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }


    @Override
    public int compareTo(TimeSpan o) {
        int startCompareResult = start.compareTo(o.getStart());
        if (startCompareResult != 0) {
            return startCompareResult;
        } else {
            return end.compareTo(o.getEnd());
        }
    }
}
