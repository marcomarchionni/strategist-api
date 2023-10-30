package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;

import java.time.LocalDate;
import java.util.List;

public interface FlexStatementService {

    // TODO: Implement db check for data continuity
    @SuppressWarnings("unused")
    LocalDate findLatestToDate();

    UpdateReport<FlexStatement> save(FlexStatement flexStatement);

    @SuppressWarnings("unused")
    List<FlexStatement> findAllOrderedByFromDateAsc();
}

