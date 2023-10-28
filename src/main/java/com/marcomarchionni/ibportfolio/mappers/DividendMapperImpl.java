package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.ClosedDividend;
import com.marcomarchionni.ibportfolio.domain.Dividend;
import com.marcomarchionni.ibportfolio.domain.OpenDividend;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import com.marcomarchionni.ibportfolio.dtos.response.DividendListDto;
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
    public Dividend toDividend(FlexQueryResponseDto.ChangeInDividendAccrual closedDividendDto) {
        return modelMapper.map(closedDividendDto, ClosedDividend.class);
    }

    @Override
    public Dividend toDividend(FlexQueryResponseDto.OpenDividendAccrual openDividendDto) {
        return modelMapper.map(openDividendDto, OpenDividend.class);
    }
}
