package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.Strategy;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendListDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class DividendMapperImpl implements DividendMapper {

    public DividendMapperImpl() {
    }

    @Override
    public DividendListDto toDividendListDto(Dividend dividend) {
        DividendListDto dto = new DividendListDto();
        dto.setId(dividend.getId());
        dto.setConId(dividend.getConId());
        dto.setStrategyId(Optional.ofNullable(dividend.getStrategy()).map(Strategy::getId).orElse(null));
        dto.setStrategyName(Optional.ofNullable(dividend.getStrategy()).map(Strategy::getName).orElse(null));
        dto.setSymbol(dividend.getSymbol());
        dto.setDescription(dividend.getDescription());
        dto.setExDate(dividend.getExDate());
        dto.setPayDate(dividend.getPayDate());
        dto.setGrossRate(dividend.getGrossRate());
        dto.setQuantity(dividend.getQuantity());
        dto.setGrossAmount(dividend.getGrossAmount());
        dto.setTax(dividend.getTax());
        dto.setNetAmount(dividend.getNetAmount());
        dto.setOpenClosed(dividend.getOpenClosed().name());
        return dto;
    }

    @Override
    public Dividend mergeIbProperties(Dividend source, Dividend target) {
        target.setId(source.getId());
        target.setConId(source.getConId());
        target.setSymbol(source.getSymbol());
        target.setDescription(source.getDescription());
        target.setExDate(source.getExDate());
        target.setPayDate(source.getPayDate());
        target.setGrossRate(source.getGrossRate());
        target.setQuantity(source.getQuantity());
        target.setGrossAmount(source.getGrossAmount());
        target.setTax(source.getTax());
        target.setNetAmount(source.getNetAmount());
        target.setOpenClosed(source.getOpenClosed());
        return target;
    }

    @Override
    public Dividend toClosedDividend(FlexQueryResponseDto.ChangeInDividendAccrual closedDividendDto) {
        return toDividend(closedDividendDto, Dividend.OpenClosed.CLOSED);
    }

    @Override
    public Dividend toOpenDividend(FlexQueryResponseDto.OpenDividendAccrual openDividendDto) {
        return toDividend(openDividendDto, Dividend.OpenClosed.OPEN);
    }


    private Dividend toDividend(FlexQueryResponseDto.DividendAccrual dividendAccrual, Dividend.OpenClosed openClosed) {
        Dividend d = new Dividend();
        d.setId(calculateDividendId(dividendAccrual.getConid(), dividendAccrual.getPayDate()));
        d.setConId(dividendAccrual.getConid());
        d.setSymbol(dividendAccrual.getSymbol());
        d.setExDate(dividendAccrual.getExDate());
        d.setPayDate(dividendAccrual.getPayDate());
        d.setQuantity(dividendAccrual.getQuantity());
        d.setGrossRate(dividendAccrual.getGrossRate());
        d.setTax(Optional.ofNullable(dividendAccrual.getTax()).map(BigDecimal::abs).orElse(null));
        d.setGrossAmount(Optional.ofNullable(dividendAccrual.getGrossAmount()).map(BigDecimal::abs).orElse(null));
        d.setNetAmount(Optional.ofNullable(dividendAccrual.getNetAmount()).map(BigDecimal::abs).orElse(null));
        d.setOpenClosed(openClosed);
        return d;
    }

    private Long calculateDividendId(Long conid, LocalDate payDate) {
        long payDateLong = Long.parseLong(payDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        return (long) ((conid * 10E7) + payDateLong);
    }
}
