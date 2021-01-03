package com.workplace.simon.service;

import com.workplace.simon.model.Act;
import com.workplace.simon.repository.ActRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActService {
    @Autowired
    private ActRepository actRepository;

    public ActRepository getActRepository() {
        return actRepository;
    }

    public Act save(Act act) {
        return this.getActRepository().save(act);
    }

    public List<Act> findAll() {
        return this.getActRepository().findAll();
    }

    public Optional<Act> findById(Long id) {
        return this.getActRepository().findById(id);
    }

    public void delete(Act act) {
        this.getActRepository().delete(act);
    }
}
