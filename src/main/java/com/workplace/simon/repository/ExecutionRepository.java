package com.workplace.simon.repository;

import com.workplace.simon.model.Execution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {
    List<Execution> findBySourceAndStatus(Long userId, String status);
}
