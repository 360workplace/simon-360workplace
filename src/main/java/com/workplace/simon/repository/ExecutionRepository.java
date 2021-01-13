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

    List<Execution> findBySourceAndStatusNotOrderByPriorityDesc(Long userId, String status);

    List<Execution> findBySupervisorAndStatus(Long supervisorId, String status);

    List<Execution> findByStatusNot(String status);

    @Query(value = "SELECT execution0_.id as id, execution0_.code_from as code_from, execution0_.deadline as deadline, " +
            "execution0_.detail as detail, execution0_.priority as priority, execution0_.resources as resource, " +
            "execution0_.source as source, execution0_.status as status, execution0_.supervisor as supervisor, " +
            "execution0_.title as title, execution0_.resources as resources " +
            "FROM ejecucion execution0_ " +
            "INNER JOIN users u ON (execution0_.source = u.id) " +
            "INNER JOIN area a ON (u.area_id = a.id) " +
            "WHERE a.id = (:areaId) " +
            "AND execution0_.status = (:status)", nativeQuery = true)
    List<Execution> findByAreaAndStatus(@Param("areaId") Long areaId, @Param("status") String status);

    @Query(value = "SELECT execution0_.id as id, execution0_.code_from as code_from, execution0_.deadline as deadline, " +
            "execution0_.detail as detail, execution0_.priority as priority, execution0_.resources as resource, " +
            "execution0_.source as source, execution0_.status as status, execution0_.supervisor as supervisor, " +
            "execution0_.title as title, execution0_.resources as resources " +
            "FROM ejecucion execution0_ " +
            "INNER JOIN users u ON (execution0_.source = u.id) " +
            "INNER JOIN area a ON (u.area_id = a.id) " +
            "WHERE a.id == (:areaId) " +
            "AND execution0_.status <> (:status)", nativeQuery = true)
    List<Execution>  findByAreaAndStatusNot(@Param("areaId") Long areaId, @Param("status") String status);
}
