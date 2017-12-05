package com.kt.sys;

import com.kt.session.Principal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vega Zhou on 2015/11/30.
 */
public class WafContext {

    public static final String IGNORE_MAIL = "ignore_mail";

    private static ThreadLocal<Map<String, Object>> props = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };


    public static void put(String key, Object value) {
        props.get().put(key, value);
    }


    public static Object get(String key) {
        return props.get().get(key);
    }


    public static void clear() {
        props.get().clear();
    }

}
