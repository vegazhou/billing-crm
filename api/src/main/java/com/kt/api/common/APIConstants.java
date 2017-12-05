package com.kt.api.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.MediaType;


/**
 * The Class Constants.
 */
public class APIConstants {

	/** The Constant MEDIATYPE. */
	public static final String MEDIATYPE = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8";

	/** The Constant HEADER_NAME_UID. */
	public static final String HEADER_NAME_UID = "Uid";

	/** The Constant HEADER_NAME_TOKEN. */
	public static final String HEADER_NAME_TOKEN = "Token";

	/** The Constant HEADER_NAME_SIGN. */
	public static final String HEADER_NAME_SIGN = "Sign";

	/** The Constant HEADER_NAME_SOURCE. */
	public static final String HEADER_NAME_SOURCE = "Source";

	/** The Constant HEADER_NAME_LOCATION. */
	public static final String HEADER_NAME_LOCATION = "Location";

	/** The Constant HEADER_NAME_USERAGENT. */
	public static final String HEADER_NAME_USERAGENT = "user-agent";

	/** The Constant LOG_TYPE_GETSINGLE. */
	public static final int LOG_TYPE_GETSINGLE = 1;

	/** The Constant LOG_TYPE_GETLIST. */
	public static final int LOG_TYPE_GETLIST = 2;

	/** The Constant LOG_TYPE_CREATESINGLE. */
	public static final int LOG_TYPE_CREATESINGLE = 3;

	/** The Constant LOG_TYPE_CREATEMULTIPLE. */
	public static final int LOG_TYPE_CREATEMULTIPLE = 4;

	/** The Constant LOG_TYPE_UPDATE. */
	public static final int LOG_TYPE_UPDATE = 5;

	/** The Constant LOG_TYPE_DELETE. */
	public static final int LOG_TYPE_DELETE = 6;

	/** The Constant LOG_TYPE_SEARCH. */
	public static final int LOG_TYPE_SEARCH = 7;

	/** The Constant LOG_TYPE_PUBLISH. */
	public static final int LOG_TYPE_PUBLISH = 8;

	/** The Constant LOG_TYPE_LOGIN. */
	public static final int LOG_TYPE_LOGIN = 9;

	/** The Constant LOG_TYPE_LOGOUT. */
	public static final int LOG_TYPE_LOGOUT = 10;

	/** The Constant LOG_TYPE_SMS. */
	public static final int LOG_TYPE_SMS = 99;

	/** The Constant LOG_RESULT_FAIL. */
	public static final int LOG_RESULT_FAIL = 0;

	/** The Constant LOG_RESULT_SUCCESS. */
	public static final int LOG_RESULT_SUCCESS = 1;

	/** The Constant MILLIS_DAY. */
	public static final long MILLIS_DAY = 86400000L;

	/** The Constant MILLIS_WEEK. */
	public static final long MILLIS_WEEK = 604800000L;

	/** The Constant MILLIS_SECOND. */
	public static final long MILLIS_SECOND = 1000L;

	/** The Constant LOG_OBJ_AREA. */
	public static final String LOG_OBJ_AREA = "area";

	/** The Constant LOG_OBJ_CAPTCHA. */
	public static final String LOG_OBJ_CAPTCHA = "captcha";

	/** The Constant LOG_OBJ_ORG. */
	public static final String LOG_OBJ_ORG = "org";

	/** The Constant LOG_OBJ_ORGUSER. */
	public static final String LOG_OBJ_ORGUSER = "orguser";

	/** The Constant LOG_OBJ_GROUP. */
	public static final String LOG_OBJ_GROUP = "group";
	
	/** The Constant LOG_OBJ_GROUPMEMBER. */
	public static final String LOG_OBJ_GROUPMEMBER = "groupMember";

	/** The Constant LOG_OBJ_POSITION. */
	public static final String LOG_OBJ_POSITION = "position";

	/** The Constant LOG_OBJ_SKILL. */
	public static final String LOG_OBJ_SKILL = "skill";

	/** The Constant LOG_OBJ_INDUSTRY. */
	public static final String LOG_OBJ_INDUSTRY = "industry";

	/** The Constant APP_NAME_WHITE_LIST. */
	public static final Set<String> APP_NAME_WHITE_LIST = new HashSet<String>(Arrays.asList("101", "102", "103", "104",
			"201", "202", "203", "204", "301", "302", "303"));

	/** The Constant APP_NAME_FOR_JGWX. */
	public static final String APP_NAME_FOR_JGWX = "JGWX";

	/** The Constant APP_NAME_FOR_XKZX. */
	public static final String APP_NAME_FOR_XKZX = "XKZX";

	/** The Constant APP_NAME_FOR_QYDX. */
	public static final String APP_NAME_FOR_QYDX = "QYDX";

	/** The Constant APP_NAME_FOR_XZT. */
	public static final String APP_NAME_FOR_XZT = "XZT";
	
	public static final String ORGADMINROLE = "002";


	/**
	 * Hide Constructor.
	 */
	private APIConstants() {
	}
}
