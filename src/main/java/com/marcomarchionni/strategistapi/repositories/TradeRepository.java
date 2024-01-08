package com.marcomarchionni.strategistapi.repositories;

import com.marcomarchionni.strategistapi.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query("SELECT t FROM trade t WHERE " +
            "(t.accountId = :accountId) and" +
            "(:startDate is null or t.tradeDate >= :startDate) and" +
            "(:endDate is null or t.tradeDate <= :endDate) and" +
            "(:symbol is null or t.symbol = :symbol) and" +
            "(:assetCategory is null or t.assetCategory = :assetCategory) and" +
            "(:tagged is null or ((:tagged = true and t.strategy is not null ) or (:tagged = false and t.strategy is null)))")
    List<Trade> findByParams(@Param("accountId") String accountId,
                             @Param("startDate") LocalDate startDate,
                             @Param ("endDate") LocalDate endDate,
                             @Param("tagged") Boolean tagged,
                             @Param("symbol") String symbol,
                             @Param("assetCategory") String assetCategory);

    boolean existsByAccountIdAndIbOrderId(String accountId, Long ibOrderId);

    Optional<Trade> findByAccountIdAndIbOrderId(String accountId, Long ibOrderId);

    Optional<Trade> findByIdAndAccountId(Long id, String accountId);
}
