package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.FlexStatement;

import java.time.LocalDate;
import java.util.List;

public interface FlexStatementService {

    LocalDate getLastReportDate();

    FlexStatement saveFlexStatement(FlexStatement flexStatement);

    List<FlexStatement> getAllOrderedByFromDateAsc();
}

