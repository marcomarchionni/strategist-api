package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;

import java.time.LocalDate;

public interface FlexStatementService {

    LocalDate findLatestToDate(User user);

    UpdateReport<FlexStatement> updateFlexStatements(User user, FlexStatement flexStatement);
}

