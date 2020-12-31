package com.workplace.simon.repository;

import com.workplace.simon.model.BaseSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceRepository extends JpaRepository<BaseSource, Long> {
    List<BaseSource> findByActiveTrue();
}
