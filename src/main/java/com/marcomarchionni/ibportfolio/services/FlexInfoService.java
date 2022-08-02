package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.domain.FlexInfo;

import java.time.LocalDate;
import java.util.List;

public interface FlexInfoService {

    LocalDate getLatestDateWithDataInDb();

    void save(FlexInfo flexInfo);

    List<FlexInfo> findAllOrderedByFromDateAsc();
}

