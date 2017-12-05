package com.kt.entity.mysql.billing;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * Created by Garfield  on 2016/8/25.
 */
@Table(name = "B_BILL_INVOICE")
@Entity
public class BillExport {

	@Id
	@GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "PID")
    private String pid;
	
    @Column(name = "INVOICE_NAME")
    private String invoiceName;

    @Column(name = "DOCUMENT_TYPE")
    private String documentType;

    @Column(name = "DOCUMENT_DATE")
    private String documentDate;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "SALES_CHANNEL")
    private String salesChannel;

    @Column(name = "CONTRACT_COMMENCE")
    private String contractCommence;

    @Column(name = "CONTRACT_TERM")
    private int contractTerm;
    
    @Column(name = "PAYMENT_INTERVAL")
    private int paymentInterval;
    
    @Column(name = "REVENUE_TYPE")
    private String revenueType;
    
    @Column(name = "PRODUCT_DESC")
    private String productDesc;
    
    @Column(name = "PRODUCT_TYPE")
    private String productType;
    
    
    @Column(name = "UNIT")
    private int unit;
    
    
    @Column(name = "LISTED_PRICE")
    private float listedPrice;
    
    @Column(name = "INVOICED_AMOUNT")
    private float invoicedAmount;
    
    
    @Column(name = "NET_INVOICED_AMOUNT")
    private float netInvoicedAmount;
    
    @Column(name = "BEFORE_TAX_VALUE",nullable=true)
    private float beforeTaxValue;
    
    @Column(name = "PAYMENT_INTERVAL_DB")
    private int paymentIntervalDB;
    
    @Column(name = "FEE_TYPE")
    private int feeType;
    
    @Column(name = "CREATE_DATE")
    private String createDate;
    
    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;
    
    @Column(name = "INVOICE_MEMO")
    private String invoiceMemo;
    
    @Column(name = "ORDERID")
    private String orderId;
    
    
    @Column(name = "CREDITTYPE")
    private String creditType;
    
    @Column(name = "CREDITAMOUNT")
    private float creditAmount;
    
    @Transient
    private String biggestdate;
    
    @Transient
    private String smallestdate;
    

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(String ducomentDate) {
		this.documentDate = ducomentDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public String getContractCommence() {
		return contractCommence;
	}

	public void setContractCommence(String contractCommence) {
		this.contractCommence = contractCommence;
	}

	public int getContractTerm() {
		return contractTerm;
	}

	public void setContractTerm(int contractTerm) {
		this.contractTerm = contractTerm;
	}

	public int getPaymentInterval() {
		return paymentInterval;
	}

	public void setPaymentInterval(int paymentInterval) {
		this.paymentInterval = paymentInterval;
	}

	public String getRevenueType() {
		return revenueType;
	}

	public void setRevenueType(String revenueType) {
		this.revenueType = revenueType;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public float getListedPrice() {
		return listedPrice;
	}

	public void setListedPrice(float listedPrice) {
		this.listedPrice = listedPrice;
	}

	public float getInvoicedAmount() {
		return invoicedAmount;
	}

	public void setInvoicedAmount(float invoicedAmount) {
		this.invoicedAmount = invoicedAmount;
	}

	public float getNetInvoicedAmount() {
		return netInvoicedAmount;
	}

	public void setNetInvoicedAmount(float netInvoicedAmount) {
		this.netInvoicedAmount = netInvoicedAmount;
	}

	public int getPaymentIntervalDB() {
		return paymentIntervalDB;
	}

	public void setPaymentIntervalDB(int paymentIntervalDB) {
		this.paymentIntervalDB = paymentIntervalDB;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public float getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(float creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getInvoiceMemo() {
		return invoiceMemo;
	}

	public void setInvoiceMemo(String invoiceMemo) {
		this.invoiceMemo = invoiceMemo;
	}

	public String getBiggestdate() {
		return biggestdate;
	}

	public void setBiggestdate(String biggestdate) {
		this.biggestdate = biggestdate;
	}

	public String getSmallestdate() {
		return smallestdate;
	}

	public void setSmallestdate(String smallestdate) {
		this.smallestdate = smallestdate;
	}

	public float getBeforeTaxValue() {
		return beforeTaxValue;
	}

	public void setBeforeTaxValue(float beforeTaxValue) {
		this.beforeTaxValue = beforeTaxValue;
	}

	public int getFeeType() {
		return feeType;
	}

	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}    
    
    



    
}
