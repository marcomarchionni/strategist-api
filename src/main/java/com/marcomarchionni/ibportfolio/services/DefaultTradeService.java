package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Trade;
import com.marcomarchionni.ibportfolio.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DefaultTradeService implements TradeService{

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public boolean saveTrades(List<Trade> trades) {

        /**
         * la versione più semplice possibile di un servizio di persistenza: utilizzi la repo iniettata con autowired e fai un saveAll sulla lista
         * questo crea le entità nuove o aggiorna le entità esistenti, vedi se fa al caso tuo, altrimenti ne riparliamo
         *
         * Nota la annotation a riga 12 -> ci importa una libreria per fare un po' di logging
         */

        try {
            tradeRepository.saveAll(trades);
            return true;
        } catch (Exception e) {
            log.error("Exception of some kind: {}", e.getMessage());
            return false;
        }
    }
}
