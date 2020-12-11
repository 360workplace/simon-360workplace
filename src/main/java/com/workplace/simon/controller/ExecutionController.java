package com.workplace.simon.controller;

import com.workplace.simon.model.Execution;
import com.workplace.simon.repository.UserRepository;
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

    public ExecutionService getExecutionService() {
        return executionService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @GetMapping("execution/creation/{source}/{id}")
    public String processExecution(@PathVariable String source, @PathVariable Long id, Model model) {
        model.addAttribute("execution", new Execution());

        return "execution-creation-form";
    }
}
