package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DividendRepository extends JpaRepository<Dividend, Long> {

    void deleteByOpenClosed(String openClosed);

  @Query("SELECT d FROM dividend d WHERE " +
          "(:exDateFrom is null or d.exDate > :exDateFrom) and " +
          "(:exDateTo is null or d.exDate < :exDateTo) and " +
          "(:payDateFrom is null or d.payDate > :payDateFrom) and " +
          "(:payDateTo is null or d.payDate < :payDateTo) and " +
          "(:symbol is null or d.symbol = :symbol) and " +
          "(:tagged is null or ((:tagged = true and d.strategy is not null ) or (:tagged = false and d.strategy is null)))")
    List<Dividend> findByParams(@Param("exDateFrom") LocalDate exDateFrom,
                                @Param("exDateTo") LocalDate exDateTo,
                                @Param("payDateFrom") LocalDate payDateFrom,
                                @Param("payDateTo") LocalDate payDateTo,
                                @Param("tagged") Boolean tagged,
                                @Param("symbol") String symbol);
}

