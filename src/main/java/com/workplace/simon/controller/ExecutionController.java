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

    /**
     * Controller to manage the convert to source to assigns.
     * @param source Label that is idicate the table from
     * @param id This is the id from the table that will be gets the values. They can be BL, or ..
     * @param model Model view to interact with the front.
     * @return Name to the view.
     */
    @GetMapping("execution/creation/{source}/{id}")
    public String processExecution(@PathVariable String source, @PathVariable Long id, Model model) {
        model.addAttribute("execution", new Execution());

        // TODO - Get from source and the id the correct table and the values to the correct table.


        return "execution-creation-form";
    }

    public String processPolicy(@PathVariable String source, @PathVariable Long id, Model model) {


        return "policy-creation-form";
    }
}
