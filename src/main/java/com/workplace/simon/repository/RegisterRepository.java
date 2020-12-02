package com.workplace.simon.repository;

import com.workplace.simon.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegisterRepository extends JpaRepository<Register, UUID> {
}
