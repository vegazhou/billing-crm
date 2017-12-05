package com.kt.repo.edr.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jianf on 2016/7/11.
 */
public class TelephoneBillDetail {
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public long getConfId() {
        return confId;
    }

    public void setConfId(long confId) {
        this.confId = confId;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCallingNum() {
        return callingNum;
    }

    public void setCallingNum(String callingNum) {
        this.callingNum = callingNum;
    }

    public String getCalledNum() {
        return calledNum;
    }

    public void setCalledNum(String calledNum) {
        this.calledNum = calledNum;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private String hostName;
    private long confId;
    private String confName;
    private Date startTime;
    private Date endTime;
    private String callingNum;
    private String calledNum;

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getOrigCountryCode() {
        return origCountryCode;
    }

    public void setOrigCountryCode(String origCountryCode) {
        this.origCountryCode = origCountryCode;
    }

    public String getOrigAreaCode() {
        return origAreaCode;
    }

    public void setOrigAreaCode(String origAreaCode) {
        this.origAreaCode = origAreaCode;
    }

    public String getDestCountryCode() {
        return destCountryCode;
    }

    public void setDestCountryCode(String destCountryCode) {
        this.destCountryCode = destCountryCode;
    }

    public String getDestAreaCode() {
        return destAreaCode;
    }

    public void setDestAreaCode(String destAreaCode) {
        this.destAreaCode = destAreaCode;
    }

    public String getAccessNumber() {
        return accessNumber;
    }

    public void setAccessNumber(String accessNumber) {
        this.accessNumber = accessNumber;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    private String sessionType;
    private int duration;

    private String callType;
    private String origCountryCode;
    private String origAreaCode;
    private String destCountryCode;
    private String destAreaCode;
    private String accessNumber;

    private BigDecimal fee;

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    private String rateCode;
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    private BigDecimal rate;


    public Date getPrivateTalkStarttime() {
        return privateTalkStarttime;
    }

    public void setPrivateTalkStarttime(Date privateTalkStarttime) {
        this.privateTalkStarttime = privateTalkStarttime;
    }

    public Date getPrivateTalkEndtime() {
        return privateTalkEndtime;
    }

    public void setPrivateTalkEndtime(Date privateTalkEndtime) {
        this.privateTalkEndtime = privateTalkEndtime;
    }

    private Date privateTalkStarttime;
    private Date privateTalkEndtime;

    public int getPrivateDuration() {
        return privateDuration;
    }

    public void setPrivateDuration(int privateDuration) {
        this.privateDuration = privateDuration;
    }

    private int privateDuration;

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    private int totalDuration;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    private String siteName;

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    private String hostEmail;
    private String meetingNumber;
    public String getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(String meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    public Date getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(Date meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    private Date meetingStartTime;

    public Date getMeetingEndTime() {
        return meetingEndTime;
    }

    public void setMeetingEndTime(Date meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }

    private Date meetingEndTime;
}
