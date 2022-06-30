package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import com.marcomarchionni.ibportfolio.repositories.PositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DefaultDividendService implements DividendService {

    @Autowired
    private DividendRepository dividendRepository;

    @Override
    public boolean saveDividends(List<Dividend> dividends) {
        try {
            dividendRepository.saveAll(dividends);
            return true;
        } catch (Exception e) {
            log.error("Exception of some kind: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteOpenDividends() {
        try {
            dividendRepository.deleteByOpenClosed("OPEN");
            return true;
        } catch (Exception e) {
            log.error("Exception of some kind: {}", e.getMessage());
            return false;
        }
    }
}
