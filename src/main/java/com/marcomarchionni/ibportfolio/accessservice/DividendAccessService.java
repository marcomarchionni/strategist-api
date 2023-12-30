package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.Dividend;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DividendAccessService {

    List<Dividend> findByParams(LocalDate exDateFrom, LocalDate exDateTo, LocalDate payDateFrom, LocalDate payDateTo,
                                Boolean tagged, String symbol);

    Optional<Dividend> findById(Long id);

    List<Dividend> findBySymbol(String symbol);

    boolean existsByActionId(Long actionId);

    List<Dividend> findOpenDividends();

    Dividend save(Dividend dividend);

    List<Dividend> saveAll(List<Dividend> dividends);
}
