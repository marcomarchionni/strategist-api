package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendSummaryDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DividendMapperImpl implements DividendMapper {

    ModelMapper mapper;

    public DividendMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public DividendSummaryDto toDividendListDto(Dividend dividend) {
        return mapper.map(dividend, DividendSummaryDto.class);
    }

    @Override
    public Dividend mergeFlexProperties(Dividend source, Dividend target) {
        mapper.map(source, target);
        return target;
    }

    @Override
    public Dividend toClosedDividend(FlexQueryResponseDto.ChangeInDividendAccrual closedDividendDto) {
        return mapper.map(closedDividendDto, Dividend.class);
    }

    @Override
    public Dividend toOpenDividend(FlexQueryResponseDto.OpenDividendAccrual openDividendDto) {
        return mapper.map(openDividendDto, Dividend.class);
    }
}
