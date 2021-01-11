package com.workplace.simon.controller;

import com.workplace.simon.model.WeekDetail;
import com.workplace.simon.service.KeepSessionService;
import com.workplace.simon.service.WeeklyNewsService;
import com.workplace.simon.service.WeeklyOperatingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/")
public class ManagerReportController {
    @Autowired
    private WeeklyOperatingReportService weeklyOperatingReportService;

    @Autowired
    private WeekDetail weekDetail;

    @Autowired
    private WeeklyNewsService weeklyNewsService;

    @Autowired
    private KeepSessionService keepSessionService;

    public WeeklyOperatingReportService getWeeklyOperatingReportService() {
        return weeklyOperatingReportService;
    }

    public WeekDetail getWeekDetail() {
        return weekDetail;
    }

    public WeeklyNewsService getWeeklyNewsService() {
        return weeklyNewsService;
    }

    public KeepSessionService getKeepSessionService() {
        return keepSessionService;
    }

    public String printWeeklyReport(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        this.getKeepSessionService().setCurrentUser(userDetails, model);

        return "manager-weekly-report";
    }
}
