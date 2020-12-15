package com.workplace.simon.service;

import com.workplace.simon.model.BaseLine;
import com.workplace.simon.repository.BaseLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BaseLineService {
    @Autowired
    private BaseLineRepository baseLineRepository;

    public BaseLineRepository getBaseLineRepository() {
        return baseLineRepository;
    }

    public BaseLine save(BaseLine baseLine) {
        return this.getBaseLineRepository().save(baseLine);
    }

    public List<BaseLine> findAll() {
        return this.getBaseLineRepository().findByActiveTrue();
    }

    public Optional<BaseLine> findById(Long id) {
        return this.getBaseLineRepository().findById(id);
    }

    public void delete(BaseLine baseLine) {
        this.getBaseLineRepository().delete(baseLine);
    }
}
