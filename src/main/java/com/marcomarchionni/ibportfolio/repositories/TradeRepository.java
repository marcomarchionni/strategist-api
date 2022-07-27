package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.models.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query("SELECT t FROM trade t WHERE " +
            "(:startDate is null or t.tradeDate >= :startDate) and" +
            "(:endDate is null or t.tradeDate <= :endDate) and" +
            "(:symbol is null or t.symbol = :symbol) and" +
            "(:assetCategory is null or t.assetCategory = :assetCategory) and" +
            "(:tagged is null or ((:tagged is true and t.strategyId is not null ) or (:tagged is false and t.strategyId is null)))")
    List<Trade> findWithParameters(@Param("startDate") LocalDate startDate,
                                   @Param ("endDate") LocalDate endDate,
                                   @Param("tagged") Boolean tagged,
                                   @Param("symbol") String symbol,
                                   @Param("assetCategory") String assetCategory);
}
