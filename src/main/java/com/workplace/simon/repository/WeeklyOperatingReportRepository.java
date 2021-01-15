package com.workplace.simon.repository;

import com.workplace.simon.model.Execution;
import com.workplace.simon.model.WeeklyOperatingReport;
import com.workplace.simon.model.dto.WeeklyView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyOperatingReportRepository extends JpaRepository<WeeklyOperatingReport, Long> {
    Optional<WeeklyOperatingReport> findByExecution(Execution execution);

    @Query(value = "SELECT e.id as id, e.sequential as sequential, p.start_date as start_date, " +
            "p.end_date as end_date, d.date as detail_date, d.detail as detail, x.title as title, " +
            "x.detail as execution_detail, x.priority as priority, x.deadline as deadline, x.status as status " +
            "FROM ejecucion_semanal e " +
            "INNER JOIN ejecucion x ON e.ejecucion_id = x.id " +
            "INNER JOIN detalle_semana d ON d.weekly_operating_report_id = e.id " +
            "INNER JOIN periodo_semanal p ON d.period_id = p.id " +
            "WHERE x.status <> 'C' " +
            "ORDER BY e.ejecucion_id ASC, x.priority DESC", nativeQuery = true)
    List<WeeklyView> getWeeklyReport();
}
