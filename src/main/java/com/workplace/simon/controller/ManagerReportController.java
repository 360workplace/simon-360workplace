package com.workplace.simon.controller;

import com.workplace.simon.model.dto.WeeklyView;
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

import java.util.List;
import java.util.Set;

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

    @GetMapping("week/report")
    public String printWeeklyReport(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        this.getKeepSessionService().setCurrentUser(userDetails, model);

        Set report = makeReport(this.getWeeklyOperatingReportService().getWeeklyReport());

        model.addAttribute("weeklyReport", report);
        model.addAttribute("currentPeriod", this.getUtilDate().getPeriod());

        return "manager-weekly-report";
    }

    private Set makeReport(List<WeeklyView> weeklyReport) {
        return null;
    }
}
