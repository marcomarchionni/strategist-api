package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.accessservice.FlexStatementAccessService;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlexStatementServiceImpl implements FlexStatementService {

    private final FlexStatementAccessService dataGateway;

    @Override
    public LocalDate findLatestToDate() {
        Optional<FlexStatement> optionalLastFlex =
                dataGateway.findFirstOrderByToDateDesc();
        return optionalLastFlex.map(FlexStatement::getToDate).orElse(LocalDate.MIN);
    }

    @Override
    public UpdateReport<FlexStatement> updateFlexStatements(FlexStatement flexStatement) {
        try {
            FlexStatement savedFlexStatement = dataGateway.save(flexStatement);
            return UpdateReport.<FlexStatement>builder()
                    .added(List.of(savedFlexStatement))
                    .build();
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException("Unable to save FlexStatement: " + e.getMessage());
        }
    }
}
