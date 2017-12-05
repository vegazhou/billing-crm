package wang.huaichao.data.ds.details;

import wang.huaichao.data.entity.crm.MeetingRecord;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 9/8/2016.
 */
public class HostDetails {
    private String userName;
    private String hostName;
    private String siteName;
    private BigDecimal cost;
    private Map<Long, MeetingDetails> meetingDetailsMap = new LinkedHashMap<>();

    public void add(MeetingRecord record) {
        long confId = record.getConfId();

        MeetingDetails meetingDetails = meetingDetailsMap.get(confId);

        if (meetingDetails == null) {
            meetingDetails = new MeetingDetails();
            meetingDetails.setConfId(record.getConfId());
            meetingDetails.setConfName(record.getConfName());
            meetingDetailsMap.put(confId, meetingDetails);
        }

        meetingDetails.add(record);
    }

    public void calculate() {
        BigDecimal t = BigDecimal.ZERO;
        for (MeetingDetails meetingDetails : meetingDetailsMap.values()) {
            meetingDetails.calculate();
            t = t.add(meetingDetails.getCost());
        }
        cost = t;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Map<Long, MeetingDetails> getMeetingDetailsMap() {
        return meetingDetailsMap;
    }

    public void setMeetingDetailsMap(Map<Long, MeetingDetails> meetingDetailsMap) {
        this.meetingDetailsMap = meetingDetailsMap;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
