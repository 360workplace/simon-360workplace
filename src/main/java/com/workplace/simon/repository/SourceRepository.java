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

    @Query(value = "SELECT source0_.id as id1_4_, source0_.active as active2_4_, source0_.detail as detail3_4_, source0_.fecha_inicial as fecha_in4_4_, source0_.titulo as titulo5_4_, source0_.type as type6_4_, source0_.fuente as fuente7_4_, source0_.responsable as responsa8_4_ from linea_base source0_ INNER JOIN users u ON (source0_.fuente = u.id) INNER JOIN area a ON (u.area_id = a.id) WHERE a.id = (:areaId) AND source0_.active=true", nativeQuery = true)
    List<Source> findByArea(@Param("areaId") Long id);
}
