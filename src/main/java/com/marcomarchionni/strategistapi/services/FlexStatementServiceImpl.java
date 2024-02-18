package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.FlexStatementAccessService;
import com.marcomarchionni.strategistapi.domain.FlexStatement;
import com.marcomarchionni.strategistapi.dtos.response.update.UpdateReport;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlexStatementServiceImpl implements FlexStatementService {

    private final FlexStatementAccessService flexStatementAccessService;

    @Override
    public LocalDate findLatestToDate() {
        Optional<FlexStatement> optionalLastFlex =
                flexStatementAccessService.findFirstOrderByToDateDesc();
        return optionalLastFlex.map(FlexStatement::getToDate).orElse(LocalDate.MIN);
    }

    @Override
    public UpdateReport<FlexStatement> updateFlexStatements(FlexStatement flexStatement) {
        try {
            FlexStatement savedFlexStatement = flexStatementAccessService.save(flexStatement);
            return UpdateReport.<FlexStatement>builder()
                    .added(List.of(savedFlexStatement))
                    .build();
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException("Unable to save FlexStatement: " + e.getMessage());
        }
    }
}
