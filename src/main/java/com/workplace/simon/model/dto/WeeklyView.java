package com.workplace.simon.model.dto;

import com.workplace.simon.model.Priority;

import java.sql.Date;

public class WeeklyView {
    private Long id;

    private String Sequential;

    private Date startDate;

    private Date endDate;

    private Date detailDate;

    private String detail;

    private String title;

    private String executionDetail;

    private Priority priority;

    private Date deadline;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSequential() {
        return Sequential;
    }

    public void setSequential(String sequential) {
        Sequential = sequential;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDetailDate() {
        return detailDate;
    }

    public void setDetailDate(Date detailDate) {
        this.detailDate = detailDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExecutionDetail() {
        return executionDetail;
    }

    public void setExecutionDetail(String executionDetail) {
        this.executionDetail = executionDetail;
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
}
