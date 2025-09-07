package com.marcomarchionni.strategistapi.strategies.spec;

import com.marcomarchionni.strategistapi.domain.Strategy;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleStrategySpecification {

    public Specification<Strategy> buildSpecification(String accountId, String name, String portfolioName) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always filter by accountId
            predicates.add(cb.equal(root.get("accountId"), accountId));

            // Name filter (case-insensitive contains)
            if (name != null && !name.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            // Portfolio name filter (case-insensitive contains)
            if (portfolioName != null && !portfolioName.trim().isEmpty()) {
                Join<Object, Object> portfolioJoin = root.join("portfolio");
                predicates.add(cb.like(cb.lower(portfolioJoin.get("name")), "%" + portfolioName.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
