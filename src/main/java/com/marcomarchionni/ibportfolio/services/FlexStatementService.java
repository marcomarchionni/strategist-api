package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.FlexInfo;

import java.time.LocalDate;
import java.util.List;

public interface FlexStatementService {

    LocalDate getLatestDateWithDataInDb();

    void save(FlexInfo flexInfo);

    List<FlexInfo> findAllOrderedByFromDateAsc();
}

