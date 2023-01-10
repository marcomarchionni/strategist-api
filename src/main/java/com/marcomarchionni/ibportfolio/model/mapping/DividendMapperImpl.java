package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.ClosedDividend;
import com.marcomarchionni.ibportfolio.model.domain.Dividend;
import com.marcomarchionni.ibportfolio.model.domain.OpenDividend;
import com.marcomarchionni.ibportfolio.model.dtos.flex.FlexQueryResponse;
import com.marcomarchionni.ibportfolio.model.dtos.response.DividendListDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DividendMapperImpl implements DividendMapper {

    ModelMapper modelMapper;

    public DividendMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DividendListDto toDividendListDto(Dividend dividend) {
        return modelMapper.map(dividend, DividendListDto.class);
    }

    @Override
    public Dividend toDividend(FlexQueryResponse.ChangeInDividendAccrual closedDividendDto) {
        return modelMapper.map(closedDividendDto, ClosedDividend.class);
    }

    @Override
    public Dividend toDividend(FlexQueryResponse.OpenDividendAccrual openDividendDto) {
        return modelMapper.map(openDividendDto, OpenDividend.class);
    }
}
