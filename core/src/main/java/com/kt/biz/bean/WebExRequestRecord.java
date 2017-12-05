package com.kt.biz.bean;

/**
 * Created by Vega Zhou on 2016/6/7.
 */
public class WebExRequestRecord {
	
	private String ID;
	
    private String ORDER_ID;

    private String CONTRACT_ID;

    private String SITE_NAME;

    private String TYPE;

    private String STATE;

    private String WBX_PROV_STR;

    private String CALL_BACK_STR;

    private String CREATETIME;

    private String CALLBACKTIME;

    private String SITE_ID;

    private String HOST_EMAIL;

    private String CMD_RESULT;
    
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

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
    
    
}
