package com.workplace.simon.service;

import com.workplace.simon.model.Execution;
import com.workplace.simon.model.WeekDetail;
import com.workplace.simon.model.WeeklyOperatingReport;
import com.workplace.simon.repository.WeekDetailRepository;
import com.workplace.simon.repository.WeeklyOperatingReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeeklyOperatingReportService {
    @Autowired
    private WeeklyOperatingReportRepository weeklyOperatingReportRepository;

    @Autowired
    private WeekDetailRepository weekDetailRepository;

    public WeeklyOperatingReportRepository getWeeklyOperatingReportRepository() {
        return weeklyOperatingReportRepository;
    }

    public WeekDetailRepository getWeekDetailRepository() {
        return weekDetailRepository;
    }

    public WeeklyOperatingReport save(WeeklyOperatingReport weeklyOperatingReport) {
        return this.getWeeklyOperatingReportRepository().save(weeklyOperatingReport);
    }

    public List<WeeklyOperatingReport> findAll() {
        return this.getWeeklyOperatingReportRepository().findAll();
    }

    public Optional<WeeklyOperatingReport> findById(Long id) {
        return this.getWeeklyOperatingReportRepository().findById(id);
    }

    public void delete(WeeklyOperatingReport weeklyOperatingReport) {
        this.getWeeklyOperatingReportRepository().delete(weeklyOperatingReport);
    }

    public Optional<WeeklyOperatingReport> findByExecution(Execution execution) {
        return this.getWeeklyOperatingReportRepository().findByExecution(execution);
    }

    public WeeklyOperatingReport persist(WeeklyOperatingReport weeklyOperatingReport) {
        List<WeekDetail> details = weeklyOperatingReport.getWeekDetails();

        WeeklyOperatingReport saved = this.save(weeklyOperatingReport);

        for (WeekDetail detail : details) {
            detail.setWeeklyOperatingReport(saved);
            this.getWeekDetailRepository().save(detail);
        }

        return saved;
    }
}
