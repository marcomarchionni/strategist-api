package com.marcomarchionni.strategistapi.services.odata;

import com.marcomarchionni.strategistapi.errorhandling.exceptions.FilterParsingException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class FilterParserImpl implements FilterParser {
    private static final List<String> FUNCTIONS = Arrays.asList("startswith", "endswith", "substringof", "not " +
            "startswith", "not endswith", "not substringof");
    private static final List<String> OPERATORS = Arrays.asList("eq", "ne", "gt", "ge", "lt", "le");

    @Override
    public List<ParsedFilter> parse(String filter) {
        String modifiedFilter = removeLeadingBracket(filter.trim());

        // Split the filter into individual parts based on logical operators
        List<String> filterParts = splitByLogicalOperators(modifiedFilter);

        // Parse each filter part individually
        return filterParts.stream()
                .map(this::parseSingleFilter)
                .toList();
    }

    private List<String> splitByLogicalOperators(String filter) {
        // Split by " and " or " or ", keeping the logical operators
        return new ArrayList<>(Arrays.asList(filter.split(" (and|or) ")));
    }

    private ParsedFilter parseSingleFilter(String filter) {
        String modifiedFilter = removeLeadingBracket(filter.trim());

        // Search for function or operator
        return FUNCTIONS.stream()
                .filter(modifiedFilter::startsWith)
                .findFirst()
                .map(function -> parseFunction(function, modifiedFilter))
                .orElseGet(() -> OPERATORS.stream()
                        .filter(modifiedFilter::contains)
                        .findFirst()
                        .map(operator -> parseOperation(operator, modifiedFilter))
                        .orElseThrow(() -> new FilterParsingException("Filter not supported: " + filter)));
    }

    private String removeLeadingBracket(String filter) {
        return filter.startsWith("(") ? filter.substring(1).trim() : filter.trim();
    }

    private ParsedFilter parseFunction(String function, String filter) {
        String[] parts = extractParameters(filter);

        String field = fieldCleanUp(function.equals("substringof") ? parts[1] : parts[0]);
        String value = valueCleanUp(function.equals("substringof") ? parts[0] : parts[1]);

        return new ParsedFilter(function, field, value);
    }

    private ParsedFilter parseOperation(String operator, String filter) {
        String[] parts = filter.split(" " + operator + " ");
        return new ParsedFilter(operator, fieldCleanUp(parts[0]), valueCleanUp(parts[1]));
    }

    private String[] extractParameters(String filter) {
        String params = filter.substring(filter.indexOf('(') + 1, filter.lastIndexOf(')'));
        return params.split(",", 2);
    }

    private String fieldCleanUp(String fieldPart) {
        return fieldPart.replaceAll("(tolower\\(|[()])", "").trim();
    }

    private String valueCleanUp(String valuePart) {
        return valuePart.replaceAll("[()']", "").trim();
    }
}
