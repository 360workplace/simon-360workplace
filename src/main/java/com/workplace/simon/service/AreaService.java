package com.workplace.simon.service;

import com.workplace.simon.model.Area;
import com.workplace.simon.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    public AreaRepository getAreaRepository() {
        return areaRepository;
    }

    public Area save(Area area) {
        return this.getAreaRepository().save(area);
    }

    public List<Area> findAll() {
        return this.getAreaRepository().findAll();
    }

    public Optional<Area> findById(Long id) {
        return this.getAreaRepository().findById(id);
    }

    public void delete(Area area) {
        this.getAreaRepository().delete(area);
    }
}
