package com.workplace.simon.repository;

import com.workplace.simon.model.User;
import com.workplace.simon.model.WeeklyNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface WeeklyNewsRepository extends JpaRepository<WeeklyNews, Long> {
    List<WeeklyNews> findByDateBetween(Date start, Date end);

    List<WeeklyNews> findByDateBetweenAndSource(Date start, Date end, User user);
}
