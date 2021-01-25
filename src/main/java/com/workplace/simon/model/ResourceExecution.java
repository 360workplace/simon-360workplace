package com.workplace.simon.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "recurso_ejecucion")
public class ResourceExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "valor_monetario")
    private Double money;

    @Size(max = 1024, message = "The content is too long.")
    @Column(name = "descripcion_recurso", columnDefinition = "text")
    private String detail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User supervisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejecucion_id", referencedColumnName = "id")
    private Execution execution;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }
}
