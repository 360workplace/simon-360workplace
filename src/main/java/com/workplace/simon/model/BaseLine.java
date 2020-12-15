package com.workplace.simon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "Linea_Base")
public class BaseLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fuente")
    private Long source;

    @Column(name = "titulo")
    @NotBlank(message = "The Title is mandatory")
    private String title;

    @Column(name = "detalle")
    private String detail;

    @Column(name = "responsable")
    private Long supervisor;

    @Column(name = "fecha_inicial")
    @NotBlank(message = "The start date is mandatory")
    private Date startDate;

    @OneToMany(mappedBy="baseLine")
    private Set<BaseLineResource> resources;

    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Long supervisor) {
        this.supervisor = supervisor;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = Date.valueOf(startDate);
    }

    public Set<BaseLineResource> getResources() {
        return resources;
    }

    public void setResources(Set<BaseLineResource> resources) {
        this.resources = resources;
    }
}
