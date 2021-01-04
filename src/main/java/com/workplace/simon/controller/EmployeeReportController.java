package com.workplace.simon.controller;

import com.workplace.simon.model.AssignationStatus;
import com.workplace.simon.model.User;
import com.workplace.simon.service.ExecutionService;
import com.workplace.simon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("employee")
public class EmployeeReportController {
    @Autowired
    private ExecutionService executionService;

    @Autowired
    private UserService userService;

    public ExecutionService getExecutionService() {
        return executionService;
    }

    public UserService getUserService() {
        return userService;
    }

    @GetMapping("week/report")
    public String showActiveAssign(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        User user = setCurrentUser(userDetails, model);
        model.addAttribute("executions", this.getExecutionService().findBySourceAndStatus(
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
}
