package com.kt.service;


import com.kt.common.util.SqlHelper;

/**
 * Base class that defines simple search criteria. DAOs that wish offer searching capabilities can extend this, offering
 * methods for the searchable properties.
 */
public class SearchFilter {
	private static final int DEFAULT_PAGE_SIZE = 10;

	private int pageSize = DEFAULT_PAGE_SIZE; // row count per page.

	private String orderByField;

	private String orderType; // ascending vs descending, value is 'DESC' or 'ASC'

	private String displayName;

	private String type;

	private String state;
	
	private String customerId;

	private String customerName;
	
	private String accountPeriod;
	
	private String salesman;


	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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


	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAccountPeriod() {
		return accountPeriod;
	}

	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	
	
}