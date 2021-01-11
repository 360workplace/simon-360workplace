package com.workplace.simon.service;

import com.workplace.simon.model.WeeklyNews;
import com.workplace.simon.repository.WeeklyNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeeklyNewsService {
    @Autowired
    private WeeklyNewsRepository weeklyNewsRepository;

    public WeeklyNewsRepository getWeeklyNewsRepository() {
        return weeklyNewsRepository;
    }

    public WeeklyNews save(WeeklyNews weeklyNews) {
        return this.getWeeklyNewsRepository().save(weeklyNews);
    }

    public List<WeeklyNews> findAll() {
        return this.getWeeklyNewsRepository().findAll();
    }

    public Optional<WeeklyNews> findById(Long id) {
        return this.getWeeklyNewsRepository().findById(id);
    }

    public void delete(WeeklyNews weeklyNews) {
        this.getWeeklyNewsRepository().delete(weeklyNews);
    }
}
