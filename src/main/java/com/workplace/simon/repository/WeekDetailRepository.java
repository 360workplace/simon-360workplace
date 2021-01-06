package com.workplace.simon.repository;

import com.workplace.simon.model.WeekDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekDetailRepository extends JpaRepository<WeekDetail, Long> {
}
