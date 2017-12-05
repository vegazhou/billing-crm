package com.kt.entity.mysql.billing;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
@Table(name = "BB_ACCOUNT")
@Entity
@SequenceGenerator(name="SEQ_ACCOUNT_ID", sequenceName="SEQ_ACCOUNT_ID", allocationSize = 1)
public class CustomerAccount {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQ_ACCOUNT_ID")
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "BALANCE")
    private Float balance;

    @Column(name = "CUSTOMER_ID")
    private String customerId;
    
    @Transient
    private String company;

    @Transient
    private String customerCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
