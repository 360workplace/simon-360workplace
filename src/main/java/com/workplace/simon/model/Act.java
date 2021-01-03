package com.workplace.simon.model;

import javax.persistence.*;

@Entity
@Table(name = "act_source")
public class Act extends BaseSource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private SourceType type;

    @OneToOne
    private FileDB fileRecord;

    @Column(columnDefinition = "text")
    private String content;

    @OneToOne
    private Meeting meeting;

    @OneToOne
    private Participant participants;

    @Override
    public Long getId() {
        return id;
    }

    @Override
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
