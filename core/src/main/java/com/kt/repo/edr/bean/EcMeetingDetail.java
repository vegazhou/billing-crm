package com.kt.repo.edr.bean;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by jianf on 2016/7/13.
 */
public class EcMeetingDetail {
    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getPeakAttendee() {
        return peakAttendee;
    }

    public void setPeakAttendee(long peakAttendee) {
        this.peakAttendee = peakAttendee;
    }

    private int siteId;
    private String siteName;
    private long confId;
    private String confName;
    private Date startTime;
    private Date endTime;
    private long hostId;
    private String hostName;
    private String hostEmail;
    private long duration;
    private long peakAttendee;

}
