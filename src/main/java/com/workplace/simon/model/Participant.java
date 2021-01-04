package com.workplace.simon.model;

import javax.persistence.*;

@Entity
@Table(name = "participante")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer assistants;

    private Integer missing;

    private Integer unpunctual;

    @OneToOne(mappedBy = "participants")
    private ActRegister act;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAssistants() {
        return assistants;
    }

    public void setAssistants(Integer assistants) {
        this.assistants = assistants;
    }

    public Integer getMissing() {
        return missing;
    }

    public void setMissing(Integer missing) {
        this.missing = missing;
    }

    public Integer getUnpunctual() {
        return unpunctual;
    }

    public void setUnpunctual(Integer unpunctual) {
        this.unpunctual = unpunctual;
    }

    public ActRegister getAct() {
        return act;
    }

    public void setAct(ActRegister act) {
        this.act = act;
    }
}
