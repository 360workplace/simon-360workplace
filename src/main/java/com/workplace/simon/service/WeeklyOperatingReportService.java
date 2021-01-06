package com.workplace.simon.service;

import com.workplace.simon.model.WeeklyOperatingReport;
import com.workplace.simon.repository.WeeklyOperatingReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeeklyOperatingReportService {
    @Autowired
    private WeeklyOperatingReportRepository weeklyOperatingReportRepository;

    public WeeklyOperatingReportRepository getWeeklyOperatingReportRepository() {
        return weeklyOperatingReportRepository;
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
}
