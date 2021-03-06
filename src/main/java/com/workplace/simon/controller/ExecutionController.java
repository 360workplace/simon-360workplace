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
import java.util.Optional;

@Controller
@RequestMapping("process")
public class ExecutionController {
    @Autowired
    private ExecutionService executionService;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private UserService userService;

    @Autowired
    private SourceService sourceService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private KeepSessionService keepSessionService;

    public ExecutionService getExecutionService() {
        return executionService;
    }

    public PolicyService getPolicyService() {
        return policyService;
    }

    public UserService getUserService() {
        return userService;
    }

    public SourceService getSourceService() {
        return sourceService;
    }

    public AreaService getAreaService() {
        return areaService;
    }

    public KeepSessionService getKeepSessionService() {
        return keepSessionService;
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
        model.addAttribute("allUsers", this.getUserService().findAll());

        execution.setCodeFrom(sourceLabel);
        Source source = this.getSourceService().findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("The source is not defined in any table."));
        model.addAttribute("source", source);
        model.addAttribute("sourceId", sourceId);

        User userSource = this.getUserService().findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("The userId can't gets any register."));

        User userSupervisor = getUserSupervisor(source);

        execution.setTitle(source.getTitle());
        execution.setDetail(source.getDetail());
        execution.setSource(userSource.getId());
        execution.setSupervisor(userSupervisor.getId());

        return "execution-creation-form";
    }

    private User getUserSupervisor(Source source) {
        User userSupervisor;
        if (source.getUserSupervisor() != null) {
            userSupervisor = this.getUserService().findById(source.getUserSupervisor().getId())
                    .orElse(new User());
        } else {
            userSupervisor = new User();
        }

        return userSupervisor;
    }

    @PostMapping(params = "save", path = "execution/add/{id}")
    public String addExecution(
            @PathVariable("id") Long sourceId,
            @Valid Execution execution,
            BindingResult bindingResult,
            Model model
    ) {
        Source source = this.getSourceService().findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("The source id is not valid " + sourceId));
        model.addAttribute("source", source);

        if (bindingResult.hasErrors()) {
            model.addAttribute("sourceId", sourceId);
            model.addAttribute("allUsers", this.getUserService().findAll());

            return "execution-creation-form";
        }

        execution.setStatus(AssignationStatus.OPEN.getLabel());
        this.getExecutionService().save(execution);

        return "redirect:/data/source/list/" + source.getType();
    }

    @PostMapping(params = "addItem", path = "execution/add/{id}")
    public String addResource(
            @PathVariable("id") Long sourceId,
            Execution execution,
            HttpServletRequest request,
            Model model
    ) {
        execution.getResourceExecutions().add(new ResourceExecution());

        model.addAttribute("sourceId", sourceId);
        model.addAttribute("allUsers", this.getUserService().findAll());

        if (AjaxRequest.AJAX_HEADER_VALUE.equals(request.getHeader(AjaxRequest.AJAX_HEADER_NAME))) {
            return "execution-creation-form::#resources";
        } else {
            return "execution-creation-form";
        }
    }

    @PostMapping(params = "removeItem", path = "execution/add/{id}")
    public String removeResource(
            @PathVariable("id") Long sourceId,
            Execution execution,
            @RequestParam("removeItem") int index,
            HttpServletRequest request,
            Model model
    ) {
        execution.getResourceExecutions().remove(index);

        model.addAttribute("sourceId", sourceId);
        model.addAttribute("allUsers", this.getUserService().findAll());

        if (AjaxRequest.AJAX_HEADER_VALUE.equals(request.getHeader(AjaxRequest.AJAX_HEADER_NAME))) {
            return "execution-creation-form::#resources";
        } else {
            return "execution-creation-form";
        }
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

        User userSource = this.getUserService().findById(userId)
                .orElseThrow(() -> new  IllegalArgumentException("The user id is not valid " + userId));
        policy.setUserSource(userSource.getId());

        Source source = this.getSourceService().findById(sourceId)
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
            Model model
    ) {
        Source source = this.getSourceService().findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("The source id is not valid " + sourceId));
        model.addAttribute("source", source);
        model.addAttribute("sourceId", sourceId);

        if (bindingResult.hasErrors()) {
            return "policy-creation-form";
        }

        this.getPolicyService().save(policy);

        return "redirect:/data/source/list/" + source.getType();
    }

    @GetMapping("execution/list")
    public String showExecution(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("areaFilter") Optional<Long> area,
            Model model
    ) {
        this.getKeepSessionService().setCurrentUser(userDetails, model);
        Long areaId = area.orElse(0L);
        model.addAttribute("allAreas", this.getAreaService().findAll());

        if (areaId == 0) {
            model.addAttribute("executions", this.getExecutionService().findByStatusNot(AssignationStatus.CLOSED));
        } else {
            model.addAttribute("executions", this.getExecutionService().findByAreaAndStatusNot(
                    areaId,
                    AssignationStatus.CLOSED
            ));
        }

        return "execution-active-list";
    }
}
