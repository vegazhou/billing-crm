package com.kt.biz.customer;

/**
 * Created by Vega Zhou on 2016/5/31.
 */
public enum CustomerLevel {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private int level;

    public int getLevel() {
        return level;
    }

    CustomerLevel(int level) {
        this.level = level;
    }

    CustomerLevel valueOf(int level) {
        switch (level) {
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            case 5:
                return FIVE;
        }
        return null;
    }
}
