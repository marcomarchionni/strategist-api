package com.marcomarchionni.strategistapi.services.odata;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PredicateBuilderImpl implements PredicateBuilder {

    private final ValueParser valueParser;

    private final Map<String, QuadFunction<Root<Portfolio>, CriteriaBuilder, String, String, Predicate>> PREDICATE_MAP = new HashMap<>();

    public PredicateBuilderImpl(ValueParser valueParser) {
        this.valueParser = valueParser;
        initializePredicateMap();
    }

    private void initializePredicateMap() {
        // Functions
        PREDICATE_MAP.put("startswith", (root, criteriaBuilder, field, value) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), value.toLowerCase() + "%"));
        PREDICATE_MAP.put("endswith", (root, criteriaBuilder, field, value) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + value.toLowerCase()));
        PREDICATE_MAP.put("substringof", (root, criteriaBuilder, field, value) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + value.toLowerCase() + "%"));

        // Not functions
        PREDICATE_MAP.put("not startswith", (root, criteriaBuilder, field, value) ->
                criteriaBuilder.notLike(criteriaBuilder.lower(root.get(field)), value.toLowerCase() + "%"));
        PREDICATE_MAP.put("not endswith", (root, criteriaBuilder, field, value) ->
                criteriaBuilder.notLike(criteriaBuilder.lower(root.get(field)), "%" + value.toLowerCase()));
        PREDICATE_MAP.put("not substringof", (root, criteriaBuilder, field, value) ->
                criteriaBuilder.notLike(criteriaBuilder.lower(root.get(field)), "%" + value.toLowerCase() + "%"));

        // Operators
        PREDICATE_MAP.put("eq", (root, criteriaBuilder, field, value) -> {
            Class<Comparable> fieldType = (Class<Comparable>) root.get(field).getJavaType();
            Comparable<?> parsedValue = valueParser.parse(fieldType, value);
            return criteriaBuilder.equal(root.get(field), parsedValue);
        });
        PREDICATE_MAP.put("ne", (root, criteriaBuilder, field, value) -> {
            Class<Comparable> fieldType = (Class<Comparable>) root.get(field).getJavaType();
            Comparable<?> parsedValue = valueParser.parse(fieldType, value);
            return criteriaBuilder.notEqual(root.get(field), parsedValue);
        });
        PREDICATE_MAP.put("gt", (root, criteriaBuilder, field, value) -> {
            Class<Comparable> fieldType = (Class<Comparable>) root.get(field).getJavaType();
            Comparable parsedValue = valueParser.parse(fieldType, value);
            return criteriaBuilder.greaterThan(root.get(field), parsedValue);
        });
        PREDICATE_MAP.put("ge", (root, criteriaBuilder, field, value) -> {
            Class<Comparable> fieldType = (Class<Comparable>) root.get(field).getJavaType();
            Comparable parsedValue = valueParser.parse(fieldType, value);
            return criteriaBuilder.greaterThanOrEqualTo(root.get(field), parsedValue);
        });
        PREDICATE_MAP.put("lt", (root, criteriaBuilder, field, value) -> {
            Class<Comparable> fieldType = (Class<Comparable>) root.get(field).getJavaType();
            Comparable parsedValue = valueParser.parse(fieldType, value);
            return criteriaBuilder.lessThan(root.get(field), parsedValue);
        });
        PREDICATE_MAP.put("le", (root, criteriaBuilder, field, value) -> {
            Class<Comparable> fieldType = (Class<Comparable>) root.get(field).getJavaType();
            Comparable parsedValue = valueParser.parse(fieldType, value);
            return criteriaBuilder.lessThanOrEqualTo(root.get(field), parsedValue);
        });
    }

    public Predicate build(Root<Portfolio> root, CriteriaBuilder criteriaBuilder, ParsedFilter filter) {
        return PREDICATE_MAP.get(filter.expression()).apply(root, criteriaBuilder, filter.field(), filter.value());
    }
}