package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Position;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DefaultPositionService implements PositionService{

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public boolean savePositions(List<Position> positions) {
        /**
         * la versione più semplice possibile di un servizio di persistenza: utilizzi la repo iniettata con autowired e fai un saveAll sulla lista
         * questo crea le entità nuove o aggiorna le entità esistenti, vedi se fa al caso tuo, altrimenti ne riparliamo
         *
         * Nota la annotation a riga 12 -> ci importa una libreria per fare un po' di logging
         */

        try {
            positionRepository.saveAll(positions);
            return true;
        } catch (Exception e) {
            log.error("Exception of some kind: {}", e.getMessage());
            return false;
        }
    }
}
