package com.kt.biz.model.order.util;

import com.kt.biz.model.order.OrderBean;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Vega Zhou on 2016/3/21.
 */
public class OrderUtils {

    public static boolean isTimeOverlapping(OrderBean order1, OrderBean order2) {
        return !(!order1.getEndDate().after(order2.getStartDate()) || !order2.getEndDate().after(order1.getStartDate()));
    }


    public static boolean isSameSite(String site1, String site2) {
        return StringUtils.isNotBlank(site1) && StringUtils.isNotBlank(site2) && StringUtils.equalsIgnoreCase(site1.trim(), site2.trim());
    }


    public static void main(String args[]) throws ParseException {
        OrderBean order1 = new OrderBean("");
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order1.setStartDate(f.parse("2016-01-01 00:00:00"));
        order1.setEndDate(f.parse("2016-10-01 00:00:01"));
        OrderBean order2 = new OrderBean("");
        order2.setStartDate(f.parse("2016-10-01 00:00:00"));
        order2.setEndDate(f.parse("2016-11-01 00:00:00"));

        System.out.println(isTimeOverlapping(order1, order2));
    }
}
