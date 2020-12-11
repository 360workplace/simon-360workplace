package com.workplace.simon.service;

import com.workplace.simon.model.User;
import com.workplace.simon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public List<User> findAll() {
        return this.getUserRepository().findAll();
    }

    public Optional<User> findById(Long id) {
        return this.getUserRepository().findById(id);
    }
}
