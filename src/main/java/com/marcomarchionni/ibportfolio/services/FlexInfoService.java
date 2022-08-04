package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.domain.FlexInfo;

import java.time.LocalDate;
import java.util.List;

public interface FlexInfoService {

    LocalDate getLastReportDate();

    void save(FlexInfo flexInfo);

    List<FlexInfo> findAllOrderedByFromDateAsc();
}

