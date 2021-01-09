package com.workplace.simon.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ejecucion_semanal")
public class WeeklyOperatingReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sequential;

    @OneToOne(cascade = CascadeType.ALL)
    private Period period;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejecucion_id", referencedColumnName = "id")
    private Execution execution;

    @OneToMany(mappedBy = "weeklyOperatingReport")
    private List<WeekDetail> weekDetails = new ArrayList<>();

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

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    public List<WeekDetail> getWeekDetails() {
        return weekDetails;
    }

    public void setWeekDetails(List<WeekDetail> weekDetails) {
        this.weekDetails = weekDetails;
    }
}
