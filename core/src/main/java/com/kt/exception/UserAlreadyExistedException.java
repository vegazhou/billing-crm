package com.kt.exception;

/**
 * Created by Vega Zhou on 2016/3/31.
 */
public class UserAlreadyExistedException extends WafException {
    @Override
    public String getKey() {
        return "user_already_existed";
    }
}
