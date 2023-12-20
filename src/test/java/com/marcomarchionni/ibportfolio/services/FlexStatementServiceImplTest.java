package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.User;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.ibportfolio.repositories.FlexStatementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.marcomarchionni.ibportfolio.util.TestUtils.getSampleUser;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Sql("classpath:dbScripts/insertSampleData.sql")
class FlexStatementServiceImplTest {
    FlexStatementService flexStatementService;

    @Autowired
    FlexStatementRepository flexStatementRepository;

    FlexStatement flexStatement;
    User user = getSampleUser();

    @BeforeEach
    void setUp() {
        user = getSampleUser();
        flexStatementService = new FlexStatementServiceImpl(flexStatementRepository);

        flexStatement = FlexStatement.builder()
                .accountId(user.getAccountId())
                .fromDate(LocalDate.now().minusWeeks(1))
                .toDate(LocalDate.now())
                .whenGenerated(LocalDateTime.now())
                .build();


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findLatestToDate() {
        Optional<FlexStatement> lastFlex =
                flexStatementRepository.findFirstByAccountIdOrderByToDateDesc(user.getAccountId());
        assertTrue(lastFlex.isPresent());
        LocalDate expectedLastFlexDate = lastFlex.get().getToDate();

        LocalDate actualLastFlexDate = flexStatementService.findLatestToDate(user);

        assertEquals(expectedLastFlexDate, actualLastFlexDate);
    }

    @Test
    void save() {
        UpdateReport<FlexStatement> report = flexStatementService.save(user, flexStatement);

        assertNotNull(report);
        assertNotNull(report.getAdded());
        assertEquals(user.getAccountId(), report.getAdded().get(0).getAccountId());
    }

    @Test
    void saveException() {
        flexStatement.setAccountId("U2222222");
        assertThrows(UnableToSaveEntitiesException.class, () -> flexStatementService.save(user, flexStatement));
    }

    @Test
    void saveExceptionNull() {
        flexStatement.setAccountId(null);
        assertThrows(UnableToSaveEntitiesException.class, () -> flexStatementService.save(user, flexStatement));
    }
}