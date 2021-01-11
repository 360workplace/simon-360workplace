package com.workplace.simon.controller;

import com.workplace.simon.model.WeekDetail;
import com.workplace.simon.model.WeeklyNews;
import com.workplace.simon.model.WeeklyOperatingReport;
import com.workplace.simon.service.KeepSession;
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
    private WeeklyOperatingReport weeklyOperatingReport;

    @Autowired
    private WeekDetail weekDetail;

    @Autowired
    private WeeklyNews weeklyNews;

    @Autowired
    private KeepSession keepSession;

    public WeeklyOperatingReport getWeeklyOperatingReport() {
        return weeklyOperatingReport;
    }

    public WeekDetail getWeekDetail() {
        return weekDetail;
    }

    public WeeklyNews getWeeklyNews() {
        return weeklyNews;
    }

    public KeepSession getKeepSession() {
        return keepSession;
    }

    public String printWeeklyReport(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        this.getKeepSession().setCurrentUser(userDetails, model);

        return "manager-weekly-report";
    }
}
