package com.kt.repo.mongo.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

import javax.persistence.Id;

/**
 * Created by Garfield Chen on 2016/9/28.
 */
@Document(collection = "contract_pdf")
public class ContractPdfBean {
	@Id
    @Field(value = "id")
    private String id;
    private String contractId;
    private byte[] pdfContent;
    private Date createdAt;
   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   

    public byte[] getPdfContent() {
        return pdfContent;
    }

    public void setPdfContent(byte[] pdfContent) {
        this.pdfContent = pdfContent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

   
}
