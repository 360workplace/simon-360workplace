package com.workplace.simon.controller;

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

import java.util.HashMap;
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

//        Set report = makeReport(this.getWeeklyOperatingReportService().getWeeklyReport());
        Period period = this.getUtilDate().getPeriod();

        model.addAttribute("weeklyReport", this.getWeeklyOperatingReportService().getWeeklyReport());
        model.addAttribute("currentPeriod", period);
        model.addAttribute("newsReport", this.getWeeklyNewsService().findByDateBetween(
                period.getStartDate(),
                period.getEndDate()
        ));

        return "manager-weekly-report";
    }

    private Set makeReport(List<Object[]> weeklyReport) {
        HashMap<Long, Set<Object[]>> result = new HashMap<Long, Set<Object[]>>();
        Long id = 0L;

        HashMap<String, Object[]> elements = new HashMap<>();
        for (Object[] item : weeklyReport) {
            if (!id.equals(Long.valueOf((String) item[0]))) {
                elements = new HashMap<>();
                id = Long.valueOf((String) item[0]);
                result.put(id, (Set<Object[]>) elements);

                elements.put("value", new Object[]{
                        item[1], item[2], item[3]
                });
            }


//            if (item[4] > item[2] && item[4].toString() <= item[3].toString()) {
//
//            }
        }

        return null;
    }
}
