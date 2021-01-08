package com.workplace.simon.controller;

import com.workplace.simon.model.*;
import com.workplace.simon.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeReportController {
    @Autowired
    private ExecutionService executionService;

    @Autowired
    private UserService userService;

    @Autowired
    private WeeklyOperatingReportService weeklyOperatingReportService;

    @Autowired
    private UtilDateService utilDate;

    public ExecutionService getExecutionService() {
        return executionService;
    }

    public UserService getUserService() {
        return userService;
    }

    public WeeklyOperatingReportService getWeeklyOperatingReportService() {
        return weeklyOperatingReportService;
    }

    public UtilDate getUtilDate() {
        return utilDate;
    }

    @GetMapping("week/report")
    public String showActiveAssign(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        User user = setCurrentUser(userDetails, model);
        model.addAttribute("executions", this.getExecutionService().findBySourceAndStatusOrderByPriorityDesc(
                user.getId(),
                AssignationStatus.OPEN.getLabel()
        ));

        return "employee-report-list";
    }

    private User setCurrentUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = this.getUserService().findByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);

        return currentUser;
    }

    @GetMapping("week/show/{executionId}")
    public String showAssignedActualExecution(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long executionId,
            Model model
    ) {
        setCurrentUser(userDetails, model);
        Execution execution = this.getExecutionService().findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("The execution id is not valid " + executionId));

        model.addAttribute("execution", execution);

        return "assigned-execution-show";
    }

    @GetMapping("week/report/{executionId}")
    public String makeWeeklyReport(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long executionId,
            Model model
    ) {
        User currentUser = setCurrentUser(userDetails, model);

        Execution execution = this.getExecutionService().findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("The [Execution] id is not valid " + executionId));
        WeeklyOperatingReport weeklyOperatingReport = this.getWeeklyOperatingReportService().findByExecution(execution)
                .orElse(null);

        if (weeklyOperatingReport == null) {
            weeklyOperatingReport = addDefaultValues(currentUser, execution);
        }

        WeekDetail weekDetail = new WeekDetail();
        weekDetail.setDate(new Date(System.currentTimeMillis()));
        weeklyOperatingReport.getWeekDetails().add(weekDetail);

        model.addAttribute("weeklyReport", weeklyOperatingReport);

        return "weekly-operating-report-creation";
    }

    private WeeklyOperatingReport addDefaultValues(User user, Execution execution) {
        WeeklyOperatingReport weeklyOperatingReport = new WeeklyOperatingReport();
        weeklyOperatingReport.setMaker(user);
        weeklyOperatingReport.setPeriod(getPeriod());
        weeklyOperatingReport.setExecution(execution);

        return weeklyOperatingReport;
    }

    private Period getPeriod() {
        Period period = new Period();

        List<java.util.Date> currentWeek = this.getUtilDate().getStartAndEndDate();

        period.setStartDate(java.sql.Date.valueOf(String.valueOf(currentWeek.get(0))));
        period.setEndDate(java.sql.Date.valueOf(String.valueOf(currentWeek.get(1))));

        return period;
    }
}
