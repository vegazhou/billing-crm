package com.kt.entity.mysql.crm;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Table(name = "B_PRODUCT")
@Entity
public class Product {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "PID")
    private String pid;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "BIZ_ID")
    private String bizId;

    @Column(name = "CHARGE_SCHEME_ID")
    private String chargeSchemeId;

    @Column(name = "STATE")
    private String state;

    @Column(name = "IS_TRIAL")
    private int isTrial;

    @Column(name = "IS_AGENT")
    private int isAgent;

    @Column(name = "DIRECT_PRODUCT")
    private String directProduct;

    @Column(name = "AGENT_ID")
    private String agentId;

    @Transient
    private String chargeType;

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

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getChargeSchemeId() {
        return chargeSchemeId;
    }

    public void setChargeSchemeId(String chargeSchemeId) {
        this.chargeSchemeId = chargeSchemeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public int getIsTrial() {
        return isTrial;
    }

    public void setIsTrial(int isTrial) {
        this.isTrial = isTrial;
    }

    public int getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(int isAgent) {
        this.isAgent = isAgent;
    }

    public String getDirectProduct() {
        return directProduct;
    }

    public void setDirectProduct(String directProduct) {
        this.directProduct = directProduct;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
