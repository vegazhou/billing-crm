package com.kt.common.exception;

/**
 * ExceptionKey
 */
public class ExceptionKey {
    /** The Constant SEND_SMS_ERROR. */
    public static final String SEND_SMS_ERROR = "apis.captcha.sms.failed";
    
    public static final String AUTHOR_NOACCESSTOKEN_ERROR = "apis.author.noaccesstoken.failed";
    
    public static final String AUTHOR_NOPERMISSION_ERROR = "apis.author.nopermission.failed";
    
    public static final String AUTHOR_PERMISSIONLISTNULL_ERROR = "apis.author.permissionlistnull.failed";
    

    /**
     * Hide Constructor
     */
    private ExceptionKey() {
    }
}
