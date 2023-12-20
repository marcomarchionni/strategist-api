package com.marcomarchionni.ibportfolio.config;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.domain.Position;
import com.marcomarchionni.ibportfolio.domain.Trade;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {
    private final Converter<BigDecimal, BigDecimal> absValue =
            ctx -> ctx.getSource() == null ? null : ctx.getSource().abs();
    private final Converter<FlexQueryResponseDto.DividendAccrual, Long> dividendIdConverter = ctx -> {
        long conIdFactor = ctx.getSource().getConid() * 100_000_000L;
        long payDateLong = Long.parseLong(ctx.getSource().getPayDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        return conIdFactor + payDateLong;
    };

    private PropertyMap<FlexQueryResponseDto.Order, Trade> getTradePropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getIbOrderID());
                map().setConId(source.getConid());
            }
        };
    }
//    private final Converter<LocalDateTime, Long> flexStatementIdConverter = ctx -> {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        String formattedDateTime = ctx.getSource().format(formatter);
//        return Long.parseLong(formattedDateTime);
//    };

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(getFlexStatementPropertyMap());
        modelMapper.addMappings(getTradePropertyMap());
        modelMapper.addMappings(getPositionPropertyMap());
        modelMapper.addMappings(getMergePositionPropertyMap());
        modelMapper.addMappings(getClosedDividendPropertyMap());
        modelMapper.addMappings(getOpenDividendPropertyMap());
        modelMapper.addMappings(getMergeDividendPropertyMap());
        return modelMapper;
    }

    private PropertyMap<FlexQueryResponseDto.FlexStatement, FlexStatement> getFlexStatementPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setId(null);
            }
        };
    }

    private PropertyMap<FlexQueryResponseDto.OpenPosition, Position> getPositionPropertyMap() {
        return new PropertyMap<>() {
            protected void configure() {
                map().setId(source.getConid());
                map().setConId(source.getConid());
                map().setQuantity(source.getPosition());
            }
        };
    }

    private PropertyMap<Position, Position> getMergePositionPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setStrategy(null);
            }
        };
    }

    // Extract a unique dividend id from the flex query dto to avoid duplicates in the update process
    private PropertyMap<FlexQueryResponseDto.ChangeInDividendAccrual, Dividend> getClosedDividendPropertyMap() {
        return new PropertyMap<>() {
            protected void configure() {
                using(dividendIdConverter).map(source, destination.getId());
                map().setConId(source.getConid());
                using(absValue).map().setGrossAmount(source.getGrossAmount());
                using(absValue).map().setNetAmount(source.getNetAmount());
                using(absValue).map().setTax(source.getTax());
                map().setOpenClosed(Dividend.OpenClosed.CLOSED);
            }
        };
    }

    private PropertyMap<FlexQueryResponseDto.OpenDividendAccrual, Dividend> getOpenDividendPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                using(dividendIdConverter).map(source, destination.getId());
                map().setConId(source.getConid());
                map().setOpenClosed(Dividend.OpenClosed.OPEN);
            }
        };
    }

    private PropertyMap<Dividend, Dividend> getMergeDividendPropertyMap() {
        return new PropertyMap<>() {
            protected void configure() {
                skip().setStrategy(null);
            }
        };
    }
}
