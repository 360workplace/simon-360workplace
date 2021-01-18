package com.workplace.simon.controller;

import com.workplace.simon.model.Role;
import com.workplace.simon.model.User;
import com.workplace.simon.service.*;
import com.workplace.simon.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private KeepSessionService keepSessionService;

    public UserService getUserService() {
        return userService;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public UserValidator getUserValidator() {
        return userValidator;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public AreaService getAreaService() {
        return areaService;
    }

    public KeepSessionService getKeepSessionService() {
        return keepSessionService;
    }

    @GetMapping("/admin/registration")
    public String registration(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        this.getKeepSessionService().setCurrentUser(currentUser, model);

        model.addAttribute("userForm", new User());
        model.addAttribute("allRoles", this.getRoleService().findAll());
        model.addAttribute("allAreas", this.getAreaService().findAll());

        return getSignUpForm(model);
    }

    @PostMapping("/admin/registration")
    public String registration(
            @ModelAttribute("userForm") User userForm,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal UserDetails currentUser
    ) {
        this.getKeepSessionService().setCurrentUser(currentUser, model);
        this.getUserValidator().validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return getSignUpForm(model);
        }

        for (Role role : userForm.getRoles()) {
            System.out.println(role.getName());
        }

        //this.getUserService().save(userForm);

        return "redirect:/dashboard";
    }

    private String getSignUpForm(Model model) {
        model.addAttribute("allUsers", this.getUserService().findAll());

        return "registration";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (this.getSecurityService().isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "login";
    }

    @GetMapping({"/", "/dashboard", "/admin"})
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        this.getKeepSessionService().setCurrentUser(currentUser, model);

        return "dashboard";
    }

    @GetMapping("/admin/users/list")
    public String showUsers(
            @AuthenticationPrincipal UserDetails currentUser,
            Model model
    ) {
        this.getKeepSessionService().setCurrentUser(currentUser, model);

        model.addAttribute("users", this.getUserService().findAll());

        return "users-list";
    }

    @GetMapping("/admin/user/update/{userId}")
    public String updateUser(
            @AuthenticationPrincipal UserDetails currentUser,
            @PathVariable("userId") Long userId,
            Model model
    ) {
        this.getKeepSessionService().setCurrentUser(currentUser, model);
        User user = this.getUserService().findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User id is not valid " + userId));

        model.addAttribute("userForm", user);
        model.addAttribute("allRoles", this.getRoleService().findAll());
        model.addAttribute("allAreas", this.getAreaService().findAll());

        return "registration";
    }
}
