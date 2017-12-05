package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/6/1.
 */
public class SalesmanNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "salesman_not_found";
    }
}
