package com.marcomarchionni.strategistapi.strategies.spec;

import com.marcomarchionni.strategistapi.domain.Strategy;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SimpleStrategySpecification {

  public Specification<Strategy> buildSpecification(
      String accountId,
      String name,
      String description,
      String portfolioName,
      String createdAfter,
      String createdBefore) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Always filter by accountId
      predicates.add(cb.equal(root.get("accountId"), accountId));

      // Name filter (case-insensitive contains)
      if (name != null && !name.trim().isEmpty()) {
        predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
      }

      // Description filter (case-insensitive contains)
      if (description != null && !description.trim().isEmpty()) {
        predicates.add(
            cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
      }

      // Portfolio name filter (case-insensitive contains)
      if (portfolioName != null && !portfolioName.trim().isEmpty()) {
        Join<Object, Object> portfolioJoin = root.join("portfolio");
        predicates.add(
            cb.like(cb.lower(portfolioJoin.get("name")), "%" + portfolioName.toLowerCase() + "%"));
      }

      // Created date range filters
      if (createdAfter != null && !createdAfter.trim().isEmpty()) {
        try {
          LocalDate date = LocalDate.parse(createdAfter, DateTimeFormatter.ISO_LOCAL_DATE);
          predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), date.atStartOfDay()));
        } catch (Exception e) {
          // Invalid date format, ignore filter
        }
      }

      if (createdBefore != null && !createdBefore.trim().isEmpty()) {
        try {
          LocalDate date = LocalDate.parse(createdBefore, DateTimeFormatter.ISO_LOCAL_DATE);
          predicates.add(
              cb.lessThanOrEqualTo(root.get("createdAt"), date.plusDays(1).atStartOfDay()));
        } catch (Exception e) {
          // Invalid date format, ignore filter
        }
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
