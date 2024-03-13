package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql("classpath:dbScripts/insertSampleData.sql")
class AdminServiceImplIT {

    @Autowired
    AdminService adminService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FlexStatementRepository flexStatementRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    StrategyRepository strategyRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    DividendRepository dividendRepository;


    @Test
    void deleteUserAndUserData() {
        // Arrange
        List<User> users = userRepository.findAll();
        User user = users.get(0);
        assertNotNull(user);
        String email = user.getEmail();
        String accountId = user.getAccountId();

        // Act
        adminService.deleteUserAndUserData(email);

        assertEquals(0, flexStatementRepository.findAllByAccountId(accountId).size());
        assertEquals(0, portfolioRepository.findAllByAccountId(accountId).size());
        assertEquals(0, strategyRepository.findAllByAccountId(accountId).size());
        assertEquals(0, positionRepository.findAllByAccountId(accountId).size());
        assertEquals(0, tradeRepository.findAllByAccountId(accountId).size());
        assertEquals(0, dividendRepository.findAllByAccountId(accountId).size());
    }
}