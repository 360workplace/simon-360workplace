package com.workplace.simon.repository;

import com.workplace.simon.model.Execution;
import com.workplace.simon.model.WeeklyOperatingReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeeklyOperatingReportRepository extends JpaRepository<WeeklyOperatingReport, Long> {
    Optional<WeeklyOperatingReport> findByExecution(Execution execution);
}