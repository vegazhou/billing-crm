package com.kt.entity.mysql.crm;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Table(name = "B_ORDER")
@Entity
public class Order {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "PID")
    private String pid;

    @Column(name = "CONTRACT_ID")
    private String contractId;

    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "STATE")
    private String state;

    @Column(name = "PAY_INTERVAL")
    private int payInterval;

    @Column(name = "EFFECTIVESTARTDATE")
    private String effectiveStartDate;

    @Column(name = "EFFECTIVEENDDATE")
    private String effectiveEndDate;

    @Column(name = "BIZ_ID")
    private String bizId;

    @Column(name = "CHARGE_ID")
    private String chargeId;

    @Column(name = "FIRST_INSTALLMENT", scale = 2, precision = 2)
    private Double firstInstallment;

    @Column(name = "FIRST_INSTALLMENT_O", scale = 2, precision = 2)
    private Double sysFirstInstallment;

    @Column(name = "FI_TYPE")
    private int fiType;

    @Column(name = "TOTAL_AMOUNT", scale = 2, precision = 2)
    private Double totalAmount;

    @Column(name = "ORIGINAL_ORDER_ID")
    private String originalOrderId;

    @Column(name = "PLACED_DATE")
    private String placedDate;

    @Column(name = "PROVISION_STATE")
    private String provisionState;

    @Column(name = "TERMINATE_STATE")
    private String terminateState;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "BIZ_CHANCE")
    private String bizChance;



    @Transient
    private String contractName;
    
    @Transient
    private String siteName;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(String effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public String getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(String effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public int getPayInterval() {
        return payInterval;
    }

    public void setPayInterval(int payInterval) {
        this.payInterval = payInterval;
    }

    public Double getFirstInstallment() {
        return firstInstallment;
    }

    public void setFirstInstallment(Double firstInstallment) {
        this.firstInstallment = firstInstallment;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOriginalOrderId() {
        return originalOrderId;
    }

    public void setOriginalOrderId(String originalOrderId) {
        this.originalOrderId = originalOrderId;
    }

    public String getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(String placedDate) {
        this.placedDate = placedDate;
    }

    public String getProvisionState() {
        return provisionState;
    }

    public void setProvisionState(String provisionState) {
        this.provisionState = provisionState;
    }

    public String getTerminateState() {
        return terminateState;
    }

    public void setTerminateState(String terminateState) {
        this.terminateState = terminateState;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

    public Double getSysFirstInstallment() {
        return sysFirstInstallment;
    }

    public void setSysFirstInstallment(Double sysFirstInstallment) {
        this.sysFirstInstallment = sysFirstInstallment;
    }

    public int getFiType() {
        return fiType;
    }

    public void setFiType(int fiType) {
        this.fiType = fiType;
    }

    public String getBizChance() {
        return bizChance;
    }

    public void setBizChance(String bizChance) {
        this.bizChance = bizChance;
    }
}
