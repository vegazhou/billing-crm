package com.kt.exception;

/**
 * Created by Vega Zhou on 2015/10/21.
 */
public class UserNotFoundException extends WafException {
	  @Override
	    public String getKey() {
	        return "user_not_existed";
	    }
}
