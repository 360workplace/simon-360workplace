package com.workplace.simon.controller;

import com.workplace.simon.model.User;
import com.workplace.simon.service.SecurityService;
import com.workplace.simon.service.UserService;
import com.workplace.simon.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    public UserService getUserService() {
        return userService;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (this.getSecurityService().isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        this.getUserValidator().validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        this.getUserService().save(userForm);

        this.getSecurityService().autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (this.getSecurityService().isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "login";
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        return "dashboard";
    }
}
