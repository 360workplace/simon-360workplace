package com.workplace.simon.service;

import com.workplace.simon.model.Register;
import com.workplace.simon.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Deprecated
@Service
public class RegisterService {
    @Autowired
    private RegisterRepository registerRepository;

    public RegisterRepository getRegisterRepository() {
        return registerRepository;
    }

    public Register save(Register register) {
        return this.getRegisterRepository().save(register);
    }

    public List<Register> findAll() {
        return this.getRegisterRepository().findAll();
    }

    public Optional<Register> findById(Long id) {
        return this.getRegisterRepository().findById(id);
    }

    public void delete(Register register) {
        this.getRegisterRepository().delete(register);
    }
}
