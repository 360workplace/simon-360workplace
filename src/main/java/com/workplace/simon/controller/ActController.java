package com.workplace.simon.controller;

import com.workplace.simon.model.Act;
import com.workplace.simon.model.Resource;
import com.workplace.simon.model.BaseSource;
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
@RequestMapping("/act/")
public class ActController {
    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    @GetMapping("management")
    public String showBaselineForm(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        Act act = new Act();
        User user = this.getUserService().findByUsername(userDetails.getUsername());

        model.addAttribute("act", act);
        model.addAttribute("currentUser", user);
        model.addAttribute("userid", user.getId());

        return "act-management-form";
    }
}
