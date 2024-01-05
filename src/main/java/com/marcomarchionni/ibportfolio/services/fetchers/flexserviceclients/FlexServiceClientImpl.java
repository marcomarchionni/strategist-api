package com.marcomarchionni.ibportfolio.services.fetchers.flexserviceclients;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexStatementResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FlexServiceClientImpl implements FlexServiceClient {

    private final RestTemplate restTemplate;
    private final String authUrl;
    private final String reqPath;
    private final HttpEntity<String> requestEntity = createRequestWithHeaders();

    public FlexServiceClientImpl(RestTemplate restTemplate,
                                 @Value("${flexservice.auth-url}") String authUrl,
                                 @Value("${flexservice.req-path}") String reqPath) {
        this.restTemplate = restTemplate;
        this.authUrl = authUrl;
        this.reqPath = reqPath;
    }

    private static HttpEntity<String> createRequestWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Technology/Version");
        return new HttpEntity<>(headers);
    }

    @Override
    public ResponseEntity<FlexStatementResponseDto> fetchFlexStatementResponse(String queryId, String token) {

        return restTemplate.exchange(
                authUrl,
                HttpMethod.GET,
                requestEntity,
                FlexStatementResponseDto.class,
                token, queryId);
    }

    @Override
    public ResponseEntity<FlexQueryResponseDto> fetchFlexQueryResponse(FlexStatementResponseDto flexStatementResponseDto, String token) {

        return restTemplate.exchange(
                flexStatementResponseDto.getUrl() + reqPath,
                HttpMethod.GET,
                requestEntity,
                FlexQueryResponseDto.class,
                token,
                flexStatementResponseDto.getReferenceCode());
    }
}
