package com.workplace.simon.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "informe_ejecucion_semanal")
public class WeeklyOperatingReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sequential;

    @ManyToOne
    @JoinColumn(name = "ejecucion_id", referencedColumnName = "id")
    private Execution execution;

    @OneToOne
    private User maker;

    @OneToMany
    private Set<WeekDetail> weekDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSequential() {
        return sequential;
    }

    public void setSequential(String sequential) {
        this.sequential = sequential;
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    public User getMaker() {
        return maker;
    }

    public void setMaker(User maker) {
        this.maker = maker;
    }

    public Set<WeekDetail> getActual() {
        return actual;
    }

    public void setActual(Set<WeekDetail> actual) {
        this.actual = actual;
    }
}
