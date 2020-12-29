package com.workplace.simon.service;

import com.workplace.simon.model.Source;
import com.workplace.simon.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SourceService {
    @Autowired
    private SourceRepository baseLineRepository;

    public SourceRepository getBaseLineRepository() {
        return baseLineRepository;
    }

    public Source save(Source source) {
        return this.getBaseLineRepository().save(source);
    }

    public List<Source> findAll() {
        return this.getBaseLineRepository().findByActiveTrue();
    }

    public Optional<Source> findById(Long id) {
        return this.getBaseLineRepository().findById(id);
    }

    public void delete(Source source) {
        this.getBaseLineRepository().delete(source);
    }
}
