package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;

import java.time.LocalDate;

public interface FlexStatementService {

    LocalDate findLatestToDate();

    UpdateReport<FlexStatement> updateFlexStatements(FlexStatement flexStatement);
}

