package com.marcomarchionni.ibportfolio.update;

import com.marcomarchionni.ibportfolio.errorhandling.exceptions.IbServerErrorException;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexStatementResponseDto;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
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

        // eseguiamo la chiamata indicando anche la risposta che ci attendiamo dal servizio, mappata nella classe FlexStatementResponseDt
        ResponseEntity<FlexStatementResponseDto> response = restTemplate.exchange(
                authUrl, HttpMethod.GET, request, FlexStatementResponseDto.class, token, queryId);

        if (response.getBody() == null || response.getStatusCode() != HttpStatus.OK) {
            throw new IbServerErrorException("Error while invoking external parsing");
        }

        // eseguiamo la seconda chiamata utilizzando l'url e il codice restituiti nella prima

        ResponseEntity<FlexQueryResponseDto> result =
                restTemplate.exchange(
                        response.getBody().getUrl() + reqPath,
                        HttpMethod.GET,
                        request,
                        FlexQueryResponseDto.class,
                        token,
                        response.getBody().getReferenceCode());

        if (result.getBody() == null || result.getStatusCode() != HttpStatus.OK) {
            throw new IbServerErrorException("Error while invoking external parsing");
        }

        return result.getBody();
    }

    public FlexQueryResponseDto fetchFromFile(File flexQuery) throws Exception {

            JAXBContext jaxbContext = JAXBContext.newInstance(FlexQueryResponseDto.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return (FlexQueryResponseDto) jaxbUnmarshaller.unmarshal(flexQuery);
    }
}
