package com.workplace.simon.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "detalle_semana")
public class WeekDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date date;

    @Column(columnDefinition = "text")
    private String detail;

    @ManyToOne
    @JoinColumn(name = "weekly_operating_report_id", referencedColumnName = "id", nullable = false)
    private WeeklyOperatingReport weeklyOperatingReport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public WeeklyOperatingReport getWeeklyOperatingReport() {
        return weeklyOperatingReport;
    }

    public void setWeeklyOperatingReport(WeeklyOperatingReport weeklyOperatingReport) {
        this.weeklyOperatingReport = weeklyOperatingReport;
    }
}
