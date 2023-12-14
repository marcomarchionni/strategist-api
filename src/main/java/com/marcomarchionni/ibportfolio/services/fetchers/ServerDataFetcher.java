package com.marcomarchionni.ibportfolio.services.fetchers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.IbServerErrorException;
import com.marcomarchionni.ibportfolio.services.fetchers.validators.DtoValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ServerDataFetcher implements DataFetcher {

    private final RestTemplate restTemplate;
    @Value("${token}")
    private String token;
    @Value("${query.id}")
    private String queryId;
    @Value("${auth.url}")
    private String authUrl;
    @Value("${req.path}")
    private String reqPath;

    private final int MAX_ATTEMPTS = 3;
    private final int RETRY_DELAY = 1000;

    private final DtoValidator dtoValidator;

    public ServerDataFetcher(RestTemplate restTemplate, DtoValidator dtoValidator) {
        this.restTemplate = restTemplate;
        this.dtoValidator = dtoValidator;
    }

    @Override
    public FlexQueryResponseDto fetch(FetchContext context) {

        HttpEntity<String> requestEntity = createRequest();

        // Fetch statement response dto from server
        FlexStatementResponseDto statementResponseDto = executeRequestWithRetry(authUrl, requestEntity,
                FlexStatementResponseDto.class, token, queryId);

        // Extract url and reference code from statement response dto
        assert statementResponseDto != null;
        String downloadUrl = statementResponseDto.getUrl() + reqPath;
        String referenceCode = statementResponseDto.getReferenceCode();

        // Fetch flex query response dto using url and code from first response

        return executeRequestWithRetry(downloadUrl, requestEntity, FlexQueryResponseDto.class, token, referenceCode);
    }

    private HttpEntity<String> createRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Technology/Version");
        return new HttpEntity<>(headers);
    }

    private <T> T executeRequestWithRetry(String url, HttpEntity<?> requestEntity,
                                          Class<T> responseType, Object... uriVariables) {
        int attempts = 0;
        long delay = RETRY_DELAY;
        ResponseEntity<T> response = null;
        while (attempts < MAX_ATTEMPTS) {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    responseType,
                    uriVariables);

            // If response is OK and dto is valid, return dto
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && dtoValidator.isValid(response.getBody())) {
                return response.getBody();
            }

            // If response is not OK, wait and retry
            log.info("Invalid response from IB server, retrying... Response: {}", response);
            attempts++;
            try {
                Thread.sleep(delay);
                delay *= 2;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IbServerErrorException("Interrupted while waiting to retry");
            }
        }
        throw new IbServerErrorException(response);
    }
}
