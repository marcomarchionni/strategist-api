package com.marcomarchionni.ibportfolio.jpaconverters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;
import java.util.Optional;

@Converter(autoApply = true)
public class BigDecimalConverter implements AttributeConverter<BigDecimal, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(BigDecimal bigDecimal) {
        return bigDecimal;
    }

    @Override
    public BigDecimal convertToEntityAttribute(BigDecimal bigDecimal) {
        return Optional.ofNullable(bigDecimal).map(BigDecimal::stripTrailingZeros).orElse(null);
    }
}
