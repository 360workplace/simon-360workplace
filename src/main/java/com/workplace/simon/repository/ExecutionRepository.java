package com.workplace.simon.repository;

import com.workplace.simon.model.Execution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {
    List<Execution> findBySourceAndStatus(Long userId, String status);

    List<Execution> findBySupervisorAndStatus(Long supervisorId, String status);

    @Query(value = "SELECT e FROM ejecucion e INNER JOIN users u ON (e.source = u.id) INNER JOIN area a ON (u.area_id = a.id) WHERE a.id = (:areaId)", nativeQuery = true)
    List<Execution> findByArea(@Param("areaId") Long id);
}
