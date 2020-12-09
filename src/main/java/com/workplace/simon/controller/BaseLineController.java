package com.workplace.simon.controller;

import com.workplace.simon.model.BaseLine;
import com.workplace.simon.service.BaseLineService;
import com.workplace.simon.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/data/")
public class BaseLineController {
    @Autowired
    private BaseLineService baseLineService;

    @Autowired
    private RegisterService registerService;

    public BaseLineService getBaseLineService() {
        return baseLineService;
    }

    public RegisterService getRegisterService() {
        return registerService;
    }

    @GetMapping("baseline")
    public String showBaselineForm(@RequestParam("user") Optional<String> user, Model model) {
        return getBaselineForm(user, model);
    }

    @PostMapping("add")
    public String addBaseline(@RequestParam("user") Optional<String> user, @Valid BaseLine baseLine, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getBaselineForm(user, model);
        }

        this.getBaseLineService().save(baseLine);

        return "index";
    }

    private String getBaselineForm(@RequestParam("user") Optional<String> user, Model model) {
        String nickname = user.orElseGet(() -> "not provided");
        model.addAttribute("baseline", new BaseLine());
        model.addAttribute("allUsers", this.getRegisterService().findAll());

        return "baseline-form";
    }
}
