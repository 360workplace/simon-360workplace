package com.workplace.simon.service;

import com.workplace.simon.model.Execution;
import com.workplace.simon.repository.ExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExecutionService {
    @Autowired
    private ExecutionRepository executionRepository;

    public ExecutionRepository getExecutionRepository() {
        return executionRepository;
    }

    public Execution save(Execution execution) {
        return this.getExecutionRepository().save(execution);
    }

    public List<Execution> findAll() {
        return this.getExecutionRepository().findAll();
    }

    public Optional<Execution> findById(Long id) {
        return this.getExecutionRepository().findById(id);
    }

    public void delete(Execution execution) {
        this.getExecutionRepository().delete(execution);
    }

    public List<Execution> findBySourceAndStatus(Long userId, String status) {
        return this.getExecutionRepository().findBySourceAndStatus(userId, status);
    }

    public List<Execution> findByArea(Long areaId, String status) {
        return this.getExecutionRepository().findByArea(areaId, status);
    }
}
