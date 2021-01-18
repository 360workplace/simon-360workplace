package com.workplace.simon.service;

import com.workplace.simon.model.User;
import com.workplace.simon.repository.RoleRepository;
import com.workplace.simon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface, FindUserService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public List<User> findAll() {
        return this.getUserRepository().findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.getUserRepository().findById(id);
    }

    @Override
    public void save(User user) {
        user.setPassword(this.getPasswordEncoder().encode(user.getPassword()));
        user.setRoles(new HashSet<>(user.getRoles()));
        this.getUserRepository().save(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.getUserRepository().findByUsername(username);
    }
}
