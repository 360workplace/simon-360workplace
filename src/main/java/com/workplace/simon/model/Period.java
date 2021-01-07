package com.workplace.simon.model;

import javax.persistence.*;
import java.sql.Time;
import java.time.Month;
import java.time.Year;

@Entity
@Table(name = "periodo_semanal")
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Time startDate;

    private Time endDate;

    private Integer weekNo;

    private Month month;

    private Integer year;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getStartDate() {
        return startDate;
    }

    public void setStartDate(Time startDate) {
        this.startDate = startDate;
    }

    public Time getEndDate() {
        return endDate;
    }

    public void setEndDate(Time endDate) {
        this.endDate = endDate;
    }

    public Integer getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(Integer weekNo) {
        this.weekNo = weekNo;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
