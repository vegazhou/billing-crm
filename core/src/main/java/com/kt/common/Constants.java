/**
 * 
 */
package com.kt.common;

/**
 * OAF Constants
 */
public final class Constants {
	public static final int USER_TYPE_OPEN = 0; //开发注册
	public static final int USER_TYPE_PARENT = 1; //家长
	public static final int USER_TYPE_TEACHER = 2; //老师
	public static final int USER_TYPE_PARENT_TEACHER = 3; //既是家长又是老师
	
	public static final String ORG_REGION_PROVINCE = "1"; //省级组织
	public static final String ORG_REGION_CITY = "2"; //市级组织
	public static final String ORG_REGION_AREA = "3"; //区级组织
	
	public static final int ORG_USER_TYPE_ADMIN = 0; //系统管理员
	public static final int ORG_USER_TYPE_AGENT = 1; //代理商
	public static final int ORG_USER_TYPE_SALESMAN = 2; //业务员
	public static final int ORG_USER_TYPE_SCHOOL_ADMIN = 3; //学校负责人
	public static final int ORG_USER_TYPE_CLASS_ADMIN = 4; //班级负责人
	public static final int ORG_USER_TYPE_OPRATION = 5; //运维人员
	public static final int ORG_USER_TYPE_OTHER = 6; //系统管理员
	
	public static final int SEARCH_REPORT_TYPE_TOTAL_USER_BY_DAY = 1; // 当日总用户
	public static final int SEARCH_REPORT_TYPE_ACTIVE_USER_BY_DAY = 2; // 当日活跃用户
	public static final int SEARCH_REPORT_TYPE_NEW_USER_TODAY = 3; // 今日新 用户
	public static final int SEARCH_REPORT_TYPE_ACTIVE_USER_TODAY = 4; //今日活跃用户
	
	public static final int SEARCH_REPORT_TYPE_TOTAL_USER_BY_REGION = 1; // 按区域查询总人数比例图
	public static final int SEARCH_REPORT_TYPE_ACTIVE_USER_BY_REGION = 2; // 按区域查询活跃人数比例图
	
	public static final int SEARCH_REPORT_REGION_TYPE_PROVINCE = 1; //查省注册人数比例图
	public static final int SEARCH_REPORT_REGION_TYPE_CITY = 2; //查市注册人数比例图
	public static final int SEARCH_REPORT_REGION_TYPE_AREA = 3; //查区注册人数比例图
	
	public static final String SYS_ROLE_AGENT_ROLEID = "100002";//代理商角色ID
	public static final String SYS_ROLE_SCHOOL_ROLEID = "100003";//学校负责人角色ID
	public static final String SYS_ROLE_CLASS_ROLEID = "100004";//班级负责人角色ID
	
	public static final String initPassword = "111111";//初始密码
	
	public static final String SYS_ROLE_AGENT_DEFAULTROLENAME = "业务员";//代理商默认的角色名称
	
	public static final String SMS_SERVER_URL = "http://114.141.133.137/JianzhouSMSWSServer/services/BusinessService";//SMS网关地址
	public static final String SMS_SERVER_USERNAME = "sdk_jzkj";//SMS网关用户名
	public static final String SMS_SERVER_PASSWORD = "Szj@0106";//SMS网关用户密码
	public static final String PARANAME_REFEREE = "REFEREE";
	public static final String PARANAME_COMMENTS = "USER_COMMENTS";
	/** iOS device token length */
	public static final int DEVICE_TOKEN_LENGTH = 64;
	public static final int FEETYPE_WEBEX_FIRST_INSTALLMENT = 1;
	public static final String NETWORK_USAGE_OR_OVERAGE = "Network usage or overage";
	public static final String NETWORK_SUBSCRIPTION_FEE = "Network subscription fee";
	public static final String SITE_CONFIGURATION_FEE = "Site configuration fee";
	public static final String AUDIO_USAGE_OR_OVERAGE = "Audio usage or overage";
	public static final String CREDIT_NOTE = "Credit Note";
	public static final String AUDIO_PACKAGE_FEE = "Audio package fee";
	public static final String PSTN_PERSONAL_PACKET = "Personal audio package fee";
	public static final String STORAGE_FEE = "Recording service";
	public static final String INVOICE ="Invoice";
	public static final String CREDITNOTE ="Credit Note";
	public static final String CREDITTYPE ="Upselling";
	public static final String DIRECT ="Direct";
	public static final String PRODUCT_TYPE_AUDIO_USAGE_OR_OVERAGE ="AUDIO_USEAGE_OR_OVERAGE_INVOICE";
	public static final String PRODUCT_TYPE_NETWORK_USAGE_OR_OVERAGE ="NETWORK_USAGE_OR_OVERAGE_INVOICE";
	public static final float TAXRATE = 1.0672f;
	public static final String EDR_CALL_TYPE_INTERNATIONAL = "International";

	public static final String EDR_CALL_TYPE_DOMESTIC = "Domestic";
	
	public static final String CREDITTYPE_ADJUSTBILL ="Adjustbill";
	public static final String PSTN_FEE = "PSTN fee";
	public static final String CMR_FEE = "CMR fee";
	public static final String PERSONAL_FEE  = "Monthly Pay Personal fee";
	public static final String SHLXCODE = "KG80226HD6";
}
