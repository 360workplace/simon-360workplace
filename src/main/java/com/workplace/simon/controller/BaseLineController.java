package com.workplace.simon.controller;

import com.workplace.simon.model.BaseLine;
import com.workplace.simon.model.BaseLineResource;
import com.workplace.simon.model.Register;
import com.workplace.simon.service.BaseLineService;
import com.workplace.simon.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public String showBaselineForm(@RequestParam("user") Optional<Integer> userId, Model model) {
        BaseLine baseLine = new BaseLine();
        baseLine.setSource(Long.valueOf(userId.orElseGet(() -> 0)));
        Register currentUser = this.getRegisterService().findById(baseLine.getSource())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getSource()));

        model.addAttribute("baseLine", baseLine);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allUsers", this.getRegisterService().findAll());

        return "baseline-form";
    }

    @PostMapping("add")
    public String addBaseline(@Valid BaseLine baseLine, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Register currentUser = this.getRegisterService().findById(baseLine.getSource())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getSource()));
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("allUsers", this.getRegisterService().findAll());

            return "baseline-form";
        }

        this.getBaseLineService().save(baseLine);

        return "redirect:/";
    }

    @PostMapping("update/{id}")
    public String updateBaseline(
            @PathVariable("id") Long id,
            @Valid BaseLine baseLine,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            baseLine.setId(id);
            Register currentUser = this.getRegisterService().findById(baseLine.getSource())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getSource()));
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("allUsers", this.getRegisterService().findAll());

            return "baseline-form";
        }

        this.getBaseLineService().save(baseLine);

        return "redirect:/data/baseline/list";
    }

    @GetMapping("baseline/list")
    public String students(Model model) {
        model.addAttribute("baseLine", this.getBaseLineService().findAll());

        return "baseline-list";
    }

    @GetMapping("baseline/show/{baseLineId}")
    public String showItem(
            @PathVariable("baseLineId") Long baseLineId,
            Model model
    ) {
        BaseLine baseLine = this.getBaseLineService().findById(baseLineId)
                .orElseThrow(() -> new IllegalArgumentException("The id to gets the baseline record is not exists."));
        Register currentUser = this.getRegisterService().findById(baseLine.getSource())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getSource()));

        model.addAttribute("baseLine", baseLine);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allUsers", this.getRegisterService().findAll());

        return "baseline-show";
    }

    @GetMapping("baseline/trunk/{baseLineId}")
    public String moveToTrunk(
            @PathVariable("baseLineId") Long baseLineId,
            Model model
    ) {
        BaseLine baseLine = this.getBaseLineService().findById(baseLineId)
                .orElseThrow(() -> new IllegalArgumentException("The id to gets the baseline record is not exists."));
        baseLine.setActive(false);
        this.getBaseLineService().save(baseLine);

        return "redirect:/data/baseline/list";
    }

    @RequestMapping(value = "/seedstartermng", params = {"addRow"})
    public String addRow(final BaseLine baseLine, final BindingResult bindingResult) {
        baseLine.getResources().add(new BaseLineResource());

        return "seedstartermng";
    }

    @RequestMapping(value = "/seedstartermng", params = {"removeRow"})
    public String removeRow(
            final BaseLine baseLine,
            final BindingResult bindingResult,
            final HttpServletRequest request
    ) {
        final Integer rowId = Integer.valueOf(request.getParameter("removeRow"));
        baseLine.getResources().remove(rowId.intValue());

        return "seedstartermng";
    }
}
