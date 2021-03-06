package com.workplace.simon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ejecucion")
public class Execution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated
    @Column(columnDefinition = "smallint", name = "code_from")
    private SourceType codeFrom;

    @Column(name = "source")
    private Long source;

    @Column(name = "title")
    @NotBlank(message = "title is mandatory")
    private String title;

    @Column(columnDefinition = "text")
    private String detail;

    @Column(name = "supervisor")
    private Long supervisor;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private Priority priority;

    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "status", length = 1)
    private String status;

    @OneToMany(mappedBy = "execution")
    private List<ResourceExecution> resourceExecutions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SourceType getCodeFrom() {
        return codeFrom;
    }

    public void setCodeFrom(SourceType codeFrom) {
        this.codeFrom = codeFrom;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResourceExecution> getResourceExecutions() {
        return resourceExecutions;
    }

    public void setResourceExecutions(List<ResourceExecution> resourceExecutions) {
        this.resourceExecutions = resourceExecutions;
    }
}
