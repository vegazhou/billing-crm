package com.kt.biz.customer;

import com.kt.entity.mysql.crm.Customer;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Vega Zhou on 2016/5/31.
 */
public class CustomerOptionalFields {

    private String address;

    private String phone;

    private String vatNo;

    private String bank;

    private String bankAccount;
    
    private boolean rel;

    private int industry;

    public void fillCustomerEntity(Customer customer) {
        if (StringUtils.isNotBlank(address)) {
            customer.setAddress(address.trim());
        }
        if (StringUtils.isNotBlank(phone)) {
            customer.setPhone(phone.trim());
        }
        if (StringUtils.isNotBlank(vatNo)) {
            customer.setVatNo(vatNo.trim());
        }
        if (StringUtils.isNotBlank(bank)) {
            customer.setBank(bank.trim());
        }
        if (StringUtils.isNotBlank(bankAccount)) {
            customer.setBankAccount(bankAccount.trim());
        }
        customer.setIsRel(rel ? 1 : 0);
        customer.setIndustry(industry);
        
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

	public boolean getRel() {
		return rel;
	}

	public void setRel(boolean rel) {
		this.rel = rel;
	}

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }
}
