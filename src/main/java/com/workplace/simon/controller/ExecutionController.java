package com.workplace.simon.controller;

import com.workplace.simon.model.BaseLine;
import com.workplace.simon.model.Execution;
import com.workplace.simon.model.Policy;
import com.workplace.simon.model.User;
import com.workplace.simon.repository.UserRepository;
import com.workplace.simon.service.BaseLineService;
import com.workplace.simon.service.ExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("process")
public class ExecutionController {
    @Autowired
    private ExecutionService executionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BaseLineService baseLineService;

    public ExecutionService getExecutionService() {
        return executionService;
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
            @PathVariable String sourceLabel,
            @PathVariable Long sourceId,
            @PathVariable Long userId,
            Model model) {
        Execution execution = new Execution();
        model.addAttribute("execution", execution);
        model.addAttribute("allUsers", this.getUserRepository().findAll());

        // TODO - Get from source and the id the correct table and the values to the correct table.
        execution.setCodeFrom("BL-001");
        BaseLine baseLine = this.getBaseLineService().findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("The source is not defined in any table."));
        User userSource = this.getUserRepository().findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("The userId can't gets any register."));
        User userSupervisor = this.getUserRepository().findById(baseLine.getSupervisor())
                .orElse(new User());

        execution.setTitle(baseLine.getTitle());
        execution.setDetail(baseLine.getDetail());
        execution.setSource(userSource.getId());
        execution.setSupervisor(userSupervisor);

        return "execution-creation-form";
    }

    @GetMapping("policy/creation/")
    public String processPolicy(
            @PathVariable String sourceLabel,
            @PathVariable Long sourceId,
            @PathVariable Long userId,
            Model model) {
        Policy policy = new Policy();
        model.addAttribute("policy", policy);

        return "policy-creation-form";
    }
}
