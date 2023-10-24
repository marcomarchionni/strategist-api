package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;

public interface DataSaverService {

    void save(FlexQueryResponseDto flexQueryResponseDto);
}
