package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.repositories.FlexStatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlexStatementServiceImpl implements FlexStatementService {

    private final FlexStatementRepository flexStatementRepository;

    @Override
    public LocalDate findLatestToDate(User user) {
        Optional<FlexStatement> optionalLastFlex =
                flexStatementRepository.findFirstByAccountIdOrderByToDateDesc(user.getAccountId());
        return optionalLastFlex.map(FlexStatement::getToDate).orElse(LocalDate.MIN);
    }

    @Override
    public UpdateReport<FlexStatement> updateFlexStatements(User user, FlexStatement flexStatement) {
        if (!user.getAccountId().equals(flexStatement.getAccountId())) {
            throw new UnableToSaveEntitiesException("Authenticated User and FlexStatement must have the same " +
                    "accountId");
        }
        try {
            FlexStatement savedFlexStatement = flexStatementRepository.save(flexStatement);
            return UpdateReport.<FlexStatement>builder()
                    .added(List.of(savedFlexStatement))
                    .build();
        } catch (Exception e) {
            throw new UnableToSaveEntitiesException("Unable to save FlexStatement: " + e.getMessage());
        }
    }
}
