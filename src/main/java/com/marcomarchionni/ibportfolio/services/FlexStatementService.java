package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;

import java.time.LocalDate;
import java.util.List;

public interface FlexStatementService {

    // TODO: Implement db check for data continuity
    @SuppressWarnings("unused")
    LocalDate getLastReportDate();

    void save(FlexStatement flexStatement);

    @SuppressWarnings("unused")
    List<FlexStatement> findAllOrderedByFromDateAsc();
}

