package com.kt.service;

import java.util.Date;

import com.kt.common.util.CommonUtil;
import com.kt.common.util.SqlHelper;

/**
 * Base class that defines simple search criteria. DAOs that wish offer searching capabilities can extend this, offering
 * methods for the searchable properties.
 */
public class SearchCriteria {
  /**
   * pageSize
   */
  protected int pageSize; // row count per page.
  
  protected int totalCount; // page total row count
  /**
   * orderByField
   */
  protected String orderByField;
  /**
   * orderType
   */
  protected String orderType; // ascending vs descending, value is 'DESC' or 'ASC'
  /**
   * searchKey
   */
  protected String searchKey; // search textField.
  
  /**
   * orgId
   */
  protected String orgId; //公司ID
  /**
   * orgName
   */
  protected String orgName; //公司名称
  /**
   * orgadminPhone
   */
  protected String orgAdminPhone; //公司负责人电话.

  /**
   * orgAdminAccount
   */
  protected String orgAdminAccount; //公司负责人帐号
  
  /**
   * orgAdminName
   */
  protected String orgAdminName; //公司负责人姓名
  
  /**
   * province
   */
  protected String province;//省
  /**
   * city
   */
  protected String city;//市
  /**
   * area
   */
  protected String area;//区
  /**
   * The StartTime selected by User in Page
   */
  protected Date searchStartTime;
  /**
   * The EndTime selected by User in Page
   */
  protected Date searchEndTime;
  
  /**
   * sysroleName
   */
  protected String sysroleName;//角色名称
  /**
   * status
   */
  protected String status;//状态
  /**
   * schoolName
   */
  protected String schoolName;//学校名称
  
  /**
   * schoolName
   */
  protected String schoolID;//学校ID
  /**
   * schoolAddr
   */
  protected String schoolAddr;//学校地址
  /**
   * classesName
   */
  protected String classesName;//班级名称
  
  protected String classesID; //班级ID
  
  protected String studentID; //学生ID
  
  /**
   * userAccount
   */
  protected String userAccount;//用户帐号
  /**
   * userName
   */
  protected String userName;//用户姓名
  /**
   * userPhone
   */
  protected String userPhone;//用户电话
  /**
   * xiaoxincode
   */
  protected String xiaoxincode;//校信号
  
  /**
   * ownerName
   */
  protected String ownerName; //学校/班级负责人姓名
  /**
   * ownerPhone
   */
  protected String ownerPhone; //学校/班级负责人电话
  
  private String studentName; // 学生姓名
  
  /**
   * userType
   */
  protected int userType = -1; //用户类型
  /**
   * currentUserID
   */
  protected String currentUserID; // 当前登录用户uuid
  
  protected String productID; //商品id
  
  protected String productName; //商品名称
  
  protected int searchUserType; // 报表查询用户类型
  
  protected int searchReportType; // 报表查询类型: 1-今日新增用户  2-今日活跃用户
  
  protected int searchRegionType = 0; //报表查询区域类型: 1-省 2-市 3-区
  
  protected String orderNumber; // 订单编号
  
  protected String orderStatus; // 订单状态
  
  protected int searchRegister = -1; // 是否注册
  
  protected String subjectName;
  protected String subjectCourse;
  protected String subjectGrade;
  protected int subjectType = -1;
  protected String subjectUploader;
  
  
  protected  String  dynamictype;	// 表字段名称为:s_dynamictype 相应注释为:班级动态类型。
  protected  String  theme;	// 表字段名称为:s_theme 相应注释为:班级动态主题。
  protected  String logincount;//登录次数
  protected  String classactivitycount;//班级活跃度
  
  protected String userCreatorID;
  
  protected String domain;
  
  protected String serviceName;
  
  
  /**
   * serviceId
   */
  protected String serviceId;
  
  /**
   *SearchCriteria
   */
  public SearchCriteria() {
      this.pageSize = 50;
      this.orderType = "ASC";
      this.orderByField = "";
      this.searchKey = "";
  }

	
	public int getPageSize() {
		return pageSize;
	}
	
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public String getOrderByField() {
		return orderByField;
	}
	
	
	public void setOrderByField(String orderByField) {
		SqlHelper.validateOrderByList(orderByField);
		this.orderByField = orderByField;
	}
	
	
	public String getOrderType() {
		return orderType;
	}
	
	
	public void setOrderType(String orderType) {
		SqlHelper.validateOrderType(orderType);
		this.orderType = orderType;
	}
	
	
	public String getSearchKey() {
		return searchKey;
	}
	
	
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
	
	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = CommonUtil.transactSQLInjection(orgId);
	}


	public String getOrgName() {
		return orgName;
	}
	
	
	public void setOrgName(String orgName) {
		this.orgName = CommonUtil.transactSQLInjection(orgName);
	}
	
	
	public String getOrgAdminPhone() {
		return orgAdminPhone;
	}
	
	
	public void setOrgAdminPhone(String orgAdminPhone) {
		this.orgAdminPhone = CommonUtil.transactSQLInjection(orgAdminPhone);
	}
	
	
	public String getOrgAdminAccount() {
		return orgAdminAccount;
	}
	
	
	public void setOrgAdminAccount(String orgAdminAccount) {
		this.orgAdminAccount = CommonUtil.transactSQLInjection(orgAdminAccount);
	}
	
	
	public String getOrgAdminName() {
		return orgAdminName;
	}
	
	
	public void setOrgAdminName(String orgAdminName) {
		this.orgAdminName = CommonUtil.transactSQLInjection(orgAdminName);
	}
	
	
	public String getProvince() {
		return province;
	}
	
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	
	public String getCity() {
		return city;
	}
	
	
	public void setCity(String city) {
		this.city = city;
	}
	
	
	public String getArea() {
		return area;
	}
	
	
	public void setArea(String area) {
		this.area = area;
	}
	
	
	public Date getSearchStartTime() {
		return searchStartTime;
	}
	
	
	public void setSearchStartTime(Date searchStartTime) {
		this.searchStartTime = searchStartTime;
	}
	
	
	public Date getSearchEndTime() {
		return searchEndTime;
	}
	
	
	public void setSearchEndTime(Date searchEndTime) {
		this.searchEndTime = searchEndTime;
	}


	public String getSysroleName() {
		return sysroleName;
	}


	public void setSysroleName(String sysroleName) {
		this.sysroleName = CommonUtil.transactSQLInjection(sysroleName);
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = CommonUtil.transactSQLInjection(status);
	}


	public String getSchoolName() {
		return schoolName;
	}


	public void setSchoolName(String schoolName) {
		this.schoolName = CommonUtil.transactSQLInjection(schoolName);
	}


	public String getSchoolID() {
		return schoolID;
	}


	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}


	public String getSchoolAddr() {
		return schoolAddr;
	}


	public void setSchoolAddr(String schoolAddr) {
		this.schoolAddr = CommonUtil.transactSQLInjection(schoolAddr);
	}

	public String getClassesID() {
		return classesID;
	}


	public void setClassesID(String classesID) {
		this.classesID = classesID;
	}


	public String getClassesName() {
		return classesName;
	}


	public void setClassesName(String classesName) {
		this.classesName = CommonUtil.transactSQLInjection(classesName);
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = CommonUtil.transactSQLInjection(userName);
	}


	public String getUserPhone() {
		return userPhone;
	}


	public void setUserPhone(String userPhone) {
		this.userPhone = CommonUtil.transactSQLInjection(userPhone);
	}


	public String getUserAccount() {
		return userAccount;
	}


	public void setUserAccount(String userAccount) {
		this.userAccount = CommonUtil.transactSQLInjection(userAccount);
	}


	public String getXiaoxincode() {
		return xiaoxincode;
	}


	public void setXiaoxincode(String xiaoxincode) {
		this.xiaoxincode = CommonUtil.transactSQLInjection(xiaoxincode);
	}

	public int getSearchRegister() {
		return searchRegister;
	}


	public void setSearchRegister(int searchRegister) {
		this.searchRegister = searchRegister;
	}


	public String getOwnerName() {
		return ownerName;
	}


	public void setOwnerName(String ownerName) {
		this.ownerName = CommonUtil.transactSQLInjection(ownerName);
	}


	public String getOwnerPhone() {
		return ownerPhone;
	}


	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = CommonUtil.transactSQLInjection(ownerPhone);
	}


	public String getCurrentUserID() {
		return currentUserID;
	}


	public void setCurrentUserID(String currentUserID) {
		this.currentUserID = currentUserID;
	}

	public String getStudentID() {
		return studentID;
	}


	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}


	public int getUserType() {
		return userType;
	}


	public void setUserType(int userType) {
		this.userType = userType;
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = CommonUtil.transactSQLInjection(studentName);
	}


	public String getProductID() {
		return productID;
	}


	public void setProductID(String productID) {
		this.productID = CommonUtil.transactSQLInjection(productID);
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = CommonUtil.transactSQLInjection(productName);
	}


	public int getSearchUserType() {
		return searchUserType;
	}


	public void setSearchUserType(int searchUserType) {
		this.searchUserType = searchUserType;
	}

	public int getSearchReportType() {
		return searchReportType;
	}

	public void setSearchReportType(int searchReportType) {
		this.searchReportType = searchReportType;
	}

	public int getSearchRegionType() {
		return searchRegionType;
	}

	public void setSearchRegionType(int searchRegionType) {
		this.searchRegionType = searchRegionType;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = CommonUtil.transactSQLInjection(orderNumber);
	}


	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = CommonUtil.transactSQLInjection(orderStatus);
	}


	public String getSubjectName() {
		return subjectName;
	}


	public void setSubjectName(String subjectName) {
		this.subjectName = CommonUtil.transactSQLInjection(subjectName);
	}


	public String getSubjectCourse() {
		return subjectCourse;
	}


	public void setSubjectCourse(String subjectCourse) {
		this.subjectCourse = CommonUtil.transactSQLInjection(subjectCourse);
	}


	public String getSubjectGrade() {
		return subjectGrade;
	}


	public void setSubjectGrade(String subjectGrade) {
		this.subjectGrade = CommonUtil.transactSQLInjection(subjectGrade);
	}


	public int getSubjectType() {
		return subjectType;
	}


	public void setSubjectType(int subjectType) {
		this.subjectType = subjectType;
	}


	public String getSubjectUploader() {
		return subjectUploader;
	}


	public void setSubjectUploader(String subjectUploader) {
		this.subjectUploader = CommonUtil.transactSQLInjection(subjectUploader);
	}


	public String getUserCreatorID() {
		return userCreatorID;
	}


	public void setUserCreatorID(String userCreatorID) {
		this.userCreatorID = CommonUtil.transactSQLInjection(userCreatorID);
	}


	public String getDynamictype() {
		return dynamictype;
	}


	public void setDynamictype(String dynamictype) {
		this.dynamictype = dynamictype;
	}


	public String getTheme() {
		return theme;
	}


	public void setTheme(String theme) {
		this.theme = theme;
	}


	public String getLogincount() {
		return logincount;
	}


	public void setLogincount(String logincount) {
		this.logincount = logincount;
	}


	public String getClassactivitycount() {
		return classactivitycount;
	}


	public void setClassactivitycount(String classactivitycount) {
		this.classactivitycount = classactivitycount;
	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	
		
}