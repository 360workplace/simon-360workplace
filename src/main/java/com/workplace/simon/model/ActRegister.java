package com.workplace.simon.model;

import javax.persistence.*;

@Entity
@Table(name = "act_source")
public class ActRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private SourceType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_record_id", referencedColumnName = "id")
    private FileDB fileRecord;

    @Column(columnDefinition = "text")
    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
    private Meeting meeting;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "participants_id", referencedColumnName = "id")
    private Participant participants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
    }

    public FileDB getFileRecord() {
        return fileRecord;
    }

    public void setFileRecord(FileDB fileRecord) {
        this.fileRecord = fileRecord;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public Participant getParticipants() {
        return participants;
    }

    public void setParticipants(Participant participants) {
        this.participants = participants;
    }
}
