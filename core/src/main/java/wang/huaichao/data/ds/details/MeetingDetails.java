package wang.huaichao.data.ds.details;

import wang.huaichao.data.entity.crm.MeetingRecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 9/8/2016.
 */
public class MeetingDetails {
    private long confId;
    private String confName;
    private BigDecimal cost;
    private List<MeetingRecord> meetingRecords = new ArrayList<>();

    public void add(MeetingRecord record) {
        meetingRecords.add(record);
    }

    public void calculate() {
        BigDecimal t = BigDecimal.ZERO;
        for (MeetingRecord meetingRecord : meetingRecords) {
            t = t.add(meetingRecord.getCost());
        }
        cost = t;
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public List<MeetingRecord> getMeetingRecords() {
        return meetingRecords;
    }

    public void setMeetingRecords(List<MeetingRecord> meetingRecords) {
        this.meetingRecords = meetingRecords;
    }
}
