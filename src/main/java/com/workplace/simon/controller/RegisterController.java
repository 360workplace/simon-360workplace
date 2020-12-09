package com.workplace.simon.controller;

import com.workplace.simon.model.Register;
import com.workplace.simon.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register/")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    public RegisterService getRegisterService() {
        return registerService;
    }

    @GetMapping("signup")
    public String showSignupForm(Register register, Model model) {
        model.addAttribute("allUsers", this.getRegisterService().findAll());

        return "signup-form";
    }

    @PostMapping("add")
    public String addUser(@Valid Register register, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allUsers", this.getRegisterService().findAll());

            return "signup-form";
        }

        this.getRegisterService().save(register);

        return "index";
    }
}
