package com.workplace.simon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "linea_base")
public class BaseSource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fuente")
    private Long userId;

    @Column(name = "titulo")
    @NotBlank(message = "The Title is mandatory")
    private String title;

    @Column(columnDefinition = "text")
    private String detail;

    @Column(name = "responsable")
    private Long userSupervisor;

    @Column(name = "fecha_inicial")
    private Date startDate;

    @OneToMany(mappedBy = "baseLine")
    private List<Resource> resources;

    @Column(name = "active")
    private Boolean active = true;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private SourceType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getUserSupervisor() {
        return userSupervisor;
    }

    public void setUserSupervisor(Long userSupervisor) {
        this.userSupervisor = userSupervisor;
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

    public List<Resource> getResources() {
        if (resources == null) {
            resources = new ArrayList<>();
        }

        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
    }
}
