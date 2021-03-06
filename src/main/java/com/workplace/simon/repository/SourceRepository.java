package com.workplace.simon.repository;

import com.workplace.simon.model.Source;
import com.workplace.simon.model.SourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {
    List<Source> findByActiveTrue();

    @Query(value = "SELECT source0_.id as id, source0_.active as active, source0_.detail as detail, " +
            "source0_.fecha_inicial as fecha_inicial, source0_.titulo as titulo, source0_.type as type, " +
            "source0_.fuente as fuente, source0_.responsable as responsable " +
            "from linea_base source0_ INNER JOIN users u ON (source0_.fuente = u.id) " +
            "INNER JOIN area a ON (u.area_id = a.id) " +
            "WHERE a.id = (:areaId) AND source0_.active=true", nativeQuery = true)
    List<Source> findByArea(@Param("areaId") Long id);

    @Query(value = "SELECT source0_.id as id, source0_.active as active, source0_.detail as detail, " +
            "source0_.fecha_inicial as fecha_inicial, source0_.titulo as titulo, source0_.type as type, " +
            "source0_.fuente as fuente, source0_.responsable as responsable " +
            "from linea_base source0_ INNER JOIN users u ON (source0_.fuente = u.id) " +
            "INNER JOIN area a ON (u.area_id = a.id) " +
            "WHERE a.id = (:areaId) " +
            "AND source0_.type = (:type) " +
            "AND source0_.active=true", nativeQuery = true)
    List<Source> findByTypeAndArea(@Param("type") int type, @Param("areaId") Long area);

    List<Source> findByType(SourceType type);
}
