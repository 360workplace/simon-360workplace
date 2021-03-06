package com.workplace.simon.repository;

import com.workplace.simon.model.ActRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActRegisterRepository extends JpaRepository<ActRegister, Long> {
}
