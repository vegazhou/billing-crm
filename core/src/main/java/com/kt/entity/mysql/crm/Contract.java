package com.kt.entity.mysql.crm;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Table(name = "B_CONTRACT")
@Entity
public class Contract {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "PID")
    private String pid;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "STATE")
    private String state;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "DRAFTDATE")
    private String draftDate;

    @Column(name = "DRAFTEDBY")
    private String draftedBy;

    @Column(name = "LASTMODIFIEDDATE")
    private String lastModifiedDate;

    @Column(name = "LASTMODIFIEDBY")
    private String lastModifiedBy;

    @Column(name = "IS_CHANGING")
    private int isChanging;

    @Column(name = "IS_ALTERATION")
    private int isAlteration;

    @Column(name = "SALESMAN")
    private String salesman;

    @Column(name = "ALTER_TARGET_ID")
    private String alterTargetId;

    @Column(name = "AGENT_ID")
    private String agentId;

    @Column(name = "IS_REGISTERED")
    private int isRegistered;

    @Column(name = "COMMENTS")
    private String comments;


    @Transient
    private String company;
    @Transient
    private String FIRST_INSTALLMENT;
    @Transient
    private String PID_1;
    
    @Transient
    private String salesmanname;

    @Transient
    private String agentName;
    

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

    public String getDraftDate() {
        return draftDate;
    }

    public void setDraftDate(String draftDate) {
        this.draftDate = draftDate;
    }

    public String getDraftedBy() {
        return draftedBy;
    }

    public void setDraftedBy(String draftedBy) {
        this.draftedBy = draftedBy;
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

    public int getIsChanging() {
        return isChanging;
    }

    public void setIsChanging(int isChanging) {
        this.isChanging = isChanging;
    }

    public int getIsAlteration() {
        return isAlteration;
    }

    public void setIsAlteration(int isAlteration) {
        this.isAlteration = isAlteration;
    }

    public String getAlterTargetId() {
        return alterTargetId;
    }

    public void setAlterTargetId(String alterTargetId) {
        this.alterTargetId = alterTargetId;
    }

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFIRST_INSTALLMENT() {
		return FIRST_INSTALLMENT;
	}

	public void setFIRST_INSTALLMENT(String fIRST_INSTALLMENT) {
		FIRST_INSTALLMENT = fIRST_INSTALLMENT;
	}

	public String getPID_1() {
		return PID_1;
	}

	public void setPID_1(String pID_1) {
		PID_1 = pID_1;
	}

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSalesmanname() {
		return salesmanname;
	}

	public void setSalesmanname(String salesmanname) {
		this.salesmanname = salesmanname;
	}

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public int getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(int isRegistered) {
        this.isRegistered = isRegistered;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
