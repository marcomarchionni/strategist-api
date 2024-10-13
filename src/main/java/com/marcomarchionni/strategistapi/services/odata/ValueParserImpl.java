package com.marcomarchionni.strategistapi.services.odata;

import com.marcomarchionni.strategistapi.errorhandling.exceptions.FilterParsingException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ValueParserImpl implements ValueParser {
    @Override
    public <T> T parse(Class<T> fieldType, String value) {
        return switch (fieldType.getSimpleName()) {
            case "LocalDateTime" -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'datetime'yyyy-MM-dd'T'HH:mm:ss'Z'");
                yield fieldType.cast(LocalDateTime.parse(value, formatter));
            }
            case "LocalDate" -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'datetime'yyyy-MM-dd'T'HH:mm:ss'Z'");
                yield fieldType.cast(LocalDateTime.parse(value, formatter).toLocalDate());
            }
            case "Integer" -> fieldType.cast(Integer.parseInt(value));

            case "Long" -> fieldType.cast(Long.parseLong(value));
            case "BigDecimal" -> fieldType.cast(new BigDecimal(value));
            case "String" -> fieldType.cast(value);
            default -> throw new FilterParsingException("Unsupported field type: " + fieldType);
        };
    }
}

