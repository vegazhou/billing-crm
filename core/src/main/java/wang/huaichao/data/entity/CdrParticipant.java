package wang.huaichao.data.entity;


import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 8/2/2016.
 */
@Component
@Entity
@Table(name = "cdr_participants")
public class CdrParticipant {
    @Id
    @Column(name = "PARTICIPANT_ID")
    private int participantId;

    @Column(name = "conference_id")
    private String conferenceId;

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public String getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
    }
}
