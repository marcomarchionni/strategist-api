package com.marcomarchionni.strategistapi.services.odata;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioSpecificationImplTest {

    @Mock
    FilterParser filterParser;

    @Mock
    PredicateBuilder predicateBuilder;

    @Mock
    Root<Portfolio> root;

    @Mock
    CriteriaQuery<?> query;

    @Mock
    CriteriaBuilder criteriaBuilder;

    @Mock
    Predicate accountPredicate;

    @Mock
    Predicate filterPredicate;

    @InjectMocks
    PortfolioSpecificationImpl portfolioSpecification;

    @Test
    void testFromFilter() {
        String filter = "someFilter";
        String accountId = "U1111111";
        ParsedFilter parsedFilter = new ParsedFilter("eq", "field", "value");

        // Set up mock behavior
        when(filterParser.parse(filter)).thenReturn(List.of(parsedFilter));
        when(criteriaBuilder.equal(root.get("accountId"), accountId)).thenReturn(accountPredicate);
        when(predicateBuilder.build(root, criteriaBuilder, parsedFilter)).thenReturn(filterPredicate);

        // Simulate that the CriteriaBuilder's conjunction method returns a valid Predicate
        Predicate combinedPredicate = mock(Predicate.class);

        // Mocking both cases of the 'and' method
        when(criteriaBuilder.and(accountPredicate, filterPredicate)).thenReturn(combinedPredicate);
        when(criteriaBuilder.and(filterPredicate)).thenReturn(filterPredicate);

        // Execute method
        var spec = portfolioSpecification.fromFilter(filter, accountId);

        // Execute the specification (triggers the lambda)
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        // Verify interactions
        verify(filterParser).parse(filter);
        verify(criteriaBuilder).equal(root.get("accountId"), accountId);
        verify(predicateBuilder).build(root, criteriaBuilder, parsedFilter);
        verify(criteriaBuilder).and(accountPredicate, filterPredicate);

        // Assertions
        assertNotNull(result);
    }
}