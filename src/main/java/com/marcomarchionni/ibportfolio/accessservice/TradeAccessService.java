package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.Trade;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TradeAccessService {

    List<Trade> findByParams(LocalDate startDate, LocalDate endDate, Boolean tagged, String symbol,
                             String assetCategory);

    boolean existsByIbOrderId(Long ibOrderId);

    Trade save(Trade trade);

    List<Trade> saveAll(List<Trade> trades);

    Optional<Trade> findById(Long id);
}
