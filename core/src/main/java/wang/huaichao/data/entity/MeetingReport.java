package wang.huaichao.data.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 8/8/2016.
 */
@Entity
@Table(name = "XXRPT_HGSMEETINGREPORT")
public class MeetingReport {
    @Id
    private long confId;
    private String confKey;
    private String confName;
    private Date startTime;
    private Date endTime;
    private int duration;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id")
    List<CdrParticipant> participants = new ArrayList<>(0);

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

    public String getConfKey() {
        return confKey;
    }

    public void setConfKey(String confKey) {
        this.confKey = confKey;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<CdrParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<CdrParticipant> participants) {
        this.participants = participants;
    }
}
