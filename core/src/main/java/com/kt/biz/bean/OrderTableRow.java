package com.kt.biz.bean;

/**
 * Created by Vega Zhou on 2016/3/31.
 */
public class OrderTableRow {
	
    private String pid;
	
    private String productName;

    private String startTime;

    private String endTime;

    private String payInterval;

    private String firstInstallment;

    private String totalAmount;
    
    private String chargeId;

    private boolean ok;

    private boolean isFromOriginalContract;

    private String state;

    private String errorMessage;
    
    private String placedDate;

    private String contractName;

    private String contractId;
    
    private String siteName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPayInterval() {
        return payInterval;
    }

    public void setPayInterval(String payInterval) {
        this.payInterval = payInterval;
    }

    public String getFirstInstallment() {
        return firstInstallment;
    }

    public void setFirstInstallment(String firstInstallment) {
        this.firstInstallment = firstInstallment;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

    public boolean isFromOriginalContract() {
        return isFromOriginalContract;
    }

    public void setIsFromOriginalContract(boolean isFromOriginalContract) {
        this.isFromOriginalContract = isFromOriginalContract;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

	public String getPlacedDate() {
		return placedDate;
	}

	public void setPlacedDate(String placedDate) {
		this.placedDate = placedDate;
	}

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
    
    
}
