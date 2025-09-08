package com.marcomarchionni.strategistapi.strategies.service;

import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.NameUpdate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyCreate;
import com.marcomarchionni.strategistapi.dtos.request.StrategyFind;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.StrategyDetail;
import com.marcomarchionni.strategistapi.dtos.response.StrategySummary;
import java.util.List;

public interface StrategyService {

  List<StrategySummary> findByFilter(StrategyFind strategyFind);

  ApiResponse<StrategySummary> findAllWithCount(FindAllReq findReq);

  StrategyDetail updateName(NameUpdate nameUpdate);

  void deleteById(Long id);

  StrategyDetail findById(Long id);

  StrategyDetail create(StrategyCreate strategyCreate);
}
