package com.workplace.simon.service;

import com.workplace.simon.model.Source;
import com.workplace.simon.model.SourceType;
import com.workplace.simon.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SourceService {
    @Autowired
    private SourceRepository sourceRepository;

    public SourceRepository getSourceRepository() {
        return sourceRepository;
    }

    public Source save(Source source) {
        return this.getSourceRepository().save(source);
    }

    public List<Source> findAll() {
        return this.getSourceRepository().findByActiveTrue();
    }

    public Optional<Source> findById(Long id) {
        return this.getSourceRepository().findById(id);
    }

    public void delete(Source source) {
        this.getSourceRepository().delete(source);
    }

    public List<Source> findByArea(Long id) {
        return this.getSourceRepository().findByArea(id);
    }

    public List<Source> findByTypeAndArea(SourceType type, Long area) {
        return this.getSourceRepository().findByTypeAndArea(type.ordinal(), area);
    }

    public List<Source> findByType(SourceType type) {
        return this.getSourceRepository().findByType(type);
    }
}
