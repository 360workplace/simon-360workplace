package com.workplace.simon.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "reunion")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "meeting_date")
    private Date meetingDate;

    @Column(name = "start_meeting")
    private String startMeeting;

    @Column(name = "end_meeting")
    private String endMeeting;

    @Column(name = "tern_meeting")
    private Integer ternMeeting;

    @OneToOne(mappedBy = "meeting")
    private ActRegister act;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getStartMeeting() {
        return startMeeting;
    }

    public void setStartMeeting(String startMeeting) {
        this.startMeeting = startMeeting;
    }

    public String getEndMeeting() {
        return endMeeting;
    }

    public void setEndMeeting(String endMeeting) {
        this.endMeeting = endMeeting;
    }

    public Integer getTernMeeting() {
        return ternMeeting;
    }

    public void setTernMeeting(Integer ternMeeting) {
        this.ternMeeting = ternMeeting;
    }

    public ActRegister getAct() {
        return act;
    }

    public void setAct(ActRegister act) {
        this.act = act;
    }
}
