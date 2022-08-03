package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.domain.Position;
import com.marcomarchionni.ibportfolio.models.dtos.request.UpdateStrategyDto;
import com.marcomarchionni.ibportfolio.models.dtos.request.PositionFindDto;
import com.marcomarchionni.ibportfolio.models.dtos.response.PositionListDto;

import java.util.List;

public interface PositionService {

    void saveAll(List<Position> positions);

    void deleteAll();

    PositionListDto updateStrategyId(UpdateStrategyDto position);

    List<PositionListDto> findByParams(PositionFindDto positionCriteria);
}
