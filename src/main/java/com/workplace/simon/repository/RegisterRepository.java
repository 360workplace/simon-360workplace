package com.workplace.simon.repository;

import com.workplace.simon.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {
}
