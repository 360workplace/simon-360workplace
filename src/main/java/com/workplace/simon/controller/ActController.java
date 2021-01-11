package com.workplace.simon.controller;

import com.workplace.simon.model.ActRegister;
import com.workplace.simon.model.FileDB;
import com.workplace.simon.model.User;
import com.workplace.simon.service.ActRegisterService;
import com.workplace.simon.service.AreaService;
import com.workplace.simon.service.FileStorageService;
import com.workplace.simon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
    private FileStorageService fileStorageService;

    @Autowired
    private AreaService areaService;

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

    public AreaService getAreaService() {
        return areaService;
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
            @RequestParam("file") MultipartFile file,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            setCurrentUser(userDetails, model);

            return "act-management-form";
        }

        preAction(file, actRegister);

        this.getActRegisterService().save(actRegister);

        return "redirect:/";
    }

    private void preAction(MultipartFile file, ActRegister actRegister) {
        try {
            actRegister.setFileRecord(this.getFileStorageService().store(file));
        } catch (Exception e) {
            getLogger().error("FileStore fail", e);
        }
    }

    @GetMapping("source/list")
    public String showActRegister(
            Model model
    ) {
        model.addAttribute("actRegister", this.getActRegisterService().findAll());
        model.addAttribute("allAreas", this.getAreaService().findAll());

        return "act-register-list";
    }

    private User setCurrentUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = this.getUserService().findByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);

        return currentUser;
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        FileDB fileDB = this.getFileStorageService().getFile(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + id));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }
}
