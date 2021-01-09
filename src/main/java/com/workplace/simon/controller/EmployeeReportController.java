package com.workplace.simon.controller;

import com.workplace.simon.model.*;
import com.workplace.simon.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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
    private UtilDate utilDate;

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

        appendWeeklyDetail(weeklyOperatingReport);

        model.addAttribute("weeklyReport", weeklyOperatingReport);
        model.addAttribute("execution", execution);
        model.addAttribute("period", weeklyOperatingReport.getPeriod());
        model.addAttribute("supervisor", getSupervisor(execution));

        return "weekly-operating-report-creation";
    }

    private WeekDetail appendWeeklyDetail(WeeklyOperatingReport weeklyOperatingReport) {
        WeekDetail weekDetail = new WeekDetail();
        weekDetail.setDate(new Date(System.currentTimeMillis()));

        weeklyOperatingReport.getWeekDetails().add(
                weeklyOperatingReport.getWeekDetails().size(),
                weekDetail
        );
        weekDetail.setWeeklyOperatingReport(weeklyOperatingReport);

        return weekDetail;
    }

    private User getSupervisor(Execution execution) {
        return this.getUserService().findById(execution.getSupervisor())
                .orElseThrow(() -> new IllegalArgumentException("The supervisor defined has not any registered user"));
    }

    private WeeklyOperatingReport addDefaultValues(User user, Execution execution) {
        WeeklyOperatingReport weeklyOperatingReport = new WeeklyOperatingReport();
        weeklyOperatingReport.setPeriod(getPeriod());
        weeklyOperatingReport.setExecution(execution);

        return weeklyOperatingReport;
    }

    private Period getPeriod() {
        Period period = new Period();

        List<java.util.Date> currentWeek = this.getUtilDate().getStartAndEndDate();

        period.setStartDate(new Date(currentWeek.get(0).getTime()));
        period.setEndDate(new Date(currentWeek.get(1).getTime()));

        return period;
    }

    @PostMapping("week/report/add/{executionId}")
    public String addWeeklyReport(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long executionId,
            @Valid WeeklyOperatingReport weeklyOperatingReport,
            BindingResult bindingResult,
            Model model
    ) {
        setCurrentUser(userDetails, model);

        if (bindingResult.hasErrors()) {
            model.addAttribute("weeklyReport", weeklyOperatingReport);
            model.addAttribute("execution", weeklyOperatingReport.getExecution());
            model.addAttribute("period", weeklyOperatingReport.getPeriod());
            model.addAttribute("supervisor", getSupervisor(weeklyOperatingReport.getExecution()));

            return "weekly-operating-report-creation";
        }

        this.getWeeklyOperatingReportService().persist(weeklyOperatingReport);

        return "redirect:/employee/week/report";
    }
}
