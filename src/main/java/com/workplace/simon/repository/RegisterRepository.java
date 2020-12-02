package com.workplace.simon.repository;

import com.workplace.simon.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegisterRepository extends JpaRepository<Register, UUID> {
}
