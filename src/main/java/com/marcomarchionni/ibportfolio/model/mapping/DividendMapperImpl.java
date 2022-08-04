package com.marcomarchionni.ibportfolio.model.mapping;

import com.marcomarchionni.ibportfolio.model.domain.Dividend;
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
}
