package com.workplace.simon.service;

import com.workplace.simon.model.User;

import java.util.List;
import java.util.Optional;

public interface FindUserService {
    List<User> findAll();

    Optional<User> findById(Long id);
}
