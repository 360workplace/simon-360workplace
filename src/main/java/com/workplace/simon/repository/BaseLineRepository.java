package com.workplace.simon.repository;

import com.workplace.simon.model.BaseLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseLineRepository extends JpaRepository<BaseLine, Long> {
    List<BaseLine> findByActiveTrue();
}
