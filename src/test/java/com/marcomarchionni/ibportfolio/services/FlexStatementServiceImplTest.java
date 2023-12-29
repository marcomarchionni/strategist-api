package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.accessservice.FlexStatementAccessService;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlexStatementServiceImplTest {
    FlexStatementService flexStatementService;

    @Mock
    FlexStatementAccessService dataGateway;

    FlexStatement flexStatement;
    User user = getSampleUser();

    @BeforeEach
    void setUp() {
        user = getSampleUser();
        flexStatementService = new FlexStatementServiceImpl(dataGateway);

        flexStatement = FlexStatement.builder()
                .accountId(user.getAccountId())
                .fromDate(LocalDate.now().minusWeeks(1))
                .toDate(LocalDate.now())
                .whenGenerated(LocalDateTime.now())
                .build();
    }

    @Test
    void findLatestToDate() {
        when(dataGateway.findFirstOrderByToDateDesc()).thenReturn(Optional.of(flexStatement));
        LocalDate expectedLastFlexDate = flexStatement.getToDate();

        LocalDate actualLastFlexDate = flexStatementService.findLatestToDate();

        assertEquals(expectedLastFlexDate, actualLastFlexDate);
    }

    @Test
    void save() {
        when(dataGateway.save(flexStatement)).thenReturn(flexStatement);

        UpdateReport<FlexStatement> report = flexStatementService.updateFlexStatements(flexStatement);

        assertNotNull(report);
        assertNotNull(report.getAdded());
        assertEquals(user.getAccountId(), report.getAdded().get(0).getAccountId());
    }
}