package com.kt.exception;

/**
 * Created by Rickey.
 */
public class WbxProvTaskNotFoundException extends WafException {
	  @Override
	    public String getKey() {
	        return "wbxProvTask_not_existed";
	    }
}
