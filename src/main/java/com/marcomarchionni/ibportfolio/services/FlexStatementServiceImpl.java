package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.repositories.FlexStatementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FlexStatementServiceImpl implements FlexStatementService {

    private final FlexStatementRepository flexStatementRepository;

    @Autowired
    public FlexStatementServiceImpl(FlexStatementRepository flexStatementRepository) {
        this.flexStatementRepository = flexStatementRepository;
    }

    @Override
    public LocalDate findLatestToDate() {
        Optional<FlexStatement> optionalLastFlex = flexStatementRepository.findFirstByOrderByToDateDesc();
        return optionalLastFlex.map(FlexStatement::getToDate).orElse(LocalDate.MIN);
    }

    @Override
    public UpdateReport<FlexStatement> save(FlexStatement flexStatement) {
        FlexStatement savedFlexStatement = flexStatementRepository.save(flexStatement);
        return UpdateReport.<FlexStatement>builder()
                .added(List.of(savedFlexStatement))
                .build();
    }

    public List<FlexStatement> findAllOrderedByFromDateAsc() {
        return flexStatementRepository.findByOrderByFromDateAsc();
    }
}
