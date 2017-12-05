package com.kt.biz.sap;

import com.kt.biz.billing.AccountPeriod;

import java.math.BigDecimal;

/**
 * Created by Vega Zhou on 2016/11/7.
 */
public class SapPdf {
    private String customerName;

    private String customerCode;

    private String customerContact;

    private AccountPeriod accountPeriod;

    private String creditNoteStartDate;

    private String creditNoteEndDate;



    private BigDecimal pstnFee = new BigDecimal(0);

    private BigDecimal overageFee = new BigDecimal(0);

    private BigDecimal monthlyFee = new BigDecimal(0);

    private BigDecimal onetimeFee = new BigDecimal(0);

    private BigDecimal originalAmount = new BigDecimal(0);

    private BigDecimal refundAmount = new BigDecimal(0);


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public AccountPeriod getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(AccountPeriod accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public BigDecimal getPstnFee() {
        return pstnFee;
    }

    public void setPstnFee(BigDecimal pstnFee) {
        this.pstnFee = pstnFee;
    }

    public BigDecimal getOverageFee() {
        return overageFee;
    }

    public void setOverageFee(BigDecimal overageFee) {
        this.overageFee = overageFee;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public BigDecimal getOnetimeFee() {
        return onetimeFee;
    }

    public String getCreditNoteStartDate() {
        return creditNoteStartDate;
    }

    public void setCreditNoteStartDate(String creditNoteStartDate) {
        this.creditNoteStartDate = creditNoteStartDate;
    }

    public String getCreditNoteEndDate() {
        return creditNoteEndDate;
    }

    public void setCreditNoteEndDate(String creditNoteEndDate) {
        this.creditNoteEndDate = creditNoteEndDate;
    }

    public void setOnetimeFee(BigDecimal onetimeFee) {
        this.onetimeFee = onetimeFee;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getTotalAmount() {
        return pstnFee.add(overageFee).add(monthlyFee).add(onetimeFee);
    }



    public void addMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = this.monthlyFee.add(monthlyFee);
    }

    public void addOnetimeFee(BigDecimal onetimeFee) {
        this.onetimeFee = this.onetimeFee.add(onetimeFee);
    }

    public void addOverageFee(BigDecimal overageFee) {
        this.overageFee = this.overageFee.add(overageFee);
    }

    public void addPstnFee(BigDecimal pstnFee) {
        this.pstnFee = this.pstnFee.add(pstnFee);
    }

    public int getFeeCount() {
        int count = 0;
        if (pstnFee.compareTo(new BigDecimal(0)) > 0) {
            count ++;
        }
        if (overageFee.compareTo(new BigDecimal(0)) > 0) {
            count ++;
        }
        if (monthlyFee.compareTo(new BigDecimal(0)) > 0) {
            count ++;
        }
        if (onetimeFee.compareTo(new BigDecimal(0)) > 0) {
            count ++;
        }
        return count;
    }
}
