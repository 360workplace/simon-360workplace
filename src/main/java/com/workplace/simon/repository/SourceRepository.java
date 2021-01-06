package com.workplace.simon.repository;

import com.workplace.simon.model.ActRegister;
import com.workplace.simon.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {
    List<Source> findByActiveTrue();

    @Query(value = "SELECT e.id as id, e FROM linea_base e INNER JOIN users u ON (e.fuente = u.id) INNER JOIN area a ON (u.area_id = a.id) WHERE a.id = (:areaId)", nativeQuery = true)
    List<Source> findByArea(@Param("areaId") Long id);
}
