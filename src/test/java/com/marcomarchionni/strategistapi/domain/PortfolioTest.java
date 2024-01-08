package com.marcomarchionni.strategistapi.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PortfolioTest {

    @Test
    void builder() {
        var portfolio = Portfolio.builder()
                .accountId("U1111111")
                .name("MFStockAdvisor")
                .build();

        assertEquals("U1111111", portfolio.getAccountId());
        assertEquals("MFStockAdvisor", portfolio.getName());
        assertNotNull(portfolio.getStrategies());
    }
}