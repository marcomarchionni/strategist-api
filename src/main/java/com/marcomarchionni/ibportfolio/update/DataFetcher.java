package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.models.dtos.FlexStatementResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
@Slf4j
public class DataFetcher {

    @Value("${token}")
    private String token;

    @Value("${query.id}")
    private String queryId;

    @Value("${auth.url}")
    private String authUrl;

    @Value("${req.path}")
    private String reqPath;

    public FlexQueryResponseDto fetchFromServer() {

        // istanziamo un restTemplate per comporre le chiamate in sequenza
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // settiamo gli header come da documentazione
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Technology/Version");
        HttpEntity<String> request = new HttpEntity<>(headers);

        // eseguiamo la chiamata indicando anche la risposta che ci attendiamo dal servizio, mappata nella classe FlexStatementResponseDto
        // inseriamo come parametri che sono il token e il query id prelevati dalle properties
        log.info("Performing first API call with token: " + token + ", queryId: " + queryId);

        ResponseEntity<FlexStatementResponseDto> response = restTemplate.exchange(authUrl, HttpMethod.GET, request, FlexStatementResponseDto.class, token, queryId);

        if (response.getBody() == null || response.getStatusCode() != HttpStatus.OK) {
            log.error("Error while invoking external services");
            return null;
        }

        // eseguiamo la seconda chiamata utilizzando l'url e il codice restituiti nella prima
        log.info("Performing second API call with reference code: {}", response.getBody().getReferenceCode());

        ResponseEntity<FlexQueryResponseDto> result =
                restTemplate.exchange(response.getBody().getUrl() + reqPath, HttpMethod.GET, request, FlexQueryResponseDto.class, token, response.getBody().getReferenceCode());

        if (result.getBody() == null || result.getStatusCode() != HttpStatus.OK) {
            log.error("Error while invoking external services");
            return null;
        }

        return result.getBody();
    }

    public FlexQueryResponseDto fetchFromFile(File flexQuery) throws Exception {

            JAXBContext jaxbContext = JAXBContext.newInstance(FlexQueryResponseDto.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            FlexQueryResponseDto dto = (FlexQueryResponseDto) jaxbUnmarshaller.unmarshal(flexQuery);

            log.info(dto.getQueryName());
            return dto;
    }
}
