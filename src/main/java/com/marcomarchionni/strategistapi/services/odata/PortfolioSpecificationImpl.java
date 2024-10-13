package com.marcomarchionni.strategistapi.services.odata;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.FilterParsingException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class PortfolioSpecificationImpl implements PortfolioSpecification {

    private final FilterParser filterParser;
    private final PredicateBuilder predicateBuilder;

    public Specification<Portfolio> fromFilter(String filter, String accountId) {
        return (root, query, criteriaBuilder) -> {
            // Always filter by accountId
            var accountPredicate = criteriaBuilder.equal(root.get("accountId"), accountId);

            if (filter == null || filter.isEmpty()) {
                return accountPredicate; // Only filter by accountId if no additional filters are provided
            }

            List<ParsedFilter> parsedFilters = filterParser.parse(filter);

            // Create a predicate for each filter
            List<Predicate> predicates = parsedFilters.stream()
                    .map(parsedFilter -> {
                        Predicate predicate = predicateBuilder.build(root, criteriaBuilder, parsedFilter);
                        if (predicate == null) {
                            throw new FilterParsingException("Unsupported filter expression: " + parsedFilter.expression());
                        }
                        return predicate;
                    })
                    .toList();

            return criteriaBuilder.and(accountPredicate, criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        };
    }
}
