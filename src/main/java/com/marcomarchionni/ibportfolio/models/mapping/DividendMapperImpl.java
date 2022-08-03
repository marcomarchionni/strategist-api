package com.marcomarchionni.ibportfolio.models.mapping;

import com.marcomarchionni.ibportfolio.models.domain.Dividend;
import com.marcomarchionni.ibportfolio.models.dtos.response.DividendListDto;
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
}
