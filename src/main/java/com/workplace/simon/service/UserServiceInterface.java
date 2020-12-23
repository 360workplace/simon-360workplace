package com.workplace.simon.service;

import com.workplace.simon.model.User;

public interface UserServiceInterface {
    void save(User user);

    User findByUsername(String username);
}
