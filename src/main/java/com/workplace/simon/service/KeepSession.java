package com.workplace.simon.service;

import com.workplace.simon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class KeepSession implements KeepSessionInterface {
    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    @Override
    public User setCurrentUser(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        User user = this.getUserService().findByUsername(currentUser.getUsername());
        model.addAttribute("currentUser", user);

        return user;
    }
}
