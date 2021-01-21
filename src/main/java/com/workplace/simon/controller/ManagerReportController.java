package com.workplace.simon.controller;

import com.workplace.simon.decorator.WeeklyReportAllStatusFormatter;
import com.workplace.simon.model.Period;
import com.workplace.simon.service.KeepSessionService;
import com.workplace.simon.service.UtilDate;
import com.workplace.simon.service.WeeklyNewsService;
import com.workplace.simon.service.WeeklyOperatingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/")
public class ManagerReportController {
    @Autowired
    private WeeklyOperatingReportService weeklyOperatingReportService;

    @Autowired
    private WeeklyNewsService weeklyNewsService;

    @Autowired
    private KeepSessionService keepSessionService;

    @Autowired
    private UtilDate utilDate;

    @Autowired
    private WeeklyReportAllStatusFormatter weeklyReportFormatter;

    public WeeklyOperatingReportService getWeeklyOperatingReportService() {
        return weeklyOperatingReportService;
    }

    public WeeklyNewsService getWeeklyNewsService() {
        return weeklyNewsService;
    }

    public KeepSessionService getKeepSessionService() {
        return keepSessionService;
    }

    public UtilDate getUtilDate() {
        return utilDate;
    }

    public WeeklyReportAllStatusFormatter getWeeklyReportFormatter() {
        return weeklyReportFormatter;
    }

    @GetMapping("week/report")
    public String printWeeklyReport(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        this.getKeepSessionService().setCurrentUser(userDetails, model);

        Period period = this.getUtilDate().getPeriod();

        model.addAttribute("weeklyReport", this.getWeeklyReportFormatter().format(
                this.getWeeklyOperatingReportService().getWeeklyReportAllStatus()
        ));
        model.addAttribute("currentPeriod", period);
        model.addAttribute("newsReport", this.getWeeklyNewsService().findByDateBetween(
                period.getStartDate(),
                period.getEndDate()
        ));

        return "manager-weekly-report";
    }
}
