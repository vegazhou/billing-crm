package com.kt.biz.bean;

import com.kt.biz.agent.RebateAlgorithm;
import com.kt.util.MathUtil;

import java.math.BigDecimal;

/**
 * Created by Vega Zhou on 2016/12/15.
 */
public class AgentRebateBean {

    private String orderId;

    private String contractId;

    private String productId;

    private String effectiveStartDate;

    private String effectiveEndDate;

    private int isRegistered;

    private float amount;

    private int feeType;

    private String accountPeriod;

    private String clearedDate;




    private String customerName;

    private String agentName;

    private String contractName;

    private String productName;

    private String site;

    private int unit;

    private double unitPrice;

    private double agentUnitPrice;

    private double totalPrice;

    private double agentTotalPrice;


    private boolean isPstnOrder;

    private double rebateWithRegister;

    private double rebateWithoutRegister;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(int isRegistered) {
        this.isRegistered = isRegistered;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getClearedDate() {
        return clearedDate;
    }

    public void setClearedDate(String clearedDate) {
        this.clearedDate = clearedDate;
    }



    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getAgentUnitPrice() {
        return agentUnitPrice;
    }

    public void setAgentUnitPrice(double agentUnitPrice) {
        this.agentUnitPrice = agentUnitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getAgentTotalPrice() {
        return agentTotalPrice;
    }

    public void setAgentTotalPrice(double agentTotalPrice) {
        this.agentTotalPrice = agentTotalPrice;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public boolean isPstnOrder() {
        return isPstnOrder;
    }

    public void setIsPstnOrder(boolean isPstnOrder) {
        this.isPstnOrder = isPstnOrder;
    }

    public double getRebateWithRegister() {
        return rebateWithRegister;
    }

    public void setRebateWithRegister(double rebateWithRegister) {
        this.rebateWithRegister = rebateWithRegister;
    }

    public double getRebateWithoutRegister() {
        return rebateWithoutRegister;
    }

    public void setRebateWithoutRegister(double rebateWithoutRegister) {
        this.rebateWithoutRegister = rebateWithoutRegister;
    }
}
