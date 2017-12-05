package com.kt.biz.bean;

/**
 * Created by Vega on 2017/11/24.
 */
public class CustomerAccountsBean {

    private String customerId;

    private String customerName;

    private String customerCode;

    private Integer prepaidAccountId;

    private Double prepaidBalance;

    private Integer depositAccountId;

    private Double depositBalance;

    private Integer ccDepositAccountId;

    private Double ccDepositBalance;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getPrepaidAccountId() {
        return prepaidAccountId;
    }

    public void setPrepaidAccountId(Integer prepaidAccountId) {
        this.prepaidAccountId = prepaidAccountId;
    }

    public Integer getDepositAccountId() {
        return depositAccountId;
    }

    public void setDepositAccountId(Integer depositAccountId) {
        this.depositAccountId = depositAccountId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Double getPrepaidBalance() {
        return prepaidBalance;
    }

    public void setPrepaidBalance(Double prepaidBalance) {
        this.prepaidBalance = prepaidBalance;
    }

    public Double getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(Double depositBalance) {
        this.depositBalance = depositBalance;
    }

    public Integer getCcDepositAccountId() {
        return ccDepositAccountId;
    }

    public void setCcDepositAccountId(Integer ccDepositAccountId) {
        this.ccDepositAccountId = ccDepositAccountId;
    }

    public Double getCcDepositBalance() {
        return ccDepositBalance;
    }

    public void setCcDepositBalance(Double ccDepositBalance) {
        this.ccDepositBalance = ccDepositBalance;
    }
}
