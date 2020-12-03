package com.workplace.simon.controller;

import com.workplace.simon.dto.User;
import com.workplace.simon.model.Register;
import com.workplace.simon.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/register/")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    public RegisterService getRegisterService() {
        return registerService;
    }

    @GetMapping("signup")
    public String showSignupForm(WebRequest request, Model model) {
        User userDto = new User();
        model.addAttribute("user", userDto);

        return "signup-form";
    }

    public String addUser(Register register, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "signup-form";
        }

        this.getRegisterService().save(register);

        return "registered-user";
    }
}
