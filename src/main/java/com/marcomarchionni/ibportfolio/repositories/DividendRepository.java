package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.models.Dividend;
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
          "(:fromExDate is null or d.exDate > :fromExDate) and" +
          "(:toExDate is null or d.exDate < :toExDate) and" +
          "(:fromPayDate is null or d.payDate > :fromPayDate) and" +
          "(:toPayDate is null or d.payDate < :toPayDate) and" +
          "(:symbol is null or d.symbol = :symbol) and" +
          "(:tagged is null or ((:tagged is true and d.strategyId is not null ) or (:tagged is false and d.strategyId is null)))")
  List<Dividend> findWithParameters(@Param("fromExDate") LocalDate fromExDate,
                                    @Param ("toExDate") LocalDate toExDate,
                                    @Param ("fromPayDate") LocalDate fromPayDate,
                                    @Param ("toPayDate") LocalDate toPayDate,
                                    @Param("tagged") Boolean tagged,
                                    @Param("symbol") String symbol);
}
