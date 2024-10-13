package com.marcomarchionni.strategistapi.services.odata;

import com.marcomarchionni.strategistapi.errorhandling.exceptions.FilterParsingException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ValueParserImplTest {

    ValueParser valueParser = new ValueParserImpl();

    @Test
    void testParseDateTimeValue() {
        String value = "datetime2024-10-01T00:00:00Z";
        LocalDateTime date = valueParser.parse(LocalDateTime.class, value);

        // Assert
        assertNotNull(date);
        assertEquals(2024, date.getYear());
        assertEquals(10, date.getMonthValue());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(0, date.getHour());
        assertEquals(0, date.getMinute());
        assertEquals(0, date.getSecond());
    }

    @Test
    void testParseDateValue() {
        String value = "datetime2024-10-01T00:00:00Z";
        LocalDate date = valueParser.parse(LocalDate.class, value);

        // Assert
        assertNotNull(date);
        assertEquals(2024, date.getYear());
        assertEquals(10, date.getMonthValue());
        assertEquals(1, date.getDayOfMonth());
    }

    @Test
    void testParseIntegerValue() {
        String value = "123";
        Integer integer = valueParser.parse(Integer.class, value);

        // Assert
        assertNotNull(integer);
        assertEquals(123, integer);
    }

    @Test
    void testParseLongValue() {
        String value = "123";
        Long longValue = valueParser.parse(Long.class, value);

        // Assert
        assertNotNull(longValue);
        assertEquals(123, longValue);
    }

    @Test
    void testParseStringValue() {
        String value = "test";
        String stringValue = valueParser.parse(String.class, value);

        // Assert
        assertNotNull(stringValue);
        assertEquals("test", stringValue);
    }

    @Test
    void testBigDecimalValue() {
        String value = "123.45";
        BigDecimal bigDecimalValue = valueParser.parse(BigDecimal.class, value);

        // Assert
        assertNotNull(bigDecimalValue);
        assertEquals(new BigDecimal("123.45"), bigDecimalValue);
    }

    @Test
    void testUnsupportedFieldType() {
        String value = "test";
        assertThrows(FilterParsingException.class, () -> valueParser.parse(Double.class, value));
    }


}