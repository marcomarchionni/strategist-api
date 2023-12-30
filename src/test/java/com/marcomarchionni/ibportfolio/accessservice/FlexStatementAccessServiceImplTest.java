package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.ibportfolio.repositories.FlexStatementRepository;
import com.marcomarchionni.ibportfolio.services.UserService;
import com.marcomarchionni.ibportfolio.services.validators.AccountIdEntityValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlexStatementAccessServiceImplTest {

    @Mock
    FlexStatementRepository flexStatementRepository;
    @Mock
    UserService userService;

    FlexStatementAccessService flexStatementAccessService;

    FlexStatement flexStatement;

    @BeforeEach
    void setUp() {
        flexStatement = FlexStatement.builder()
                .accountId("U1111111")
                .fromDate(LocalDate.now().minusWeeks(1))
                .toDate(LocalDate.now())
                .whenGenerated(LocalDateTime.now())
                .build();
        var accountIdValidator = new AccountIdEntityValidatorImpl<FlexStatement>();
        flexStatementAccessService = new FlexStatementAccessServiceImpl(flexStatementRepository, userService,
                accountIdValidator);
        when(userService.getUserAccountId()).thenReturn("U1111111");
    }

    @Test
    void save() {
        when(flexStatementRepository.save(flexStatement)).thenReturn(flexStatement);
        FlexStatement savedFlexStatement = flexStatementAccessService.save(flexStatement);
        assertEquals(flexStatement, savedFlexStatement);
    }

    @Test
    void saveException() {
        flexStatement.setAccountId("U2222222");
        assertThrows(InvalidUserDataException.class, () -> flexStatementAccessService.save(
                flexStatement));
    }

    @Test
    void saveExceptionNull() {
        flexStatement.setAccountId(null);
        assertThrows(InvalidUserDataException.class, () -> flexStatementAccessService.save(
                flexStatement));
    }

    @Test
    void findFirstOrderByToDateDesc() {
        when(flexStatementRepository.findFirstByAccountIdOrderByToDateDesc("U1111111")).thenReturn(
                Optional.of(flexStatement));
        FlexStatement actualFlexStatement = flexStatementAccessService.findFirstOrderByToDateDesc().get();
        assertEquals(flexStatement, actualFlexStatement);
    }
}