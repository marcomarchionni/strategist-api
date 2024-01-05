package com.marcomarchionni.ibportfolio.services.fetchers.flexserviceclients;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexStatementResponseDto;
import org.springframework.http.ResponseEntity;

public interface FlexServiceClient {

    ResponseEntity<FlexStatementResponseDto> fetchFlexStatementResponse(String queryId, String token);

    ResponseEntity<FlexQueryResponseDto> fetchFlexQueryResponse(FlexStatementResponseDto flexStatementResponseDto,
                                                                String token);
}
