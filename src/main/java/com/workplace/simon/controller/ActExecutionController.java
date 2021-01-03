package com.workplace.simon.controller;

import com.workplace.simon.model.*;
import com.workplace.simon.repository.UserRepository;
import com.workplace.simon.service.ActRegisterService;
import com.workplace.simon.service.ExecutionService;
import com.workplace.simon.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("register")
public class ActExecutionController {
    @Autowired
    private ExecutionService executionService;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private UserRepository userRepository;

    private ActRegisterService actRegisterService;

    public ExecutionService getExecutionService() {
        return executionService;
    }

    public PolicyService getPolicyService() {
        return policyService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public ActRegisterService getActRegisterService() {
        return actRegisterService;
    }

    /**
     * Controller to manage the convert to source to assigns.
     *
     * @param sourceLabel Label that is indicate the table from.
     * @param sourceId    This is the id from the table that will be gets the values. They can be BL, or ..
     * @param userId      The user that created the information of this execution assign.
     * @param model       Model view to interact with the front.
     * @return Name to the view.
     */
    @GetMapping("execution/creation/{sourceLabel}/{sourceId}/{userId}")
    public String processExecution(
            @PathVariable("sourceLabel") SourceType sourceLabel,
            @PathVariable("sourceId") Long sourceId,
            @PathVariable("userId") Long userId,
            Model model
    ) {
        Execution execution = new Execution();
        model.addAttribute("execution", execution);
        model.addAttribute("allUsers", this.getUserRepository().findAll());

        execution.setCodeFrom(sourceLabel);
        ActRegister source = this.getActRegisterService().findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("The source is not defined in any table."));
        model.addAttribute("source", source);
        model.addAttribute("sourceId", sourceId);

        User userSource = this.getUserRepository().findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("The userId can't gets any register."));

        execution.setSource(userSource.getId());

        return "execution-creation-form";
    }

    @PostMapping("execution/add/{id}")
    public String addExecution(
            @PathVariable("id") Long sourceId,
            @Valid Execution execution,
            BindingResult bindingResult,
            Model model
    ) {
        // TODO - It is necessary select the correct source in order to select the correct database to save data.
        ActRegister source = this.getActRegisterService().findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("The source id is not valid " + sourceId));
        model.addAttribute("source", source);

        if (bindingResult.hasErrors()) {
            model.addAttribute("sourceId", sourceId);
            model.addAttribute("allUsers", this.getUserRepository().findAll());

            return "execution-creation-form";
        }

        execution.setStatus(AssignationStatus.OPEN.getLabel());
        this.getExecutionService().save(execution);

        return "redirect:/act/source/list";
    }

    @GetMapping("policy/creation/{sourceLabel}/{sourceId}/{userId}")
    public String processPolicy(
            @PathVariable("sourceLabel") SourceType sourceLabel,
            @PathVariable("sourceId") Long sourceId,
            @PathVariable("userId") Long userId,
            Model model
    ) {
        Policy policy = new Policy();
        policy.setCodeFrom(sourceLabel);
        model.addAttribute("policy", policy);
        model.addAttribute("sourceId", sourceId);

        User userSource = this.getUserRepository().findById(userId)
                .orElseThrow(() -> new  IllegalArgumentException("The user id is not valid " + userId));
        policy.setUserSource(userSource.getId());

        ActRegister source = this.getActRegisterService().findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("The source id is not valid " + sourceId));
        model.addAttribute("source", source);

        return "policy-creation-form";
    }

    @PostMapping("policy/add/{id}")
    public String addPolicy(
            @PathVariable("id") Long sourceId,
            @Valid Policy policy,
            BindingResult bindingResult,
            Model model
    ) {
        ActRegister source = this.getActRegisterService().findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("The source id is not valid " + sourceId));
        model.addAttribute("source", source);
        model.addAttribute("sourceId", sourceId);

        if (bindingResult.hasErrors()) {
            return "policy-creation-form";
        }

        this.getPolicyService().save(policy);

        return "redirect:/act/source/list";
    }
}
