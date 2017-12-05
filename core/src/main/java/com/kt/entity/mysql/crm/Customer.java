package com.kt.entity.mysql.crm;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Table(name = "B_CUSTOMER")
@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "PID")
    private String pid;

    @Column(name = "code")
    private String code;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "ISVAT")
    private int isVat;

    @Column(name = "IS_REL")
    private int isRel;

    @Column(name = "VAT_NO")
    private String vatNo;

    @Column(name = "BANK")
    private String bank;

    @Column(name = "BANK_ACCOUNT")
    private String bankAccount;

    @Column(name = "CLEVEL")
    private int level;

    @Column(name = "SAP_SYNCED")
    private int sapSynced;



    @Column(name = "CREATEDATE")
    private String createDate;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "LASTMODIFIEDDATE")
    private String lastModifiedDate;

    @Column(name = "LASTMODIFIEDBY")
    private String lastModifiedBy;
    
    @Column(name = "CONTACT_NAME")
    private String contactName;
    
    @Column(name = "CONTACT_EMAIL")
    private String contactEmail;
    
    @Column(name = "CONTACT_PHONE")
    private String contactPhone;

    @Column(name = "AGENT_TYPE")
    private String agentType;

    @Column(name = "INDUSTRY")
    private int industry;
    

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getIsVat() {
        return isVat;
    }

    public void setIsVat(int isVat) {
        this.isVat = isVat;
    }

    public int getIsRel() {
        return isRel;
    }

    public void setIsRel(int isRel) {
        this.isRel = isRel;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSapSynced() {
        return sapSynced;
    }

    public void setSapSynced(int sapSynced) {
        this.sapSynced = sapSynced;
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

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }
}
