package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.IbServerErrorException;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexStatementResponseDto;
import com.marcomarchionni.ibportfolio.services.UpdateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
public class LiveUpdater {

    @Value("${token}")
    private String token;

    @Value("${query.id}")
    private String queryId;

    @Value("${auth.url}")
    private String authUrl;

    @Value("${req.path}")
    private String reqPath;
    private final UpdateService updateService;

    private final RestTemplate restTemplate;

    public LiveUpdater(UpdateService updateService, RestTemplate restTemplate) {
        this.updateService = updateService;
        this.restTemplate = restTemplate;
    }

    public void update() {
        FlexQueryResponseDto flexQueryResponseDto = fetchFlexQuery();
        updateService.save(flexQueryResponseDto);
    }

    private FlexQueryResponseDto fetchFlexQuery() {

        // Set headers as documented by Interactive Brokers
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Technology/Version");
        HttpEntity<String> request = new HttpEntity<>(headers);

        // First call to server, response mapped in FlexStatementResponseDto
        ResponseEntity<FlexStatementResponseDto> statementResponse = restTemplate.exchange(
                authUrl, HttpMethod.GET, request, FlexStatementResponseDto.class, token, queryId);

        if (statementResponse.getBody() == null || statementResponse.getStatusCode() != HttpStatus.OK) {
            throw new IbServerErrorException();
        }

        String downloadUrl = statementResponse.getBody().getUrl() + reqPath;
        String referenceCode = statementResponse.getBody().getReferenceCode();

        // Second call using url and code from first response
        ResponseEntity<FlexQueryResponseDto> queryResponse =
                restTemplate.exchange(
                        downloadUrl,
                        HttpMethod.GET,
                        request,
                        FlexQueryResponseDto.class,
                        token,
                        referenceCode);

        FlexQueryResponseDto xml = queryResponse.getBody();
        HttpStatusCode statusCode = queryResponse.getStatusCode();

        if (xml == null || statusCode != HttpStatus.OK) {
            throw new IbServerErrorException();
        }

        return xml;
    }
}
