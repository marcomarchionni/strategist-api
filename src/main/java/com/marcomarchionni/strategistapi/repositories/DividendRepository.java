package com.marcomarchionni.strategistapi.repositories;

import com.marcomarchionni.strategistapi.domain.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DividendRepository extends JpaRepository<Dividend, Long> {

    List<Dividend> findByOpenClosedAndAccountId(Dividend.OpenClosed openClosed, String accountId);

  @Query("SELECT d FROM dividend d WHERE " +
          "(d.accountId = :accountId) and " +
          "(:exDateFrom is null or d.exDate > :exDateFrom) and " +
          "(:exDateTo is null or d.exDate < :exDateTo) and " +
          "(:payDateFrom is null or d.payDate > :payDateFrom) and " +
          "(:payDateTo is null or d.payDate < :payDateTo) and " +
          "(:symbol is null or d.symbol = :symbol) and " +
          "(:tagged is null or ((:tagged = true and d.strategy is not null ) or (:tagged = false and d.strategy is null)))")
  List<Dividend> findByParams(@Param("accountId") String accountId,
                              @Param("exDateFrom") LocalDate exDateFrom,
                                @Param("exDateTo") LocalDate exDateTo,
                                @Param("payDateFrom") LocalDate payDateFrom,
                                @Param("payDateTo") LocalDate payDateTo,
                                @Param("tagged") Boolean tagged,
                                @Param("symbol") String symbol);

    Optional<Dividend> findByIdAndAccountId(Long id, String accountId);

  List<Dividend> findByAccountIdAndSymbol(String accountId, String symbol);

    boolean existsByAccountIdAndActionId(String accountId, Long actionId);

    void deleteByAccountId(String accountId);

    List<Dividend> findAllByAccountId(String accountId);
}

