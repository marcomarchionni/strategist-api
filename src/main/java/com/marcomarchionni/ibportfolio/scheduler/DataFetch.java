package com.marcomarchionni.ibportfolio.scheduler;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.models.dtos.FlexStatementResponseDto;
import com.marcomarchionni.ibportfolio.services.DividendService;
import com.marcomarchionni.ibportfolio.services.PositionService;
import com.marcomarchionni.ibportfolio.services.ResponseParser;
import com.marcomarchionni.ibportfolio.services.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class DataFetch {

    /**
     * Usiamo la notazione @Value per recuperare un valore dal file application.properties
     */
    @Value("${token}")
    private String token;

    @Value("${query.id}")
    private String queryId;

    @Value("${auth.url}")
    private String authUrl;

    @Value("${req.path}")
    private String reqPath;

    @Autowired
    private ResponseParser responseParser;

    @Autowired
    private PositionService positionService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private DividendService dividendService;

    @Scheduled(cron = "${cron.expression}")
    public void fetchData() {

        /**
         * istanziamo un restTemplate per comporre le chiamate in sequenza
         */
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        /**
         * settiamo gli header come da documentazione
         */
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Technology/Version");
        HttpEntity request = new HttpEntity(headers);

        /**
         * eseguiamo la chiamata indicando anche la risposta che ci attendiamo dal servizio, che ho mappato in una classe apposita con le annotation per XML
         * inseriamo anche i parametri che sono il tuo token e il query id, che ho messo nelle properties
         */
        ResponseEntity<FlexStatementResponseDto> response = restTemplate.exchange(authUrl, HttpMethod.GET, request, FlexStatementResponseDto.class, token, queryId);

        if (response == null || response.getBody() == null || response.getStatusCode() != HttpStatus.OK) {
            log.error("Error while invoking external services");
            return;
        }

        log.info("Performing second API call with reference code: {}", response.getBody().getReferenceCode());

        /**
         * eseguiamo la seconda chiamata utilizzando l'url e il codice restituiti nella prima
         * la risposta, come nel caso precedente, è stata mappata in una classe a parte
         * ora hai tutti i dati della risposta, senza semplificazioni
         * Ricorda, da grandi poteri derivano grandi responsabilità :D
         */
        ResponseEntity<FlexQueryResponseDto> result = restTemplate.exchange(response.getBody().getUrl() + reqPath, HttpMethod.GET, request, FlexQueryResponseDto.class, token, response.getBody().getReferenceCode());

        if (result == null || result.getBody() == null || result.getStatusCode() != HttpStatus.OK) {
            log.error("Error while invoking external services");
            return;
        }

        log.info("Data retrieved, dispatching to parser");

        /**
         * usiamo il servizio di parsing iniettato con l'autowired a riga 40 per ottenere la lista delle posizioni e dei trade dalla risposta del server
         */
        List<Position> positions = responseParser.parse(result.getBody(), "position");
        List<Trade> trades = responseParser.parse(result.getBody(), "trade");
        List<Dividend> dividends = responseParser.parse(result.getBody(), "dividend");

        /**
         * ora che abbiamo le nostre liste, utilizziamo il servizio che ha il compito di gestire la persistenza per salvare i nostri dati sul db
         * controlliamo che i servizi non abbiano errore, così possiamo essere certi di aver salvato tutto
         */

        log.info("Data parsed, dispatching to persistence layer");

        boolean positionSaved = positionService.savePositions(positions);

        if (!positionSaved) {
            log.error("Could not store all the positions on the DB");
        }

        boolean tradeSaved = tradeService.saveTrades(trades);

        if (!tradeSaved) {
            log.error("Could not store all the trades on the DB");
        }

        boolean dividendSaved = dividendService.saveDividends(dividends);
        if (!dividendSaved) {
            log.error("Could not store all the dividends on the DB");
        }


        log.info("Daily alignment completed successfully!");
        log.info("End");
    }
}
