package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    FlexStatementRepository flexStatementRepository;
    @Mock
    PortfolioRepository portfolioRepository;
    @Mock
    StrategyRepository strategyRepository;
    @Mock
    PositionRepository positionRepository;
    @Mock
    TradeRepository tradeRepository;
    @Mock
    DividendRepository dividendRepository;

    @Test
    void deleteUserAndUserData() {
        // Setup
        User user = User.builder().email("email").accountId("accountId").build();
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));

        // Arrange
        AdminServiceImpl adminServiceServiceImpl = new AdminServiceImpl(userRepository, flexStatementRepository,
                portfolioRepository, strategyRepository, positionRepository, tradeRepository, dividendRepository);

        // Act
        adminServiceServiceImpl.deleteUserAndUserData("email");

        // Assert userService.deleteUser is called one time
        verify(userRepository, times(1)).findByEmail("email");
        verify(flexStatementRepository, times(1)).deleteByAccountId("accountId");
        verify(portfolioRepository, times(1)).deleteByAccountId("accountId");
        verify(strategyRepository, times(1)).deleteByAccountId("accountId");
        verify(positionRepository, times(1)).deleteByAccountId("accountId");
        verify(tradeRepository, times(1)).deleteByAccountId("accountId");
        verify(dividendRepository, times(1)).deleteByAccountId("accountId");
        verify(userRepository, times(1)).delete(user);
    }
}