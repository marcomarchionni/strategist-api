package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.Dividend;
import com.marcomarchionni.ibportfolio.repositories.DividendRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DividendServiceImpl implements DividendService {

    @Autowired
    private DividendRepository dividendRepository;

    @Override
    public void saveDividends(List<Dividend> openOrClosedDividends) {
        try {
            dividendRepository.saveAll(openOrClosedDividends);
        } catch (Exception e) {
            log.error("Exception of some kind: {}", e.getMessage());
        }
    }

    @Override
    public void deleteAllOpenDividends() {
        try {
            dividendRepository.deleteByOpenClosed("OPEN");
        } catch (Exception e) {
            log.error("Exception of some kind: {}", e.getMessage());
        }
    }
}
