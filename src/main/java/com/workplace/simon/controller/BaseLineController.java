package com.workplace.simon.controller;

import com.workplace.simon.model.BaseSource;
import com.workplace.simon.model.Resource;
import com.workplace.simon.model.SourceType;
import com.workplace.simon.model.User;
import com.workplace.simon.service.SourceService;
import com.workplace.simon.service.RegisterService;
import com.workplace.simon.service.UserService;
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
    private SourceService sourceService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserService userService;

    public SourceService getSourceService() {
        return sourceService;
    }

    @Deprecated
    public RegisterService getRegisterService() {
        return registerService;
    }

    public UserService getUserService() {
        return userService;
    }

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @GetMapping("baseline/{userId}")
    public String showBaselineForm(
            @PathVariable("userId") Long userId,
            Model model
    ) {
        BaseSource baseLine = new BaseSource();
        baseLine.getResources().add(new Resource());
        baseLine.setType(SourceType.BASE_LINE);
        baseLine.setUserId(userId);
        User currentUser = this.getUserService().findById(baseLine.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getUserId()));

        model.addAttribute("baseLine", baseLine);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allUsers", this.getUserService().findAll());
        model.addAttribute("userid", userId);

        return "baseline-form";
    }

    @PostMapping(params = "save", path = {"add", "add/{userId}"})
    public String addBaseline(
            @PathVariable("userId") Long userId,
            @Valid BaseSource baseLine,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            User currentUser = this.getUserService().findById(baseLine.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getUserId()));
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("allUsers", this.getUserService().findAll());
            model.addAttribute("userid", userId);

            return "baseline-form";
        }

        this.getSourceService().save(baseLine);

        return "redirect:/";
    }

    @RequestMapping(params = "addItem", path = {"add", "add/{userId}"})
    public String addRow(
            @PathVariable("userId") Long userId,
            BaseSource baseLine,
            HttpServletRequest request
    ) {
        baseLine.getResources().add(new Resource());

        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "baseline-form::#items";
        } else {
            return "baseline-form";
        }
    }

    @RequestMapping(params = "removeItem", path = {"add", "add/{userId}"})
    public String removeRow(
            @PathVariable("userId") Long userId,
            final BaseSource baseLine,
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
            @Valid BaseSource baseLine,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            baseLine.setId(id);
            User currentUser = this.getUserService().findById(baseLine.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getUserId()));
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("allUsers", this.getUserService().findAll());

            return "baseline-form";
        }

        this.getSourceService().save(baseLine);

        return "redirect:/data/source/list";
    }

    @GetMapping("source/list")
    public String students(Model model) {
        model.addAttribute("baseLine", this.getSourceService().findAll());

        return "baseline-list";
    }

    @GetMapping("baseline/show/{baseLineId}")
    public String showItem(
            @PathVariable("baseLineId") Long baseLineId,
            Model model
    ) {
        BaseSource baseLine = this.getSourceService().findById(baseLineId)
                .orElseThrow(() -> new IllegalArgumentException("The id to gets the baseline record is not exists."));
        User currentUser = this.getUserService().findById(baseLine.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getUserId()));

        model.addAttribute("baseLine", baseLine);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allUsers", this.getUserService().findAll());

        return "baseline-show";
    }

    @GetMapping("baseline/trunk/{baseLineId}")
    public String moveToTrunk(
            @PathVariable("baseLineId") Long baseLineId,
            Model model
    ) {
        BaseSource baseLine = this.getSourceService().findById(baseLineId)
                .orElseThrow(() -> new IllegalArgumentException("The id to gets the baseline record is not exists."));
        baseLine.setActive(false);
        this.getSourceService().save(baseLine);

        return "redirect:/data/source/list";
    }
}
