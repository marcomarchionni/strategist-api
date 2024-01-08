package com.marcomarchionni.strategistapi.repositories;

import com.marcomarchionni.strategistapi.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query("SELECT p FROM position p WHERE " +
            "(p.accountId = :accountId) and" +
            "(:symbol is null or p.symbol = :symbol) and" +
            "(:assetCategory is null or p.assetCategory = :assetCategory) and" +
            "(:tagged is null or ((:tagged = true and p.strategy is not null ) or (:tagged = false and p.strategy is " +
            "null)))")
    List<Position> findByParams(@Param("accountId") String accountId,
                                @Param("tagged") Boolean tagged,
                                @Param("symbol") String symbol,
                                @Param("assetCategory") String assetCategory);

    List<Position> findAllByAccountId(String accountId);

    Optional<Position> findByAccountIdAndSymbol(String accountId, String symbol);

    Optional<Position> findByIdAndAccountId(Long id, String accountId);

}
