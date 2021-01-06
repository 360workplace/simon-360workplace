package com.workplace.simon.repository;

import com.workplace.simon.model.ActRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActRegisterRepository extends JpaRepository<ActRegister, Long> {
    @Query("SELECT * FROM ejecucion e INNER JOIN users u ON (e.source = u.id) INNER JOIN area a ON (u.area_id = a.id) WHERE a.id = ?1")
    List<ActRegister> findByArea(Long id);
}
