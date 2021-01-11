package com.workplace.simon.service;

import com.workplace.simon.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

public interface KeepSessionInterface {
    User setCurrentUser(@AuthenticationPrincipal UserDetails currentUser, Model model);
}
