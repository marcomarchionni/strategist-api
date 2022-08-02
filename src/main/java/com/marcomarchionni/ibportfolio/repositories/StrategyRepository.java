package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.models.domain.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Long> {
    @Query(
            "SELECT s FROM strategy s WHERE" +
            "(:name is null or s.name = :name)")
    List<Strategy> findByParams(@Param("name") String name);
}
