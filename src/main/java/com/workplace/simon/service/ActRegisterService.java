package com.workplace.simon.service;

import com.workplace.simon.model.ActRegister;
import com.workplace.simon.repository.ActRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActRegisterService {
    @Autowired
    private ActRegisterRepository actRegisterRepository;

    public ActRegisterRepository getActRegisterRepository() {
        return actRegisterRepository;
    }

    public ActRegister save(ActRegister actRegister) {
        return this.getActRegisterRepository().save(actRegister);
    }

    public List<ActRegister> findAll() {
        return this.getActRegisterRepository().findAll();
    }

    public Optional<ActRegister> findById(Long id) {
        return this.getActRegisterRepository().findById(id);
    }

    public void delete(ActRegister actRegister) {
        this.getActRegisterRepository().delete(actRegister);
    }

    public List<ActRegister> findByArea(Long id) {
        return this.getActRegisterRepository().findByArea(id);
    }
}
