package com.workplace.simon.service;

import com.workplace.simon.model.Role;
import com.workplace.simon.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public Role save(Role role) {
        return this.getRoleRepository().save(role);
    }

    public List<Role> findAll() {
        return this.getRoleRepository().findAll();
    }

    public Optional<Role> findById(Long id) {
        return this.getRoleRepository().findById(id);
    }

    public void delete(Role role) {
        this.getRoleRepository().delete(role);
    }
}
