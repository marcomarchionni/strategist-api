package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.FlexStatement;
import com.marcomarchionni.strategistapi.dtos.update.UpdateReport;

import java.time.LocalDate;

public interface FlexStatementService {

    LocalDate findLatestToDate();

    UpdateReport<FlexStatement> updateFlexStatements(FlexStatement flexStatement);
}

