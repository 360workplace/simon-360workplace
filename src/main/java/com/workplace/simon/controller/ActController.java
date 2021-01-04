package com.workplace.simon.controller;

import com.workplace.simon.model.ActRegister;
import com.workplace.simon.model.User;
import com.workplace.simon.service.ActRegisterService;
import com.workplace.simon.service.FileStorageService;
import com.workplace.simon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/act/")
public class ActController {
    @Autowired
    private UserService userService;

    @Autowired
    private ActRegisterService actRegisterService;

    @Autowired
    public FileStorageService fileStorageService;

    private static final Logger logger = LoggerFactory.getLogger(ActController.class);

    public UserService getUserService() {
        return userService;
    }

    public ActRegisterService getActRegisterService() {
        return actRegisterService;
    }

    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public static Logger getLogger() {
        return logger;
    }

    @GetMapping("management")
    public String showActRegisterForm(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        ActRegister actRegister = new ActRegister();
        User currentUser = setCurrentUser(userDetails, model);

        model.addAttribute("actRegister", actRegister);
        model.addAttribute("userid", currentUser.getId());

        return "act-management-form";
    }

    @PostMapping(params = "save", path = "add")
    public String addActRegister(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid ActRegister actRegister,
            @RequestParam("fileRecord") MultipartFile file,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            setCurrentUser(userDetails, model);

            return "act-management-form";
        }

        this.getActRegisterService().save(actRegister);

        postAction(file);

        return "redirect:/";
    }

    private void postAction(MultipartFile file) {
        try {
            this.getFileStorageService().store(file);
        } catch (Exception e) {
            getLogger().error("FileStore fail", e);
        }
    }

    @GetMapping("source/list")
    public String showActRegister(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        setCurrentUser(userDetails, model);
        model.addAttribute("actRegister", this.getActRegisterService().findAll());

        return "act-register-list";
    }

    private User setCurrentUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = this.getUserService().findByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);

        return currentUser;
    }
}
