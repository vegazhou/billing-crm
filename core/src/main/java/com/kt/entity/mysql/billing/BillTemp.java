package com.kt.entity.mysql.billing;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/4/11.
 */
@Table(name = "BB_BILL_TEMP")
@Entity
@SequenceGenerator(name="SEQ_TEMP_BILL", sequenceName="SEQ_TEMP_BILL", allocationSize = 1)
public class BillTemp {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SEQ_TEMP_BILL")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "ACCOUNT_PERIOD")
    private String accountPeriod;

    @Column(name = "FEE_TYPE")
    private int feeType;

    @Column(name = "AMOUNT", scale = 2, precision = 12)
    private Float amount;
    
    @Transient
    private String company;
    @Transient
    private String companyId;
    @Transient
    private String customerCode;
    @Transient
    private boolean isConfirmed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
