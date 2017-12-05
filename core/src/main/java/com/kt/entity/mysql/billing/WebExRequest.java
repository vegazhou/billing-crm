package com.kt.entity.mysql.billing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Created by Garfield Chen on 2016/6/14.
 */

@Table(name = "B_WBX_PROVISION_TASK")
@Entity
public class WebExRequest {
    @Id

	@Column(name = "id")
	private String id;
    
    @Column(name = "ORDER_ID")
    private String ORDER_ID;
    
    @Column(name = "CONTRACT_ID")
    private String CONTRACT_ID;
    
    @Column(name = "SITE_NAME")
    private String SITE_NAME;
    
    @Column(name = "TYPE")
    private String TYPE;
    
    @Column(name = "STATE")
    private String STATE;
    
    @Column(name = "WBX_PROV_STR")
    private String WBX_PROV_STR;
    
    @Column(name = "CALL_BACK_STR")
    private String CALL_BACK_STR;
    
    @Column(name = "CREATETIME")
    private String CREATETIME;
    
    @Column(name = "CALLBACKTIME")
    private String CALLBACKTIME;
    
    @Column(name = "SITE_ID")
    private String SITE_ID;
    
    @Column(name = "HOST_EMAIL")
    private String HOST_EMAIL;
    
    @Column(name = "CMD_RESULT")
    private String CMD_RESULT;
    
    @Transient
    private String DISPLAY_NAME;

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }

    public String getCONTRACT_ID() {
        return CONTRACT_ID;
    }

    public void setCONTRACT_ID(String CONTRACT_ID) {
        this.CONTRACT_ID = CONTRACT_ID;
    }

    public String getSITE_NAME() {
        return SITE_NAME;
    }

    public void setSITE_NAME(String SITE_NAME) {
        this.SITE_NAME = SITE_NAME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getWBX_PROV_STR() {
        return WBX_PROV_STR;
    }

    public void setWBX_PROV_STR(String WBX_PROV_STR) {
        this.WBX_PROV_STR = WBX_PROV_STR;
    }

    public String getCALL_BACK_STR() {
        return CALL_BACK_STR;
    }

    public void setCALL_BACK_STR(String CALL_BACK_STR) {
        this.CALL_BACK_STR = CALL_BACK_STR;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getCALLBACKTIME() {
        return CALLBACKTIME;
    }

    public void setCALLBACKTIME(String CALLBACKTIME) {
        this.CALLBACKTIME = CALLBACKTIME;
    }

    public String getSITE_ID() {
        return SITE_ID;
    }

    public void setSITE_ID(String SITE_ID) {
        this.SITE_ID = SITE_ID;
    }

    public String getHOST_EMAIL() {
        return HOST_EMAIL;
    }

    public void setHOST_EMAIL(String HOST_EMAIL) {
        this.HOST_EMAIL = HOST_EMAIL;
    }

    public String getCMD_RESULT() {
        return CMD_RESULT;
    }

    public void setCMD_RESULT(String CMD_RESULT) {
        this.CMD_RESULT = CMD_RESULT;
    }

	public String getDISPLAY_NAME() {
		return DISPLAY_NAME;
	}

	public void setDISPLAY_NAME(String dISPLAY_NAME) {
		DISPLAY_NAME = dISPLAY_NAME;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


    
}
