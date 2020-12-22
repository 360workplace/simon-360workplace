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

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @GetMapping("baseline/{userId}")
    public String showBaselineForm(@PathVariable("userId") Long userId, Model model) {
        BaseLine baseLine = new BaseLine();
        baseLine.getResources().add(new BaseLineResource());
        baseLine.setSource(userId);
        Register currentUser = this.getRegisterService().findById(baseLine.getSource())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getSource()));

        model.addAttribute("baseLine", baseLine);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allUsers", this.getRegisterService().findAll());
        model.addAttribute("userid", userId);

        return "baseline-form";
    }

    @PostMapping(params = "save", path = {"add", "add/{userId}"})
    public String addBaseline(
            @PathVariable("userId") Long userId,
            @Valid BaseLine baseLine,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Register currentUser = this.getRegisterService().findById(baseLine.getSource())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getSource()));
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("allUsers", this.getRegisterService().findAll());
            model.addAttribute("userid", userId);

            return "baseline-form";
        }

        this.getBaseLineService().save(baseLine);

        return "redirect:/";
    }

    @RequestMapping(params = "addItem", path = {"add", "add/{userId}"})
    public String addRow(
            @PathVariable("userId") Long userId,
            BaseLine baseLine,
            HttpServletRequest request
    ) {
        baseLine.getResources().add(new BaseLineResource());

        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "baseline-form::#items";
        } else {
            return "baseline-form";
        }
    }

    @RequestMapping(params = "removeItem", path = {"add", "add/{userId}"})
    public String removeRow(
            @PathVariable("userId") Long userId,
            final BaseLine baseLine,
            @RequestParam("removeItem") int index,
            HttpServletRequest request
    ) {
        baseLine.getResources().remove(index);

        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "baseline-form::#items";
        } else {
            return "baseline-form";
        }
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
}
