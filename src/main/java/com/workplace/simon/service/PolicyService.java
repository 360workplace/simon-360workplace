package com.workplace.simon.service;

import com.workplace.simon.model.Policy;
import com.workplace.simon.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {
    @Autowired
    private PolicyRepository policyRepository;

    public PolicyRepository getPolicyRepository() {
        return policyRepository;
    }

    public Policy save(Policy policy) {
        return this.getPolicyRepository().save(policy);
    }

    public List<Policy> findAll() {
        return this.getPolicyRepository().findAll();
    }

    public Optional<Policy> findById(Long id) {
        return this.getPolicyRepository().findById(id);
    }

    public void delete(Policy policy) {
        this.getPolicyRepository().delete(policy);
    }
}
