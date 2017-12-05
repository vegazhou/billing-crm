package com.kt.entity.mysql.billing;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Garfield Chen on 2016/9/28.
 */
@Table(name = "BB_CONTRACT_PDF")
@Entity
public class ContractPdf {
    @Id    
    @Column(name = "PID")
    private String id;

    @Column(name = "PDF_NAME")
    private String pdfName;

    @Column(name = "CONTRACT_NUMBER")
    private String contractNumber;

    @Column(name = "CONTRACT_ID")
    private String contractId;
    
    @Column(name = "CREATE_TIME")
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

    
}
