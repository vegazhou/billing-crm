package com.kt.api.bean.customer;

import com.kt.validation.annotation.InOneOf;

import javax.validation.constraints.Size;

/**
 * Created by Vega Zhou on 2016/3/19.
 */
public class Customer4Put {
    @Size(min = 0, max = 200, message = "customer.displayName.Size")
    private String displayName;

    @Size(max = 100, message = "customer.address.Size")
    private String address;

    @Size(max = 50, message = "customer.phone.Size")
    private String phone;

    private boolean isVat;
    
    private boolean rel;

    @Size(max = 30, message = "customer.vatNo.Size")
    private String vatNo;

    @Size(max = 100, message = "customer.bank.Size")
    private String bank;

    @Size(max = 50, message = "customer.bankAccount.Size")
    private String bankAccount;

    @InOneOf(candidates = {"ONE", "TWO", "THREE", "FOUR","FIVE"}, message = "customer.level.InOneOf")
    private String level;

    @Size(max = 100, message = "customer.contactName.Size")
    private String contactName;
    
    @Size(max = 500, message = "customer.contactEmail.Size")
    private String contactEmail;
    
    @Size(max = 50, message = "customer.contactPhone.Size")
    private String contactPhone;

    private int industry;


    
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public boolean isVat() {
        return isVat;
    }

    public void setIsVat(boolean isVat) {
        this.isVat = isVat;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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
