package com.workplace.simon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @RequestMapping("/")
    public String getHome() {
        return "index";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
}
