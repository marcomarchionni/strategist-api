package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.FlexStatement;

import java.time.LocalDate;
import java.util.List;

public interface FlexStatementService {

    LocalDate getLatestDateWithDataInDb();

    void save(FlexStatement flexStatement);

    List<FlexStatement> findAllOrderedByFromDateAsc();
}

