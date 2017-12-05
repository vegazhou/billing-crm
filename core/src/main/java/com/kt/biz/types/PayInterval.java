package com.kt.biz.types;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public class PayInterval {
    public static int ONE_TIME_VALUE = -1;

    public static final PayInterval MONTHLY = new PayInterval(1);
    public static final PayInterval QUARTERLY = new PayInterval(3);
    public static final PayInterval HALF_YEARLY = new PayInterval(6);
    public static final PayInterval YEARLY = new PayInterval(12);
    public static final PayInterval ONE_TIME = new PayInterval(-1);


    public static final String _MONTHLY = "MONTHLY";
    public static final String _QUARTERLY = "QUARTERLY";
    public static final String _HALF_YEARLY = "HALF_YEARLY";
    public static final String _YEARLY = "YEARLY";
    public static final String _ONE_TIME = "ONE_TIME";

    private int interval;

    public PayInterval(int interval) {
        this.interval = interval;
    }



    public int getInterval() {
        return interval;
    }


    public boolean isOneTime() {
        return interval == ONE_TIME_VALUE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PayInterval) {
            PayInterval i = (PayInterval) obj;
            return i.interval == this.interval;
        } else {
            return false;
        }
    }

    public static PayInterval valueOf(String pay) {
        if (_MONTHLY.equals(pay)) {
            return MONTHLY;
        } else if (_QUARTERLY.equals(pay)) {
            return QUARTERLY;
        } else if (_HALF_YEARLY.equals(pay)) {
            return HALF_YEARLY;
        } else if (_YEARLY.equals(pay)) {
            return YEARLY;
        } else if (_ONE_TIME.equals(pay)) {
            return ONE_TIME;
        }
        return null;
    }
}
