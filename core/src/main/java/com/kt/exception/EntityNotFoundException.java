package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/11.
 */
public class EntityNotFoundException extends WafException {
    @Override
    public String getKey() {
        return "entity_not_found";
    }
}
