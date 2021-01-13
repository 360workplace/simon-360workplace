package com.workplace.simon.service;

import com.workplace.simon.model.AssignationStatus;
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

    public List<Execution> findByStatusNot(AssignationStatus status) {
        return this.getExecutionRepository().findByStatusNot(status.getLabel());
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

    public List<Execution> findBySourceAndStatusOrderByPriorityDesc(Long userId, String status) {
        return this.getExecutionRepository().findBySourceAndStatusOrderByPriorityDesc(userId, status);
    }

    public List<Execution> findBySupervisorAndStatus(Long supervisorId, String status) {
        return this.getExecutionRepository().findBySupervisorAndStatus(supervisorId, status);
    }

    public List<Execution> findByAreaAndStatus(Long areaId, String status) {
        return this.getExecutionRepository().findByAreaAndStatus(areaId, status);
    }

    public Execution updateExecutionStatus(Long executionId, Execution execution, AssignationStatus status) {
        execution.setStatus(status.getLabel());
        execution.setId(executionId);

        return this.getExecutionRepository().save(execution);
    }

    public List<Execution>  findByAreaAndStatusNot(Long areaId, String label) {
        return this.getExecutionRepository().findByAreaAndStatusNot(areaId, label);
    }
}
