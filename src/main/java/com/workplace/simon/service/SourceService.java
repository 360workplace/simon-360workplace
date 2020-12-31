package com.workplace.simon.service;

import com.workplace.simon.model.BaseSource;
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

    public BaseSource save(BaseSource source) {
        return this.getBaseLineRepository().save(source);
    }

    public List<BaseSource> findAll() {
        return this.getBaseLineRepository().findByActiveTrue();
    }

    public Optional<BaseSource> findById(Long id) {
        return this.getBaseLineRepository().findById(id);
    }

    public void delete(BaseSource source) {
        this.getBaseLineRepository().delete(source);
    }
}
