package com.marcomarchionni.ibportfolio.services.fetchers;

import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.IbServerErrorException;
import com.marcomarchionni.ibportfolio.services.fetchers.validators.DtoValidator;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ServerDataFetcher implements DataFetcher {

    private final RestTemplate restTemplate;
    private final String token;
    private final String queryId;
    private final String authUrl;
    private final String reqPath;
    private final int maxAttempts;
    private final long retryDelay;

    private final DtoValidator dtoValidator;

    public ServerDataFetcher(RestTemplate restTemplate, @Value("${ib.token}") String token,
                             @Value("${ib.query-id}") String queryId, @Value("${ib.auth-url}") String authUrl,
                             @Value("${ib.req-path}") String reqPath, @Value("${ib.max-attempts}") int maxAttempts,
                             @Value("${ib.retry-delay}") long retryDelay, DtoValidator dtoValidator) {
        this.restTemplate = restTemplate;
        this.token = token;
        this.queryId = queryId;
        this.authUrl = authUrl;
        this.reqPath = reqPath;
        this.maxAttempts = maxAttempts;
        this.retryDelay = retryDelay;
        this.dtoValidator = dtoValidator;
    }

    @Override
    public FlexQueryResponseDto fetch(FetchContext context) {

        // Create request entity with headers
        HttpEntity<String> requestEntity = createRequestWithHeaders();

        // Fetch statement response dto from server
        FlexStatementResponseDto statementResponseDto = executeRequestWithRetry(authUrl, requestEntity,
                FlexStatementResponseDto.class, token, queryId);

        // Extract url and reference code from statement response dto
        String downloadUrl = statementResponseDto.getUrl() + reqPath;
        String referenceCode = statementResponseDto.getReferenceCode();

        // Fetch flex query response dto using url and code from first response

        return executeRequestWithRetry(downloadUrl, requestEntity, FlexQueryResponseDto.class, token, referenceCode);
    }

    private HttpEntity<String> createRequestWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Technology/Version");
        return new HttpEntity<>(headers);
    }

    private <T> @NotNull T executeRequestWithRetry(String url, HttpEntity<?> requestEntity,
                                                   Class<T> responseType, Object... uriVariables) {
        int attempts = 1;
        long delay = retryDelay;
        ResponseEntity<T> response;
        while (attempts <= maxAttempts) {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    responseType,
                    uriVariables);

            // If response is OK and dto is valid, return dto
            log.info("Executing request to IB server... Attempt: {}", attempts);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && dtoValidator.isValid(response.getBody())) {
                log.info("Valid response from IB server, returning... Response: {}", response);
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
        throw new IbServerErrorException("Failed to get valid response from IB server after " + maxAttempts + " " +
                "attempts");
    }
}
