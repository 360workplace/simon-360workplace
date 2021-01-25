package com.workplace.simon.controller;

import com.workplace.simon.model.*;
import com.workplace.simon.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/assign/")
public class AssignController {
    @Autowired
    private UserService userService;

    @Autowired
    private SourceService sourceService;

    @Autowired
    private ExecutionService executionService;

    @Autowired
    private KeepSessionService keepSessionService;

    public UserService getUserService() {
        return userService;
    }

    public SourceService getSourceService() {
        return sourceService;
    }

    public ExecutionService getExecutionService() {
        return executionService;
    }

    public KeepSessionService getKeepSessionService() {
        return keepSessionService;
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
        source.setType(SourceType.ASSIGN_REQUEST);

        model.addAttribute("assign", source);
        model.addAttribute("currentUser", user);
        model.addAttribute("allUsers", this.getUserService().findAll());
        model.addAttribute("userid", user.getId());

        return "assign-request-form";
    }

    @PostMapping(params = "save", path = "add")
    public String addBaseline(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid Source assign,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            User currentUser = this.getUserService().findByUsername(userDetails.getUsername());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("allUsers", this.getUserService().findAll());
            model.addAttribute("userid", currentUser.getId());

            return "assign-request-form";
        }

        this.getSourceService().save(assign);

        return "redirect:/";
    }

    @GetMapping("execution/creation")
    public String processManagerAssign(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        User currentUser = this.getKeepSessionService().setCurrentUser(userDetails, model);
        Execution execution = new Execution();
        model.addAttribute("execution", execution);
        model.addAttribute("allUsers", this.getUserService().findAll());

        execution.setCodeFrom(SourceType.MANAGER_ASSIGN);
        model.addAttribute("sourceId", currentUser.getId());

        execution.setSupervisor(currentUser.getId());

        return "execution-assignation-creation-form";
    }

    @PostMapping(params = "save", path = "execution/add")
    public String addExecution(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid Execution execution,
            BindingResult bindingResult,
            Model model
    ) {
        User currentUser = this.getKeepSessionService().setCurrentUser(userDetails, model);

        if (bindingResult.hasErrors()) {
            model.addAttribute("sourceId", currentUser.getId());
            model.addAttribute("allUsers", this.getUserService().findAll());

            return "execution-assignation-creation-form";
        }

        execution.setStatus(AssignationStatus.OPEN.getLabel());
        this.getExecutionService().save(execution);

        return "redirect:/";
    }

    @PostMapping(params = "addItem", path = {"execution/add", "execution/add/{id}"})
    public String addResource(
            @AuthenticationPrincipal UserDetails userDetails,
            Execution execution,
            HttpServletRequest request,
            Model model
    ) {
        User currentUser = this.getKeepSessionService().setCurrentUser(userDetails, model);
        execution.getResourceExecutions().add(new ResourceExecution());
        model.addAttribute("sourceId", currentUser.getId());
        model.addAttribute("allUsers", this.getUserService().findAll());

        if (AjaxRequest.AJAX_HEADER_VALUE.equals(request.getHeader(AjaxRequest.AJAX_HEADER_NAME))) {
            return "execution-assignation-creation-form::#resources";
        } else {
            return "execution-assignation-creation-form";
        }
    }

    @PostMapping(params = "removeItem", path = {"execution/add", "execution/add/{id}"})
    public String removeResource(
            @AuthenticationPrincipal UserDetails userDetails,
            Execution execution,
            @RequestParam("removeItem") int index,
            HttpServletRequest request,
            Model model
    ) {
        User currentUser = this.getKeepSessionService().setCurrentUser(userDetails, model);
        execution.getResourceExecutions().remove(index);
        model.addAttribute("sourceId", currentUser.getId());
        model.addAttribute("allUsers", this.getUserService().findAll());

        if (AjaxRequest.AJAX_HEADER_VALUE.equals(request.getHeader(AjaxRequest.AJAX_HEADER_NAME))) {
            return "execution-assignation-creation-form::#resources";
        } else {
            return "execution-assignation-creation-form";
        }
    }
}
