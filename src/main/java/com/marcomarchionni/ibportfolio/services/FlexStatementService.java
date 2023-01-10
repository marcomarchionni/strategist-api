package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.domain.FlexStatement;

import java.time.LocalDate;
import java.util.List;

public interface FlexStatementService {

    LocalDate getLastReportDate();

    void save(FlexStatement flexStatement);

    List<FlexStatement> findAllOrderedByFromDateAsc();
}

