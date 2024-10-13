package com.marcomarchionni.strategistapi.services.odata;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.repositories.PortfolioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@Sql("classpath:dbScripts/insertSampleData.sql")
public class PortfolioSpecificationIT {
    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioSpecification portfolioSpecification;

    static Stream<Arguments> sourceOne() {
        return Stream.of(
                Arguments.of("startswith(tolower(name),'saver')", "U1111111", 1),
                Arguments.of("endswith(tolower(name),'portfolio')", "U1111111", 4)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceOne")
    public void testSpecificationFilterByName(String filter, String accountId, int expectedResult) {
        // Apply filter using the specification
        Specification<Portfolio> spec = portfolioSpecification.fromFilter(filter, accountId);

        List<Portfolio> results = portfolioRepository.findAll(spec);

        // Assert results based on inserted data
        assertNotNull(results);
        assertEquals(expectedResult, results.size());
    }

    @Test
    public void testSpecificationFilterByDateRange() {
        // Apply a filter with a date range
        String filter = "(createdAt gt datetime2024-01-01T00:00:00Z) and (createdAt lt datetime2024-10-02T23:59:59Z)";
        Specification<Portfolio> spec = portfolioSpecification.fromFilter(filter, "U1111111");

        List<Portfolio> results = portfolioRepository.findAll(spec);

        // Assert results based on inserted data
        assertNotNull(results);
        assertEquals(1, results.size());
    }
}
