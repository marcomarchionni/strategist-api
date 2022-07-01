package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.FlexStatement;
import com.marcomarchionni.ibportfolio.repositories.FlexStatementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DefaultFlexStatementService implements FlexStatementService {

    private final FlexStatementRepository flexStatementRepository;

    public DefaultFlexStatementService(FlexStatementRepository flexStatementRepository) {
        this.flexStatementRepository = flexStatementRepository;
    }

    @Override
    public LocalDate getLastReportDate() {

        Optional<FlexStatement> optionalLastFlex = flexStatementRepository.findFirstByOrderByToDateDesc();
        return optionalLastFlex.map(FlexStatement::getToDate).orElse(LocalDate.MIN);
    }

    @Override
    public FlexStatement saveFlexStatement(FlexStatement flexStatement) {
        return flexStatementRepository.save(flexStatement);
    }

    @Override
    public List<FlexStatement> getAllOrderedByFromDateAsc() {
        return flexStatementRepository.findByOrderByFromDateAsc();
    }
}
