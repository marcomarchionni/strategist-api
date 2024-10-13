package com.marcomarchionni.strategistapi.services.odata;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface PredicateBuilder {
    Predicate build(Root<Portfolio> root, CriteriaBuilder criteriaBuilder, ParsedFilter filter);
}
