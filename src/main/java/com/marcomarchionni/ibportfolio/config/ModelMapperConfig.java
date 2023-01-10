package com.marcomarchionni.ibportfolio.config;

import com.marcomarchionni.ibportfolio.model.domain.*;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {
    private final Converter<BigDecimal, BigDecimal> absValue =
            ctx -> ctx.getSource() == null ? null : ctx.getSource().abs();

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(getFlexStatementPropertyMap());
        modelMapper.addMappings(getTradePropertyMap());
        modelMapper.addMappings(getPositionPropertyMap());
        modelMapper.addMappings(getClosedDividendPropertyMap());
        modelMapper.addMappings(getOpenDividendPropertyMap());
        return modelMapper;
    }

    private PropertyMap<FlexQueryResponse.FlexStatement, FlexStatement> getFlexStatementPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setId(null);
            }
        };
    }

    private PropertyMap<FlexQueryResponse.Order, Trade> getTradePropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getIbOrderID());
                map().setConId(source.getConid());
                skip().setStrategy(null);
            }
        };

    }

    private PropertyMap<FlexQueryResponse.OpenPosition, Position> getPositionPropertyMap() {
        return new PropertyMap<>() {
            protected void configure() {
                map().setId(source.getConid());
                map().setConId(source.getConid());
                map().setQuantity(source.getPosition());
                skip().setStrategy(null);
            }
        };
    }

    private PropertyMap<FlexQueryResponse.ChangeInDividendAccrual, ClosedDividend> getClosedDividendPropertyMap() {
        return new PropertyMap<>() {
            protected void configure() {
                using(ctx -> calculateDividendId(
                        ((FlexQueryResponse.ChangeInDividendAccrual) ctx.getSource()).getConid(),
                        ((FlexQueryResponse.ChangeInDividendAccrual) ctx.getSource()).getPayDate()
                )).map(source, destination.getId());
                skip().setStrategy(null);
                map().setConId(source.getConid());
                using(absValue).map().setGrossAmount(source.getGrossAmount());
                using(absValue).map().setNetAmount(source.getNetAmount());
                using(absValue).map().setTax(source.getTax());
            }
        };
    }

    // For Closed Dividends, we need to extract a unique id from the flex query data
    // to avoid duplicates in the update process
    private PropertyMap<FlexQueryResponse.OpenDividendAccrual, OpenDividend> getOpenDividendPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                using(ctx -> calculateDividendId(
                        ((FlexQueryResponse.OpenDividendAccrual) ctx.getSource()).getConid(),
                        ((FlexQueryResponse.OpenDividendAccrual) ctx.getSource()).getPayDate()
                )).map(source, destination.getId());
                skip().setStrategy(null);
                map().setConId(source.getConid());
            }
        };
    }

    private Long calculateDividendId(Long conid, LocalDate payDate) {
        long payDateLong = Long.parseLong(payDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        return (long) ((conid * 10E7) + payDateLong);
    }
}
