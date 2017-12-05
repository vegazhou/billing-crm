package com.kt.entity.mysql.batch;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Table(name = "B_WBX_PROVISION_TASK")
@Entity
public class WbxProvTask {

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "CONTRACT_ID")
    private String contractId;

    @Column(name = "SITE_NAME")
    private String siteName;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "STATE")
    private String state;

    @Column(name = "WBX_PROV_STR")
    private String wbxProvStr;

    @Column(name = "CALL_BACK_STR")
    private String callBackStr;

    @Column(name = "CREATETIME")
    private Date createTime;

    @Column(name = "CALLBACKTIME")
    private Date callBackTime;

    @Column(name = "SITE_ID")
    private String siteId;

    @Column(name = "HOST_EMAIL")
    private String hostEmail;

    @Column(name = "CMD_RESULT")
    private String cmdResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWbxProvStr() {
        return wbxProvStr;
    }

    public void setWbxProvStr(String wbxProvStr) {
        this.wbxProvStr = wbxProvStr;
    }

    public String getCallBackStr() {
        return callBackStr;
    }

    public void setCallBackStr(String callBackStr) {
        this.callBackStr = callBackStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCallBackTime() {
        return callBackTime;
    }

    public void setCallBackTime(Date callBackTime) {
        this.callBackTime = callBackTime;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getCmdResult() {
        return cmdResult;
    }

    public void setCmdResult(String cmdResult) {
        this.cmdResult = cmdResult;
    }
}
