package com.marcomarchionni.strategistapi.config;

import com.marcomarchionni.strategistapi.domain.*;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ModelMapperConfig {
    private static final Converter<BigDecimal, BigDecimal> absValue =
            ctx -> ctx.getSource() == null ? null : ctx.getSource().abs();

    public static ModelMapper configureModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(getFlexStatementPropertyMap());
        modelMapper.addMappings(getTradePropertyMap());
        modelMapper.addMappings(getPositionPropertyMap());
        modelMapper.addMappings(getMergePositionPropertyMap());
        modelMapper.addMappings(getClosedDividendPropertyMap());
        modelMapper.addMappings(getOpenDividendPropertyMap());
        modelMapper.addMappings(getMergeDividendPropertyMap());
        modelMapper.addMappings(getPortfolioSaveToPortfolioPropertyMap());
        return modelMapper;
    }

    private static PropertyMap<FlexQueryResponseDto.Order, Trade> getTradePropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setId(null);
                map().setConId(source.getConid());
            }
        };
    }

    private static PropertyMap<FlexQueryResponseDto.FlexStatement, FlexStatement> getFlexStatementPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setId(null);
            }
        };
    }

    private static PropertyMap<FlexQueryResponseDto.OpenPosition, Position> getPositionPropertyMap() {
        return new PropertyMap<>() {
            protected void configure() {
                skip().setId(null);
                map().setConId(source.getConid());
                map().setQuantity(source.getPosition());
            }
        };
    }

    private static PropertyMap<Position, Position> getMergePositionPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setId(null);
                skip().setStrategy(null);
            }
        };
    }

    // Extract a unique dividend id from the flex query dto to avoid duplicates in the update process
    private static PropertyMap<FlexQueryResponseDto.ChangeInDividendAccrual, Dividend> getClosedDividendPropertyMap() {
        return new PropertyMap<>() {
            protected void configure() {
                skip().setId(null);
                map().setConId(source.getConid());
                using(absValue).map().setGrossAmount(source.getGrossAmount());
                using(absValue).map().setNetAmount(source.getNetAmount());
                using(absValue).map().setTax(source.getTax());
                map().setOpenClosed(Dividend.OpenClosed.CLOSED);
            }
        };
    }

    private static PropertyMap<FlexQueryResponseDto.OpenDividendAccrual, Dividend> getOpenDividendPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setId(null);
                map().setConId(source.getConid());
                map().setOpenClosed(Dividend.OpenClosed.OPEN);
            }
        };
    }

    private static PropertyMap<Dividend, Dividend> getMergeDividendPropertyMap() {
        return new PropertyMap<>() {
            protected void configure() {
                skip().setId(null);
                skip().setStrategy(null);
            }
        };
    }

    private static PropertyMap<PortfolioSave, Portfolio> getPortfolioSaveToPortfolioPropertyMap() {
        return new PropertyMap<>() {
            protected void configure() {
                skip().setId(null);
            }
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return configureModelMapper();
    }
}
