package com.marcomarchionni.strategistapi.services.odata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterParserImplTest {

    private final FilterParserImpl filterParser = new FilterParserImpl();

    // Method to provide test data
    static Stream<Arguments> provideFilters() {
        return Stream.of(
                Arguments.of("startswith(tolower(name),'b')", List.of(new ParsedFilter("startswith", "name", "b"))),
                Arguments.of("substringof('test', tolower(description))", List.of(new ParsedFilter("substringof",
                        "description", "test"))),
                Arguments.of("endswith(tolower(account),'123')", List.of(new ParsedFilter("endswith", "account", "123"
                ))),
                Arguments.of("(startswith(tolower(name),'a'))", List.of(new ParsedFilter("startswith", "name", "a"))),
                Arguments.of("(tolower(name) eq '')", List.of(new ParsedFilter("eq", "name", ""))),
                Arguments.of("not startswith(tolower(name),'e')", List.of(new ParsedFilter("not startswith", "name",
                        "e"))),
                Arguments.of("(createdAt gt datetime'2024-10-10T21:59:59.999Z')", List.of(new ParsedFilter("gt",
                        "createdAt", "datetime2024-10-10T21:59:59.999Z"))),
                Arguments.of("((createdAt gt datetime'2024-10-01T21:59:59.999Z') and (createdAt lt " +
                                "datetime'2024-10-02T22:00:00.000Z')) and endswith(tolower(name),'portfolio')",
                        List.of(
                                new ParsedFilter("gt", "createdAt", "datetime2024-10-01T21:59:59.999Z"),
                                new ParsedFilter("lt", "createdAt", "datetime2024-10-02T22:00:00.000Z"),
                                new ParsedFilter("endswith", "name", "portfolio")
                        ))
        );
    }

    @ParameterizedTest
    @MethodSource("provideFilters")
    @DisplayName("Test FilterParserImpl with various filter inputs")
    void testParse(String filter, List<ParsedFilter> expectedFilters) {
        List<ParsedFilter> result = filterParser.parse(filter);
        assertEquals(expectedFilters.size(), result.size(), "Number of parsed filters should match");

        for (int i = 0; i < expectedFilters.size(); i++) {
            ParsedFilter expected = expectedFilters.get(i);
            ParsedFilter actual = result.get(i);

            assertEquals(expected.field(), actual.field());
            assertEquals(expected.value(), actual.value());
            assertEquals(expected.expression(), actual.expression());
        }
    }
}