package com.workplace.simon.repository;

import com.workplace.simon.model.WeeklyNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyNewsRepository extends JpaRepository<WeeklyNews, Long> {
}
