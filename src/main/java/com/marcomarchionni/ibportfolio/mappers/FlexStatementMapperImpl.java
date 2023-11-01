package com.marcomarchionni.ibportfolio.mappers;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import com.marcomarchionni.ibportfolio.dtos.flex.FlexQueryResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FlexStatementMapperImpl implements FlexStatementMapper {

    ModelMapper modelMapper;

    public FlexStatementMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public FlexStatement toFlexStatement(FlexQueryResponseDto.FlexStatement flexStatementDto) {
        return modelMapper.map(flexStatementDto, FlexStatement.class);
    }
}
