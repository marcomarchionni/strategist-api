package com.marcomarchionni.strategistapi.services.specifications;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SimplePortfolioSpecification {

  public Specification<Portfolio> buildSpecification(
      String accountId,
      String name,
      String description,
      String createdAfter,
      String createdBefore) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Always filter by accountId
      predicates.add(criteriaBuilder.equal(root.get("accountId"), accountId));

      // Name filter (case-insensitive contains)
      if (name != null && !name.trim().isEmpty()) {
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
      }

      // Description filter (case-insensitive contains)
      if (description != null && !description.trim().isEmpty()) {
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("description")),
                "%" + description.toLowerCase() + "%"));
      }

      // Created date range filters
      if (createdAfter != null && !createdAfter.trim().isEmpty()) {
        LocalDate afterDate = LocalDate.parse(createdAfter);
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), afterDate));
      }

      if (createdBefore != null && !createdBefore.trim().isEmpty()) {
        LocalDate beforeDate = LocalDate.parse(createdBefore);
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), beforeDate));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
