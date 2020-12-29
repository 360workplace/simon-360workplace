package com.workplace.simon.controller;

import com.workplace.simon.model.Resource;
import com.workplace.simon.model.Source;
import com.workplace.simon.model.User;
import com.workplace.simon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/assign/")
public class AssignController {
    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    @GetMapping("request")
    public String showBaselineForm(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        Source source = new Source();
        source.getResources().add(new Resource());
        User user = this.getUserService().findByUsername(userDetails.getUsername());
        source.setUserId(user.getId());

        model.addAttribute("assign", source);
        model.addAttribute("currentUser", user);
        model.addAttribute("allUsers", this.getUserService().findAll());
        model.addAttribute("userid", user.getId());

        return "assign-request-form";
    }
}
