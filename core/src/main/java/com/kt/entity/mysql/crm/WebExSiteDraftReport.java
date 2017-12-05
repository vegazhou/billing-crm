package com.kt.entity.mysql.crm;



/**
 * Created by Garfield Chen on 2016/5/13.
 */

public class WebExSiteDraftReport {
   
	private String site_id;

	private String state;
  
    private String SITENAME;    

    private String PRODUCT_NAME;

    private String CUSTOMER_NAME;

	private String CONTRACT_ID;

    private String EFFECTIVESTARTDATE;    

    private String EFFECTIVEENDDATE;
    
    private String CONTRACT_NAME;
    
    private String RESELLER;
    
    private String CUSTOMER_ID;

	public String getSITENAME() {
		return SITENAME;
	}

	public void setSITENAME(String sITENAME) {
		SITENAME = sITENAME;
	}

	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}

	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}

	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}

	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}

	public String getEFFECTIVESTARTDATE() {
		return EFFECTIVESTARTDATE;
	}

	public void setEFFECTIVESTARTDATE(String eFFECTIVESTARTDATE) {
		EFFECTIVESTARTDATE = eFFECTIVESTARTDATE;
	}

	public String getEFFECTIVEENDDATE() {
		return EFFECTIVEENDDATE;
	}

	public void setEFFECTIVEENDDATE(String eFFECTIVEENDDATE) {
		EFFECTIVEENDDATE = eFFECTIVEENDDATE;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public String getCONTRACT_ID() {
		return CONTRACT_ID;
	}

	public void setCONTRACT_ID(String CONTRACT_ID) {
		this.CONTRACT_ID = CONTRACT_ID;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCONTRACT_NAME() {
		return CONTRACT_NAME;
	}

	public void setCONTRACT_NAME(String cONTRACT_NAME) {
		CONTRACT_NAME = cONTRACT_NAME;
	}

	public String getRESELLER() {
		return RESELLER;
	}

	public void setRESELLER(String rESELLER) {
		RESELLER = rESELLER;
	}

	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}

	public void setCUSTOMER_ID(String cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}
	
	
}
