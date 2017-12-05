package wang.huaichao.data.ds;

import wang.huaichao.Global;
import wang.huaichao.utils.DateBuilder;

import java.util.*;

/**
 * Created by Administrator on 8/23/2016.
 */
public class DateRangeBuilder {
    private Map<Date, DateRange> startMap = new HashMap<>();
    private Map<Date, DateRange> endMap = new HashMap<>();

    public void union(Date start, Date end) {

        if (start.getTime() > end.getTime()) {
            Date t = start;
            start = end;
            end = t;
        }

        DateRange dateRange = startMap.get(end);

        if (dateRange != null) {
            dateRange.setStart(start);
            startMap.remove(end);
            startMap.put(start, dateRange);
            return;
        }

        dateRange = endMap.get(start);

        if (dateRange != null) {
            dateRange.setEnd(end);
            endMap.remove(start);
            endMap.put(end, dateRange);
            return;
        }

        dateRange = new DateRange(start, end);
        startMap.put(start, dateRange);
        endMap.put(end, dateRange);
    }

    public List<DateRange> dateRanges() {
        ArrayList<DateRange> dateRanges = new ArrayList<>(startMap.values());
        Collections.sort(dateRanges, new Comparator<DateRange>() {
            @Override
            public int compare(DateRange dr1, DateRange dr2) {
                return (int) (dr2.getStart().getTime() - dr1.getStart().getTime());
            }
        });
        return dateRanges;
    }

    public void inspect() {
        System.out.println("========== list of DateRange ==========");
        List<DateRange> dateRanges = dateRanges();
        for (DateRange dateRange : dateRanges) {
            System.out.println(
                    Global.DB_DATETIME_FMT.format(dateRange.getStart()) + " : " +
                            Global.DB_DATETIME_FMT.format(dateRange.getEnd())
            );
        }
    }

    public static class DateRange {
        private Date start;
        private Date end;

        public DateRange(Date start, Date end) {
            this.start = start;
            this.end = end;
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
    }

    public static void main(String[] args) {
        DateBuilder db = new DateBuilder();
        Date start = db.beginOfMonth().build();
        Date end = db.nextMonth().build();

        DateRangeBuilder drb = new DateRangeBuilder();
        drb.union(start, end);

        drb.inspect();

        start = end;
        end = db.nextMonth().build();
        drb.union(start, end);
        drb.inspect();

        start = db.nextMonth().build();
        end = db.nextMonth().build();
        drb.union(start, end);
        drb.inspect();


    }
}
