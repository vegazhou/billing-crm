package com.kt.util;

import java.math.BigDecimal;

/**
 * Created by Vega Zhou on 2016/4/12.
 */
public class MathUtil {

    public static float scale(float amount) {
        return new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static double scale(double amount) {
        return new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static BigDecimal scale(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
