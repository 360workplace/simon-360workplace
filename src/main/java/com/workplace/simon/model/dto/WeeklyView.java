package com.workplace.simon.model.dto;

import com.workplace.simon.model.Priority;

import java.sql.Date;

public class WeeklyView {
    public Long id;

    public String Sequential;

    public Date startDate;

    public Date endDate;

    public Date detailDate;

    public String detail;

    public String title;

    public String executionDetail;

    public Priority priority;

    public Date deadline;

    public String status;
}
