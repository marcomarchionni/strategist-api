package com.marcomarchionni.strategistapi.mappers;

import com.marcomarchionni.strategistapi.domain.Dividend;
import com.marcomarchionni.strategistapi.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.strategistapi.dtos.response.DividendSummary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DividendMapperImpl implements DividendMapper {

    ModelMapper mapper;

    public DividendMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public DividendSummary toDividendSummary(Dividend dividend) {
        return mapper.map(dividend, DividendSummary.class);
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
