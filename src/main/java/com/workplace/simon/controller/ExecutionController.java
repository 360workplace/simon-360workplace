package com.workplace.simon.controller;

import com.workplace.simon.model.BaseLine;
import com.workplace.simon.model.Execution;
import com.workplace.simon.model.Policy;
import com.workplace.simon.model.User;
import com.workplace.simon.repository.UserRepository;
import com.workplace.simon.service.BaseLineService;
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
@RequestMapping("process")
public class ExecutionController {
    @Autowired
    private ExecutionService executionService;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BaseLineService baseLineService;

    public ExecutionService getExecutionService() {
        return executionService;
    }

    public PolicyService getPolicyService() {
        return policyService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public BaseLineService getBaseLineService() {
        return baseLineService;
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
            @PathVariable("sourceLabel") String sourceLabel,
            @PathVariable("sourceId") Long sourceId,
            @PathVariable("userId") Long userId,
            Model model) {
        Execution execution = new Execution();
        model.addAttribute("execution", execution);
        model.addAttribute("allUsers", this.getUserRepository().findAll());

        // TODO - Get from source and the id the correct table and the values to the correct table.
        execution.setCodeFrom(sourceLabel);
        BaseLine baseLine = this.getBaseLineService().findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("The source is not defined in any table."));
        model.addAttribute("source", baseLine);

        User userSource = this.getUserRepository().findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("The userId can't gets any register."));

        User userSupervisor;
        if (baseLine.getSupervisor() != null) {
            userSupervisor = this.getUserRepository().findById(baseLine.getSupervisor())
                    .orElse(new User());
        } else {
            userSupervisor = new User();
        }

        execution.setTitle(baseLine.getTitle());
        execution.setDetail(baseLine.getDetail());
        execution.setSource(userSource.getId());
        execution.setSupervisor(userSupervisor);

        return "execution-creation-form";
    }

    @GetMapping("policy/creation/{sourceLabel}/{sourceId}/{userId}")
    public String processPolicy(
            @PathVariable("sourceLabel") String sourceLabel,
            @PathVariable("sourceId") Long sourceId,
            @PathVariable("userId") Long userId,
            Model model) {
        Policy policy = new Policy();
        policy.setCodeFrom(sourceLabel);
        model.addAttribute("policy", policy);
        model.addAttribute("sourceId", sourceId);

        User userSource = this.getUserRepository().findById(userId)
                .orElseThrow(() -> new  IllegalArgumentException("The user id is not valid " + userId));
        policy.setUserSource(userSource);

        // TODO - It is necessary select the correct source in order to select the correct database to save data.
        BaseLine source = this.getBaseLineService().findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("The source id is not valid " + sourceId));
        model.addAttribute("source", source);

        policy.setTitle(source.getTitle());
        policy.setDetail(source.getDetail());

        return "policy-creation-form";
    }

    @PostMapping("policy/add/{id}")
    public String addPolicy(
            @PathVariable("id") Long sourceId,
            @Valid Policy policy,
            BindingResult bindingResult,
            Model model) {
        // TODO - It is necessary select the correct source in order to select the correct database to save data.
        BaseLine source = this.getBaseLineService().findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("The source id is not valid " + sourceId));
        model.addAttribute("source", source);

        if (bindingResult.hasErrors()) {
            return "policy-creation-form";
        }

        this.getPolicyService().save(policy);

        // TODO - needs the change it is necessary to select correct database.
        source.setActive(false);
        this.getBaseLineService().save(source);

        return "redirect:/data/baseline/list";
    }
}
