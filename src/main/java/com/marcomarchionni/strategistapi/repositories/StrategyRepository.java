package com.marcomarchionni.strategistapi.repositories;

import com.marcomarchionni.strategistapi.domain.Strategy;
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

    Optional<Strategy> findByAccountIdAndName(String accountId, String expectedSymbol);

    Optional<Strategy> findByIdAndAccountId(Long id, String accountId);

    void deleteByAccountId(String accountId);

    List<Strategy> findAllByAccountId(String accountId);
}
