package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.domain.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Long> {
    @Query(
            "SELECT s FROM strategy s WHERE" +
                    "(s.accountId = :accountId) and" +
                    "(:name is null or s.name = :name)")
    List<Strategy> findByParams(@Param("accountId") String accountId, @Param("name") String name);

    //TODO: check if this is needed
    List<Strategy> findByName(String expectedSymbol);

    Optional<Strategy> findByIdAndAccountId(Long id, String accountId);
}
